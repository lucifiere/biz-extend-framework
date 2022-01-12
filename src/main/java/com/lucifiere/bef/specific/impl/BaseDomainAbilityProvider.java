package com.lucifiere.bef.specific.impl;

import com.google.common.collect.ImmutableSet;
import com.lucifiere.bef.specific.IAbility;
import com.lucifiere.bef.specific.IAbilitySelector;
import com.lucifiere.bef.specific.IDomainAbilityProvider;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static com.google.common.collect.Iterables.isEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

/**
 * 基础领域能力提供者
 *
 * @param <AbilityTarget> the type parameter
 * @param <DomainAbility> the type parameter
 * @author 沾清
 */
public class BaseDomainAbilityProvider<AbilityTarget, DomainAbility extends IAbility> implements
    IDomainAbilityProvider<AbilityTarget, DomainAbility> {

    /**
     * the ability realization list.
     */
    public List<DomainAbility> realizations = new ArrayList<>();

    /**
     * The Instance code to realizations.
     */
    private Map<String, DomainAbility> instanceCodeToRealizations = newHashMap();

    /**
     * The Default ability selector.
     */
    private final Set<IAbilitySelector<DomainAbility>> defaultAbilitySelector = ImmutableSet.of(
        new DefaultAbilitySelector<>());

    /**
     * The Ability selector cache.
     */
    private final Map<SelectorCacheKey, List<Pair<String, DomainAbility>>> abilitySelectorCache
        = new ConcurrentHashMap<>(1024);

    /**
     * The Enable cache ability selector.
     */
    private boolean enableCacheAbilitySelector;

    /**
     * Register realization.
     *
     * @param realization the realization
     */
    @Override
    public void registerRealization(DomainAbility realization) {
        if (null == realization) { return; }
        realizations.add(realization);
        instanceCodeToRealizations.putIfAbsent(realization.getInstanceCode(), realization);
    }

    /**
     * Gets realization.
     *
     * @param instanceCode the instance code
     * @return the realization
     */
    @Override
    public final DomainAbility getRealization(String instanceCode) {
        return instanceCodeToRealizations.get(instanceCode);
    }

    /**
     * Gets realizations.
     *
     * @param abilityCode the ability code
     * @return the realizations
     */
    @Override
    public final List<DomainAbility> getRealizations(String abilityCode) {
        List<DomainAbility> domainAbilities = new ArrayList<>();
        for (DomainAbility ability : realizations) {
            if (!abilityCode.equals(ability.getAbilityCode())) { continue; }
            domainAbilities.add(ability);
        }
        return domainAbilities;
    }

    /**
     * Gets all supported abilities.
     *
     * @param bizCode     the biz code
     * @param abilityCode the ability code
     * @param target      the target
     * @return the all supported abilities
     */
    @Override
    public final List<DomainAbility> getAllSupportedAbilities(String bizCode, String abilityCode,
                                                              AbilityTarget target) {
        return getAllAbilitiesByCondition(bizCode, abilityCode, target, null);
    }

    /**
     * Gets all abilities by condition.
     *
     * @param bizCode     the biz code
     * @param abilityCode the ability code
     * @param target      the target
     * @param predicate   the predicate
     * @return the all abilities by condition
     */
    private List<DomainAbility> getAllAbilitiesByCondition(String bizCode, String abilityCode, AbilityTarget target,
                                                           Predicate<DomainAbility> predicate) {
        if (enableCacheAbilitySelector) {
            return getAllAbilitiesByConditionWithCache(bizCode, abilityCode, target, predicate);
        }

        Set<IAbilitySelector<DomainAbility>> abilitySelectorSet = this.getAbilitySelector(bizCode, abilityCode, target);
        if (isEmpty(abilitySelectorSet)) {
            return newArrayList();
        }

        List<DomainAbility> domainAbilities = new ArrayList<>(8);
        LinkedHashSet<DomainAbility> distinct = new LinkedHashSet<>();
        for (IAbilitySelector<DomainAbility> selector : abilitySelectorSet) {
            // 注意！！！获取selector时候的abilityCode应当与这里的abilityCode相同！！！！
            List<String> list = selector.availableAbilities(abilityCode);
            list.forEach(
                code -> findDomainAbilityInstance(domainAbilities, distinct, bizCode, code, target, predicate));
        }
        return domainAbilities;
    }

    /**
     * Find domain ability instance.
     *
     * @param domainAbilities the domain abilities
     * @param distinct        the distinct
     * @param bizCode         the biz code
     * @param instanceCode    the instance code
     * @param target          the target
     * @param predicate       the predicate
     */
    private void findDomainAbilityInstance(List<DomainAbility> domainAbilities, LinkedHashSet<DomainAbility> distinct,
                                           String bizCode, String instanceCode, AbilityTarget target,
                                           Predicate<DomainAbility> predicate) {
        DomainAbility domainAbility = instanceCodeToRealizations.get(instanceCode);
        if (null == domainAbility) {
            return;
        }
        if (distinct.add(domainAbility)) {
            boolean predicateResult = (null == predicate) || predicate.test(domainAbility);
            boolean supportChecking = domainAbility.supportChecking(bizCode, target);
            if (supportChecking && predicateResult) {
                domainAbilities.add(domainAbility);
            }

        }
    }

    /**
     * Gets all supported system abilities.
     *
     * @param abilityCode the ability code
     * @param target      the target
     * @return the all supported system abilities
     */
    @Override
    public List<DomainAbility> getAllSupportedSystemAbilities(String abilityCode, AbilityTarget target) {
        return getAllAbilitiesByCondition(null, abilityCode, target, IAbility::isSystemLevel);
    }

    /**
     * get all the none system supported abilities via bizCode and abilityCode.
     * <p>
     * the non system supported abilities mainly defined by the customer themselves.
     *
     * @param bizCode     the business code.
     * @param abilityCode the ability code.
     * @param target      the target to check whether current ability support it.
     * @return the list of the ability.
     */
    @Override
    public List<DomainAbility> getAllSupportedNonSystemAbilities(String bizCode, String abilityCode,
                                                                 AbilityTarget target) {
        return getAllAbilitiesByCondition(bizCode, abilityCode, target,
            domainAbility -> !domainAbility.isSystemLevel());
    }

    /**
     * whether the specific ability is available in the specific ability list.
     *
     * @param domainAbility            the ability
     * @param availableDomainAbilities the list of the abilities.
     * @return true :available; false: not available.
     */
    private boolean isAbilityAvailable(DomainAbility domainAbility,
                                       List<Class<DomainAbility>> availableDomainAbilities) {
        for (Class<DomainAbility> abilityClass : availableDomainAbilities) {
            if (domainAbility.getClass().getName().equals(abilityClass.getName())) { return true; }
        }
        return false;
    }

    /**
     * 注意！！！！！本方法只保证能够正确返回的Selector只返回对应abilityCode的Selector，其他AbilityCode则不保证！！！！！！
     *
     * @param bizCode     the biz code
     * @param abilityCode the ability code
     * @param target      the target
     * @return the ability selector
     */
    @Override
    public Set<IAbilitySelector<DomainAbility>> getAbilitySelector(String bizCode, String abilityCode,
                                                                   AbilityTarget target) {
        return defaultAbilitySelector;
    }

    /**
     * Gets all abilities by condition with cache.
     *
     * @param bizCode     the biz code
     * @param abilityCode the ability code
     * @param target      the target
     * @param predicate   the predicate
     * @return the all abilities by condition with cache
     */
    private List<DomainAbility> getAllAbilitiesByConditionWithCache(
        String bizCode, String abilityCode, AbilityTarget target, Predicate<DomainAbility> predicate) {

        SelectorCacheKey selectorCacheKey = new SelectorCacheKey(bizCode, abilityCode);
        List<Pair<String, DomainAbility>> domainAbilityPairs = abilitySelectorCache
            .computeIfAbsent(selectorCacheKey, (key) -> {
                Set<IAbilitySelector<DomainAbility>> abilitySelectorSet = this.getAbilitySelector(bizCode, abilityCode,
                    target);
                // *2
                List<Pair<String, DomainAbility>> myDomainAbilityPairs = new ArrayList<>(
                    abilitySelectorSet.size() << 1);
                for (IAbilitySelector<DomainAbility> selector : abilitySelectorSet) {
                    // 注意！！！获取selector时候的abilityCode应当与这里的abilityCode相同！！！！
                    List<String> list = selector.availableAbilities(abilityCode);
                    for (String instanceCode : list) {
                        DomainAbility domainAbility = this.instanceCodeToRealizations.get(instanceCode);
                        myDomainAbilityPairs.add(Pair.of(instanceCode, domainAbility));
                    }
                }

                return myDomainAbilityPairs;
            });

        if (domainAbilityPairs.isEmpty()) {
            return Collections.emptyList();
        }

        List<DomainAbility> domainAbilities = new ArrayList<>(domainAbilityPairs.size());
        // *2
        HashSet<DomainAbility> distinct = new HashSet<>(domainAbilityPairs.size() << 1);
        for (Pair<String, DomainAbility> domainAbilityPair : domainAbilityPairs) {
            DomainAbility domainAbility = domainAbilityPair.getRight();
            if (null == domainAbility) {
                continue;
            }
            if (distinct.add(domainAbility)) {
                boolean predicateResult = (null == predicate) || predicate.test(domainAbility);
                boolean supportChecking = domainAbility.supportChecking(bizCode, target);
                if (supportChecking && predicateResult) {
                    domainAbilities.add(domainAbility);
                }
            }
        }
        return domainAbilities;
    }

}

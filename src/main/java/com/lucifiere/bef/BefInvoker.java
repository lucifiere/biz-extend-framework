package com.lucifiere.bef;

import com.lucifiere.bef.specific.IAbility;
import com.lucifiere.bef.specific.IDomainAbilityProvider;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * The type Bef invoker.
 *
 * @author 沾清
 */
public class BefInvoker {

    /**
     * 获取Domain Ability的Provider
     *
     * @return the domain ability provider
     */
    public static IDomainAbilityProvider getDomainAbilityProvider() {
        return com.lucifiere.bef.FunctionManager.getInstance().getDomainAbilityProvider();
    }

    /**
     * Gets all abilities.
     *
     * @param <T>         the type parameter
     * @param <Target>    the type parameter
     * @param bizCode     the biz code
     * @param abilityCode the ability code
     * @param target      the target
     * @return the all abilities
     */
    public static <T extends IAbility, Target> List<T> getAllAbilities(String bizCode, String abilityCode,
                                                                       Target target) {
        return getDomainAbilityProvider().getAllSupportedAbilities(bizCode, abilityCode, target);
    }

    /**
     * Gets all abilities.
     *
     * @param <T>         the type parameter
     * @param <Target>    the type parameter
     * @param abilityCode the ability code
     * @param target      the target
     * @return the all abilities
     */
    public static <T extends IAbility, Target> List<T> getAllAbilities(String abilityCode, Target target) {
        return getDomainAbilityProvider().getAllSupportedAbilities(null, abilityCode,
            target);
    }

    /**
     * Gets first ability.
     *
     * @param <T>         the type parameter
     * @param <Target>    the type parameter
     * @param abilityCode the ability code
     * @param target      the target
     * @return the first ability
     */
    public static <T extends IAbility, Target> T getFirstAbility(String abilityCode, Target target) {
        List<T> abilities = getAllAbilities(abilityCode, target);
        if (CollectionUtils.isEmpty(abilities)) {
            throw new IllegalArgumentException("not found ability instance by code:" + abilityCode);
        }
        return abilities.get(0);
    }

    /**
     * Gets all system abilities.
     *
     * @param <T>         the type parameter
     * @param <Target>    the type parameter
     * @param abilityCode the ability code
     * @param target      the target
     * @return the all system abilities
     */
    public static <T extends IAbility, Target> List<T> getAllSystemAbilities(String abilityCode, Target target) {
        return getDomainAbilityProvider().getAllSupportedSystemAbilities(abilityCode, target);
    }

    /**
     * Gets first system ability.
     *
     * @param <T>         the type parameter
     * @param <Target>    the type parameter
     * @param abilityCode the ability code
     * @param target      the target
     * @return the first system ability
     */
    public static <T extends IAbility, Target> T getFirstSystemAbility(String abilityCode, Target target) {
        List<T> abilities = getAllSystemAbilities(abilityCode, target);
        if (CollectionUtils.isEmpty(abilities)) {
            throw new IllegalArgumentException("not found ability instance by code:" + abilityCode);
        }
        return abilities.get(0);
    }
}

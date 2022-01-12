package com.lucifiere.bef.specific;

import com.lucifiere.bef.BefInvoker;

import java.util.List;

/**
 * The interface Domain service.
 *
 * @author 沾清
 */
public interface IDomainService {

    /**
     * 获取Domain Ability的Provider
     *
     * @return the domain ability provider
     */
    default IDomainAbilityProvider getDomainAbilityProvider() {
        return BefInvoker.getDomainAbilityProvider();
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
    default <T extends IAbility, Target> List<T> getAllAbilities(String bizCode, String abilityCode, Target target) {
        return BefInvoker.getAllAbilities(bizCode, abilityCode, target);
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
    default <T extends IAbility, Target > List<T> getAllAbilities(String abilityCode, Target target) {
        return BefInvoker.getAllAbilities(abilityCode, target);
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
    default <T extends IAbility, Target > T getFirstAbility(String abilityCode, Target target) {
        return BefInvoker.getFirstAbility(abilityCode, target);
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
    default <T extends IAbility, Target > List<T> getAllSystemAbilities(String abilityCode, Target target) {
        return BefInvoker.getAllSystemAbilities(abilityCode, target);
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
    default <T extends IAbility, Target > T getFirstSystemAbility(String abilityCode, Target target) {
        return BefInvoker.getFirstSystemAbility(abilityCode, target);
    }

}
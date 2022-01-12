package com.lucifiere.bef.specific;

import java.util.List;
import java.util.Set;

/**
 * 领域能力提供者接口
 *
 * @param <Target>        the Target type which current ability provider supporting.
 * @param <DomainAbility> the Ability which current ability provider supporting.
 * @author 沾清
 */
public interface IDomainAbilityProvider<Target, DomainAbility extends IAbility> {

    /**
     * get the List of ability based on the ability code.
     *
     * @param abilityCode the ability unique code.
     * @return the list of ability.
     */
    List<DomainAbility> getRealizations(String abilityCode);

    /**
     * register the domain ability's realization.
     *
     * @param realization the domain ability's realization.
     */
    void registerRealization(DomainAbility realization);

    /**
     * get the domain ability's realization by the instance code.
     *
     * @param instanceCode the ability's instance code.
     * @return the found DomainAbility realization.
     */
    DomainAbility getRealization(String instanceCode);

    /**
     * get the AbilitySelector based on the business code, etc.
     * 注意！！！！！本方法只保证能够正确返回的Selector只返回对应abilityCode的Selector，其他AbilityCode则不保证！！！！！！
     *
     * @param bizCode     business code.
     * @param abilityCode ability code.
     * @param target      the Target object.
     * @return the set of found AbilitySelector.
     */
    Set<IAbilitySelector<DomainAbility>> getAbilitySelector(String bizCode, String abilityCode, Target target);

    /**
     * get all the abilities based on the business code, etc.
     *
     * @param bizCode     business code.
     * @param abilityCode ability code.
     * @param target      the Target object.
     * @return the list of found abilities.
     */
    List<DomainAbility> getAllSupportedAbilities(String bizCode, String abilityCode, Target target);

    /**
     * get all the system supported abilities.
     *
     * @param abilityCode the ability code.
     * @param target      the Target object.
     * @return the list of found abilities.
     */
    List<DomainAbility> getAllSupportedSystemAbilities(String abilityCode, Target target);

    /**
     * get all the none system level abilities.
     *
     * @param bizCode     business code.
     * @param abilityCode the ability code.
     * @param target      the Target object.
     * @return the list of found abilities.
     */
    List<DomainAbility> getAllSupportedNonSystemAbilities(String bizCode, String abilityCode, Target target);

}

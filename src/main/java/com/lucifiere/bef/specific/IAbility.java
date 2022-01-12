package com.lucifiere.bef.specific;

/**
 * 能力接口
 *
 * @param <AbilityTarget>   the Target which current Ability serviced
 * @param <ExtensionPoints> the ExtensionPoint which current Ability provided
 * @author 沾清
 */
public interface IAbility<AbilityTarget, ExtensionPoints extends IExtensionPoints> {

    /**
     * Gets ability code.
     *
     * @return current ability's unique code.
     */
    String getAbilityCode();

    /**
     * set current ability unique code.
     *
     * @param abilityCode the ability unique code.
     */
    void setAbilityCode(String abilityCode);

    /**
     * Gets instance code.
     *
     * @return current domain ability's instance unique code.
     */
    String getInstanceCode();

    /**
     * check whether the target is current ability will support.
     *
     * @param bizCode the business unique code.
     * @param target  the target object.
     * @return true or false.
     */
    boolean supportChecking(String bizCode, AbilityTarget target);

    /**
     * Is system level boolean.
     *
     * @return whether current ability instance is system level or not.
     */
    boolean isSystemLevel();

    /**
     * the current ability whether is enabled.
     *
     * @param target       the target
     * @param instanceCode the instance of ability.
     * @return true or false.
     */
    boolean isEnabled(AbilityTarget target, String instanceCode);

}

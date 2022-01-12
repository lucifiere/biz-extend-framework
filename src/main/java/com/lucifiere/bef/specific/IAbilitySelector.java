package com.lucifiere.bef.specific;

import java.util.List;

/**
 * 能力选择器
 *
 * @param <Ability> the type parameter
 * @author 沾清
 */
public interface IAbilitySelector<Ability extends IAbility> {

    /**
     * get the available domain abilities with specific ability code.
     *
     * @param abilityCode the ability unique code.
     * @return the available abilities.
     */
    List<String> availableAbilities(String abilityCode);

}

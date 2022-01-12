package com.lucifiere.bef.specific.impl;

import com.lucifiere.bef.FunctionManager;
import com.lucifiere.bef.model.AbilityInstanceSpec;
import com.lucifiere.bef.model.AbilitySpec;
import com.lucifiere.bef.specific.IAbility;
import com.lucifiere.bef.specific.IAbilitySelector;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认能力选择器
 *
 * @param <Ability> the ability type for selecting.
 * @author 沾清
 */
public class DefaultAbilitySelector<Ability extends IAbility> implements IAbilitySelector<Ability> {

    /**
     * The constant ABILITY_MAP.
     */
    private static final Map<String, List<String>> ABILITY_MAP = new ConcurrentHashMap<>();

    /**
     * Available abilities list.
     *
     * @param abilityCode the ability code
     * @return the list
     */
    @Override
    public List<String> availableAbilities(String abilityCode) {
        List<String> returnValue = ABILITY_MAP.get(abilityCode);
        if (returnValue != null) {
            return returnValue;
        }

        List<String> abilityClasses = new ArrayList<>(8);
        AbilitySpec abilitySpec = FunctionManager.getInstance().getAbilitySpecByCode(abilityCode);
        if (null == abilitySpec) {
            ABILITY_MAP.put(abilityCode, abilityClasses);
            return abilityClasses;
        }

        Set<AbilityInstanceSpec> abilityInstanceSpecList = abilitySpec.getAbilityInstances();
        List<AbilityInstanceSpec> result = new ArrayList<>();
        for (AbilityInstanceSpec abilityInstanceSpec : abilityInstanceSpecList) {
            if (StringUtils.isNotEmpty(abilityInstanceSpec.getInstanceClass())) {
                result.add(abilityInstanceSpec);
            }
        }
        result.sort(Comparator.comparingInt(AbilityInstanceSpec::getPriority));
        for (AbilityInstanceSpec spec : result) {
            abilityClasses.add(spec.getInstanceClass());
        }
        ABILITY_MAP.put(abilityCode, abilityClasses);
        return abilityClasses;
    }
}

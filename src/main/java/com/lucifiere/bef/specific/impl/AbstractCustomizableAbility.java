package com.lucifiere.bef.specific.impl;

import com.lucifiere.bef.specific.IExtensionPoints;

/**
 * 自定义能力
 *
 * @param <AbilityTarget>   the type parameter
 * @param <ExtensionPoints> the type parameter
 * @author 沾清
 */
public abstract class AbstractCustomizableAbility<AbilityTarget, ExtensionPoints extends IExtensionPoints>
    extends AbstractRuleBaseAbility<AbilityTarget, ExtensionPoints> {

    /**
     * Support customization boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean supportCustomization() {
        return true;
    }

    /**
     * Need load default extension boolean.
     *
     * @return the boolean
     */
    protected final boolean needLoadDefaultExtension() {
        return false;
    }

    /**
     * Is system level boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isSystemLevel() {
        return false;
    }

    /**
     * Is enabled boolean.
     *
     * @param target       the target
     * @param instanceCode the instance code
     * @return the boolean
     */
    @Override
    public boolean isEnabled(AbilityTarget target, String instanceCode) {
        return false;
    }
}

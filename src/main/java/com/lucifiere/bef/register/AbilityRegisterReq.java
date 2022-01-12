package com.lucifiere.bef.register;

import com.lucifiere.bef.model.BaseSpec;

import java.util.Collection;

/**
 * The type Ability register req.
 *
 * @author 沾清
 */
public class AbilityRegisterReq {

    /**
     * The Parent base spec.
     */
    private BaseSpec parentBaseSpec;

    /**
     * The Class set.
     */
    private Collection<Class<?>> classSet;

    /**
     * Instantiates a new Ability register req.
     *
     * @param parentBaseSpec the parent base spec
     * @param classSet       the class set
     */
    public AbilityRegisterReq(BaseSpec parentBaseSpec, Collection<Class<?>> classSet) {
        this.parentBaseSpec = parentBaseSpec;
        this.classSet = classSet;
    }

    /**
     * Gets parentBaseSpec
     *
     * @return the parentBaseSpec
     */
    public BaseSpec getParentBaseSpec() {
        return parentBaseSpec;
    }

    /**
     * Sets parentBaseSpec
     *
     * @param parentBaseSpec the parentBaseSpec
     */
    public void setParentBaseSpec(BaseSpec parentBaseSpec) {
        this.parentBaseSpec = parentBaseSpec;
    }

    /**
     * Gets classSet
     *
     * @return the classSet
     */
    public Collection<Class<?>> getClassSet() {
        return classSet;
    }

    /**
     * Sets classSet
     *
     * @param classSet the classSet
     */
    public void setClassSet(Collection<Class<?>> classSet) {
        this.classSet = classSet;
    }
}

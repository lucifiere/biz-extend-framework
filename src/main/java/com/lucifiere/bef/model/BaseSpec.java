package com.lucifiere.bef.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 基础定义
 *
 * @author 沾清
 */
public abstract class BaseSpec {

    /**
     * Name.
     */
    private String name;

    /**
     * code.
     */
    private String code;

    /**
     * description.
     */
    private String description;

    /**
     * chech the current spec element whether in the specific Collection..
     *
     * @param elements the Collection of the spec elements.
     * @return true :in the collection, else for false.
     */
    public boolean inList(Collection<? extends BaseSpec> elements) {
        for (BaseSpec baseSpec : elements) {
            if (!this.getClass().getName().equals(baseSpec.getClass().getName())) {
                continue;
            }
            if (StringUtils.equals(this.getCode(), baseSpec.getCode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets CODE
     *
     * @return the CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets CODE
     *
     * @param code the CODE
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

package com.lucifiere.bef.specific.impl;

/**
 * 扩展点类型
 *
 * @author 沾清
 */
public enum ExtensionPointType {

    /**
     * 平台级
     */
    SYSTEM("系统级"),

    /**
     * 业务级
     */
    BUSINESS("业务级");

    /**
     * The Desc.
     */
    private String desc;

    /**
     * Instantiates a new Extension point type.
     *
     * @param desc the desc
     */
    ExtensionPointType(String desc) {
        this.desc = desc;
    }

    /**
     * Gets desc
     *
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets desc
     *
     * @param desc the desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}

package com.lucifiere.engine.register;

import com.lucifiere.biz.activities.constants.OperationParamEnum;

import java.util.Arrays;

/**
 * activity中提供的方法相关定义
 * 主要为规则引擎自动注册使用
 *
 * @author 忠魂
 */
public class ActivityFuncSpec {

    /**
     * 活动方法唯一code
     */
    private String code;

    /**
     * desc
     */
    private String desc;

    /**
     * 英文宏名称
     */
    private String macroName;

    /**
     * bean调用方法表达式
     * 例如 umpPriceActivity.decreaseMoney(context)
     */
    private String express;

    /**
     * 方法中使用的标准入参keys
     */
    private OperationParamEnum[] params;

    /**
     * activity方法的分类：condition/action/resource
     */
    private int metaType;

    /**
     * 语义动作描述 例如：满元、满件、打折、减钱、免邮等
     */
    private int actionType;

    /**
     * Of activity func spec.
     *
     * @param code the code
     * @return the activity func spec
     */
    public static ActivityFuncSpec of(String code) {
        ActivityFuncSpec activityFuncSpec = new ActivityFuncSpec();
        activityFuncSpec.code = code;
        return activityFuncSpec;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "ActivityFuncSpec{" +
            "code='" + code + '\'' +
            ", desc='" + desc + '\'' +
            ", macroName='" + macroName + '\'' +
            ", express='" + express + '\'' +
            ", params=" + Arrays.toString(params) +
            '}';
    }

    /**
     * Gets code
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
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

    /**
     * Gets macroName
     *
     * @return the macroName
     */
    public String getMacroName() {
        return macroName;
    }

    /**
     * Sets macroName
     *
     * @param macroName the macroName
     */
    public void setMacroName(String macroName) {
        this.macroName = macroName;
    }

    /**
     * Gets express
     *
     * @return the express
     */
    public String getExpress() {
        return express;
    }

    /**
     * Sets express
     *
     * @param express the express
     */
    public void setExpress(String express) {
        this.express = express;
    }

    /**
     * Gets params
     *
     * @return the params
     */
    public OperationParamEnum[] getParams() {
        return params;
    }

    /**
     * Sets params
     *
     * @param params the params
     */
    public void setParams(OperationParamEnum[] params) {
        this.params = params;
    }

    /**
     * Gets metaType
     *
     * @return the metaType
     */
    public int getMetaType() {
        return metaType;
    }

    /**
     * Sets metaType
     *
     * @param metaType the metaType
     */
    public void setMetaType(int metaType) {
        this.metaType = metaType;
    }

    /**
     * Gets actionType
     *
     * @return the actionType
     */
    public int getActionType() {
        return actionType;
    }

    /**
     * Sets actionType
     *
     * @param actionType the actionType
     */
    public void setActionType(int actionType) {
        this.actionType = actionType;
    }
}

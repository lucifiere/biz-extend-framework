package com.lucifiere.biz.activities.constants;

/**
 * 营销执行参数枚举类
 *
 * @author 忠魂
 */
public enum OperationParamEnum {

    /**
     * JSON格式的通用参数
     */
    PARAM("p_param", "JSON格式的通用参数"),
    /**
     * 打折类型
     */
    DISCOUNT_TYPE("p_discountType", "打折类型"),
    /**
     * 折扣率
     */
    DISCOUNT_RATE("p_discountRate", "折扣率"),
    ;

    /**
     * 执行参数Key
     */
    private String key;

    /**
     * 执行参数描述
     */
    private String desc;

    /**
     * 构造函数
     */
    OperationParamEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    /**
     * 参数key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * 参数描述
     */
    public String getName() {
        return this.desc;
    }
}

package com.lucifiere.biz.model.feature;

/**
 * 业务线特殊条件
 * 取决与业务线营销工具，eg：淘好票兑换券的N对N兑换规则
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年7月15日
 */
public class SimpleFeatureCondition implements FeatureCondition {

    /**
     * 值
     */
    private String feature;

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

}

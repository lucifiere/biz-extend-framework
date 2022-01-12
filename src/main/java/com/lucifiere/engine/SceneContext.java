package com.lucifiere.engine;

import com.google.common.collect.Maps;
import com.lucifiere.biz.model.condition.CommonCondition;
import com.lucifiere.biz.model.feature.FeatureCondition;

import java.io.Serializable;
import java.util.Map;

/**
 * 引擎计算--优惠信息上下文
 *
 * @author 沾清
 */
public class SceneContext implements Serializable {

    /**
     * 适用条件
     */
    private Map<String, CommonCondition> commonConditionMap;

    /**
     * 适用条件（特殊）
     */
    private Map<String, FeatureCondition> featureConditionMap;

    public SceneContext() {
        this.commonConditionMap = Maps.newHashMap();
        this.featureConditionMap = Maps.newHashMap();
    }

    public Map<String, CommonCondition> getCommonConditionMap() {
        return commonConditionMap;
    }

    public void setCommonConditionMap(Map<String, CommonCondition> commonConditionMap) {
        this.commonConditionMap = commonConditionMap;
    }

    public Map<String, FeatureCondition> getFeatureConditionMap() {
        return featureConditionMap;
    }

    public void setFeatureConditionMap(Map<String, FeatureCondition> featureConditionMap) {
        this.featureConditionMap = featureConditionMap;
    }

}

package com.lucifiere.biz.model.condition;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义通用条件
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年7月15日
 */
public class CustomCommonCondition implements CommonCondition {

    /**
     * 自定义map组
     */
    private Map<String, Object> conditionMap;

    public CustomCommonCondition(Map<String, Object> conditionMap) {
        this.conditionMap = conditionMap;
    }

    public CustomCommonCondition() {
        conditionMap = new HashMap<>();
    }

    public Map<String, Object> getConditionMap() {
        return conditionMap;
    }

    public void setConditionMap(Map<String, Object> conditionMap) {
        this.conditionMap = conditionMap;
    }

}

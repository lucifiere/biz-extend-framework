package com.lucifiere.engine;

import com.google.common.collect.Maps;
import com.lucifiere.biz.model.EngineSku;
import com.lucifiere.biz.model.action.Action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 引擎计算--优惠信息上下文
 *
 * @author 忠魂
 */
public class PromotionContext implements Serializable {

    /**
     * 优惠执行参数
     */
    private Map<String, String> paramMap = Maps.newHashMap();

    /**
     * 商品列表
     */
    private List<EngineSku> engineSkuList = new ArrayList<>();

    /**
     * 适用条件
     */
    private Map<String, Action> actionMap;

    /**
     * Gets paramMap
     *
     * @return the paramMap
     */
    public Map<String, String> getParamMap() {
        return paramMap;
    }

    /**
     * Sets param map.
     *
     * @param paramMap the param map
     */
    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * Gets engineSkuList
     *
     * @return the engineSkuList
     */
    public List<EngineSku> getEngineSkuList() {
        return engineSkuList;
    }

    /**
     * Sets engineSkuList
     *
     * @param engineSkuList the engineSkuList
     */
    public void setEngineSkuList(List<EngineSku> engineSkuList) {
        this.engineSkuList = engineSkuList;
    }

    public Map<String, Action> getActionMap() {
        return actionMap;
    }

    public void setActionMap(Map<String, Action> actionMap) {
        this.actionMap = actionMap;
    }
}

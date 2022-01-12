package com.lucifiere.engine.executor.context;

import com.lucifiere.biz.model.EngineSkuResult;
import com.lucifiere.biz.model.EngineSkuSnapshot;
import com.lucifiere.biz.model.ExtRuleInfo;

import java.util.List;
import java.util.Map;

/**
 * 计算域优惠上下文
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年6月13日
 */
public class PromotionCalContext implements PromotionExecContext {

    private Map<String, EngineSkuResult> resultMap;

    private Map<String, List<EngineSkuSnapshot>> skuSnapShotsMap;

    private ExtRuleInfo curRule;

    public Map<String, EngineSkuResult> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, EngineSkuResult> resultMap) {
        this.resultMap = resultMap;
    }

    public Map<String, List<EngineSkuSnapshot>> getSkuSnapShotsMap() {
        return skuSnapShotsMap;
    }

    public void setSkuSnapShotsMap(Map<String, List<EngineSkuSnapshot>> skuSnapShotsMap) {
        this.skuSnapShotsMap = skuSnapShotsMap;
    }

    public ExtRuleInfo getCurRule() {
        return curRule;
    }

    public void setCurRule(ExtRuleInfo curRule) {
        this.curRule = curRule;
    }
}

package com.lucifiere.engine.executor.context;

import com.lucifiere.biz.model.EngineSku;
import com.lucifiere.biz.model.ExtRuleInfo;
import com.lucifiere.biz.model.RuleCase;

import java.util.List;

/**
 * 规则域-计算前处理优惠上下文
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年6月13日
 */
public class PromotionPreHandleContext implements PromotionExecContext {

    /**
     * rule info. ps:belong to cal executor
     */
    private List<EngineSku> engineSkuList;

    /**
     * sku list. ps:belong to cal executor
     */
    private List<ExtRuleInfo> ruleDict;

    private List<RuleCase> ruleCases;

    public List<EngineSku> getEngineSkuList() {
        return engineSkuList;
    }

    public void setEngineSkuList(List<EngineSku> engineSkuList) {
        this.engineSkuList = engineSkuList;
    }

    public List<ExtRuleInfo> getRuleDict() {
        return ruleDict;
    }

    public void setRuleDict(List<ExtRuleInfo> ruleDict) {
        this.ruleDict = ruleDict;
    }

    public List<RuleCase> getRuleCases() {
        return ruleCases;
    }

    public void setRuleCases(List<RuleCase> ruleCases) {
        this.ruleCases = ruleCases;
    }
}

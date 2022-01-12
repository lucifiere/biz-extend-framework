package com.lucifiere.engine;

import com.google.common.collect.Lists;
import com.lucifiere.biz.model.ExtRuleInfo;
import com.lucifiere.biz.model.RuleCase;

import java.io.Serializable;
import java.util.List;

/**
 * 引擎计算--规则信息上下文
 *
 * @author 沾清
 */
public class RuleContext implements Serializable {

    /**
     * 商品及其相应的规则
     */
    private List<RuleCase> ruleCases = Lists.newArrayList();

    /**
     * 规则字典
     */
    private List<ExtRuleInfo> ruleDict;

    public void addCase(RuleCase oneCase) {
        this.ruleCases.add(oneCase);
    }

    public List<RuleCase> getRuleCases() {
        return ruleCases;
    }

    public void setRuleCases(List<RuleCase> ruleCases) {
        this.ruleCases = ruleCases;
    }

    public List<ExtRuleInfo> getRuleDict() {
        return ruleDict;
    }

    public void setRuleDict(List<ExtRuleInfo> ruleDict) {
        this.ruleDict = ruleDict;
    }
}

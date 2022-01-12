package com.lucifiere.biz.model;

import java.util.List;

public class RuleCase {

    private EngineSku engineSku;

    private List<String> ruleIdList;

    public RuleCase() {
    }

    public RuleCase(EngineSku engineSku, List<String> ruleIdList) {
        this.engineSku = engineSku;
        this.ruleIdList = ruleIdList;
    }

    public EngineSku getEngineSku() {
        return engineSku;
    }

    public void setEngineSku(EngineSku engineSku) {
        this.engineSku = engineSku;
    }

    public List<String> getRuleIdList() {
        return ruleIdList;
    }

    public void setRuleIdList(List<String> ruleIdList) {
        this.ruleIdList = ruleIdList;
    }

    @Override
    public String toString() {
        return "RuleCase{" +
                "ruleIdList=" + ruleIdList +
                '}';
    }
}

package com.lucifiere.biz.domain.util;

import com.lucifiere.dme.bcp.ump.api.constant.rule.RuleBrevityCode;
import com.lucifiere.dme.bcp.ump.api.request.action.Action;
import com.lucifiere.dme.bcp.ump.api.request.action.PricePromoAction;
import com.lucifiere.dme.bcp.ump.biz.cal.model.EngineSku;
import com.lucifiere.dme.bcp.ump.biz.functions.rule.model.RuleCase;
import com.lucifiere.dme.bcp.ump.repository.entity.ThemisRuleDetail;
import com.lucifiere.fastjson.JSON;
import com.lucifiere.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 价格域-价格计算工具类
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年4月9日
 */
public class PricePromotionTools {

    public static Map<String, Action> extractActionCondition(ThemisRuleDetail detail) {
        Map<String, Action> actionMap = Maps.newHashMapWithExpectedSize(1);
        JSONObject jo = JSON.parseObject(detail.getAction());
        if (jo != null) {
            if (jo.get(RuleBrevityCode.CAL_ACTION) != null) {
                actionMap.put(RuleBrevityCode.CAL_ACTION, jo.getObject(RuleBrevityCode.CAL_ACTION, PricePromoAction.class));
            }
        }
        return actionMap;
    }

    /**
     * 将rule-case转化为sku列表
     *
     * @param ruleCases 规则组合
     * @return 商品
     */
    public static List<EngineSku> convertRuleCaseToEngineSkuList(List<RuleCase> ruleCases) {
        ruleCases.forEach(ruleCase -> ruleCase.getEngineSku().setRuleIdList(ruleCase.getRuleIdList()));
        return ruleCases.stream().map(RuleCase::getEngineSku).collect(Collectors.toList());
    }

}

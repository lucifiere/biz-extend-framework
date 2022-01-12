package com.lucifiere.biz.activities.rule;

import com.lucifiere.biz.activities.constants.ThemisFunctionNameConstants;
import com.lucifiere.engine.EngineContext;
import com.lucifiere.engine.annotation.ActivityFuncDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 规则域节点
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年7月13日
 */
@Component
public class RuleActivity {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleActivity.class);

//    @Autowired
//    private RuleDomainService ruleDomainService;

    /**
     * 规则域--最优解计算
     *
     * @param context 计算上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.rule.RuleActivity.optimalSolution",
            macroName = ThemisFunctionNameConstants.RuleDomainExpressName.OPTIMAL_SOLUTION, desc = "最优解计算")
    public void optimalSolution(EngineContext context) {
//        OptimalSolutionReq req = RuleRequestConverter.convertToOptimalSolutionReq(context);
//        ruleDomainService.optimalSolution(req);
    }

    /**
     * 规则域--叠加情况预测
     *
     * @param context 计算上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.rule.RuleActivity.predictCases",
            macroName = ThemisFunctionNameConstants.RuleDomainExpressName.PREDICT_CASES, desc = "优惠结果排序")
    public void predictCases(EngineContext context) {
//        if (CollectionUtils.isEmpty(context.getRuleContext().getRuleCases())) {
//            LOGGER.warn("未找到原始商品规则组合！");
//            return;
//        }
//        if (CollectionUtils.isEmpty(context.getRuleContext().getRuleDict())) {
//            LOGGER.warn("未找到规则组合字典！");
//            return;
//        }
//        PredictCasesReq req = RuleRequestConverter.convertToPredictCasesReq(context);
//        AsyncTransientLogHelper.debug("规则优先级顺序为 -> " + req.getRuleIdOrder());
//        RuleResp<PredictCasesResp> result = ruleDomainService.predictCases(req);
//        if (result.isSuccess() && result.getModel() != null) {
//            PredictCasesResp resp = result.getModel();
//            List<RuleCase> ruleCases = Lists.newArrayList();
//            resp.getEngineSkuCases().forEach((k, v) -> v.forEach(ruleIdList -> {
//                EngineSku engineSku = new EngineSku();
//                BeanUtils.copyProperties(k, engineSku);
//                RuleCase ruleCase = new RuleCase();
//                ruleCase.setRuleIdList(ruleIdList);
//                ruleCase.setEngineSku(engineSku);
//                engineSku.setOriginal(ListUtils.isEqualList(ruleIdList, engineSku.getRuleIdList()));
//                ruleCases.add(ruleCase);
//            }));
//            AsyncTransientLogHelper.debug("预测组合为 -> " + ruleCases);
//            context.getRuleContext().setRuleCases(ruleCases);
//        }
    }

    /**
     * 规则域--计算前规则排序
     *
     * @param context 计算上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.rule.RuleActivity.sortPromotionDict",
            macroName = ThemisFunctionNameConstants.RuleDomainExpressName.SORT_PROMOTION_DICT, desc = "优惠结果排序")
    public void sortPromotionDict(EngineContext context) {
//        SortPromotionDictReq req = RuleRequestConverter.convertToPromotionResSortReq(context);
//        RuleResp<PromotionResSortResp> resp = ruleDomainService.sortPromotionDict(req);
//        PromotionResSortResp result = resp.getModel();
//        context.getRuleContext().setRuleDict(result.getRuleDict());
    }

    /**
     * 规则域--计算前排斥
     *
     * @param context 计算上下文
     */
    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.rule.RuleActivity.execExcludeRuleBeforeCalc",
            macroName = ThemisFunctionNameConstants.RuleDomainExpressName.EXEC_EXCLUDE_RULE_BEFORE_CALC, desc = "计算前优惠排他")
    public void execExcludeRuleBeforeCalc(EngineContext context) {
//        if (CollectionUtils.isEmpty(context.getRuleContext().getRuleCases())) {
//            LOGGER.warn("未找到原始商品规则组合！");
//            return;
//        }
//        if (CollectionUtils.isEmpty(context.getRuleContext().getRuleDict())) {
//            LOGGER.warn("未找到规则组合字典！");
//            return;
//        }
//        ExcludePromotionReq req = RuleRequestConverter.convertToExcludePromotionReq(context);
//        ruleDomainService.execExcludeRuleBeforeCalc(req);
    }

}

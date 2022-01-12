package com.lucifiere.common.utils;

import com.lucifiere.engine.EngineContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * 场景域-业务工具类
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年7月9日
 */
public class SceneTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(SceneTools.class);
//
//    /**
//     * 结果校验
//     *
//     * @param sceneContext 场景计算引擎上下文
//     */
//    public static void failBlockIfNecessary(SceneContext sceneContext) {
//        List<SceneResult> results = sceneContext.getSceneResults();
//        if (results.stream().anyMatch(SceneResult::ineligible)) {
//            ExceptionUtils.sceneCheckThrowBizException(results.stream().filter(SceneResult::ineligible).map(SceneResult::getResult).collect(Collectors.toList()));
//        }
//    }
//
    /**
     * 场景域条件查询
     *
     * @param service       域服务
     * @param engineContext 上下文
     */
    public static void sceneCheck(EngineContext engineContext, Function<?, ?> service) {
    }
//
//    /**
//     * 拼接场景域提示语
//     *
//     * @param resultEnum 结果提示语
//     */
//    public static String buildSceneCheckTip(@NotNull List<SceneCheckResultEnum> resultEnum) {
//        return resultEnum.stream().map(SceneCheckResultEnum::getMsg).reduce((a, b) -> a.concat("，").concat(b)).orElse(StringUtils.EMPTY);
//    }
//
//    /**
//     * 组装调用方需要的优惠信息
//     *
//     * @param sc 条件上下文
//     * @param pc 优惠计算上下文
//     * @return PromotionDetailInfo
//     */
//    public static PromotionDetailInfo buildPromotionDetailInfo(SceneContext sc, PromotionContext pc) {
//        PromotionDetailInfo result = new PromotionDetailInfo();
//        result.setActionMap(pc.getActionMap());
//        result.setCommonConditionMap(sc.getCommonConditionMap());
//        result.setFeatureConditionMap(sc.getFeatureConditionMap());
//        return result;
//    }
//
//    /**
//     * 提取规则的公共条件
//     *
//     * @param ext    外部
//     * @param rule   规则
//     * @param detail 规则细节
//     * @return 规则条件常量->公共条件
//     */
//    public static Map<String, CommonCondition> extractCommonCondition(ThemisExtVO ext, ThemisRuleVO rule, ThemisRuleDetail detail) {
//        Map<String, CommonCondition> conditionMap = Maps.newHashMapWithExpectedSize(3);
//        JSONObject jo = JSON.parseObject(detail.getCommonCondition());
//        if (jo != null) {
//            if (jo.get(RuleBrevityCode.AVAILABLE_TIME_CONDITION) != null) {
//                conditionMap.put(RuleBrevityCode.AVAILABLE_TIME_CONDITION, jo.getObject(RuleBrevityCode.AVAILABLE_TIME_CONDITION, EffectiveTimeCondition.class));
//            }
//            if (jo.get(RuleBrevityCode.EXCLUDE_TIME_CONDITION) != null) {
//                conditionMap.put(RuleBrevityCode.EXCLUDE_TIME_CONDITION, jo.getObject(RuleBrevityCode.EXCLUDE_TIME_CONDITION, EffectiveTimeCondition.class));
//            }
//            if (jo.get(RuleBrevityCode.CUSTOM_CONDITION) != null) {
//                conditionMap.put(RuleBrevityCode.CUSTOM_CONDITION, new CustomCommonCondition(jo.getJSONObject(RuleBrevityCode.CUSTOM_CONDITION)));
//            }
//        }
//        FixedCondition fixedCondition = SceneTools.buildFixedCondition(ext, rule, detail);
//        conditionMap.put(RuleBrevityCode.FIXED_CONDITION, fixedCondition);
//        return conditionMap;
//    }
//
//    /**
//     * 规则必须条件组装
//     *
//     * @param ext  外部
//     * @param rule 规则
//     * @return 规则固定条件
//     */
//    private static FixedCondition buildFixedCondition(ThemisExtVO ext, ThemisRuleVO rule, ThemisRuleDetail detail) {
//        FixedCondition fc = new FixedCondition();
//        fc.setBusinessLineCode(ext.getBusinessLineCode());
//        fc.setLeaseCode(ext.getLeaseCode());
//        fc.setSystemSource(ext.getSystemSource());
//        fc.setEndTime(rule.getEndTime());
//        fc.setStartTime(rule.getStartTime());
//        fc.setStatus(rule.getStatus());
//        fc.setVersion(rule.getVersion());
//        fc.setProductType(detail.getProductType());
//        return fc;
//    }
//
//    public static Map<String, FeatureCondition> extractFeatureCondition(ThemisRuleDetail rule) {
//        return Maps.newHashMapWithExpectedSize(0);
//    }
//
//    /**
//     * 初始化数据库规则查询结果容器，容器用来存放每个SKU可用的EXT规则列表
//     * 查询结果解释：若RuleQueryResultVO的success为true，则返回可用的规则数据，否则返回不可用的原因
//     *
//     * @param skuReqs      商品SKU
//     * @param missingExtId 未查到的规则ID
//     * @return 商品SKU -> 商品从数据库查询到的结果
//     */
//    public static Map<String, List<RuleQueryResultVO>> initRuleQueryResultMapWithMissingExt(List<SkuRequest> skuReqs, List<String> missingExtId) {
//        Map<String, List<RuleQueryResultVO>> map = Maps.newConcurrentMap();
//        if (CollectionUtils.isEmpty(missingExtId)) {
//            return map;
//        }
//        skuReqs.forEach(sku -> missingExtId.forEach(extId -> {
//            map.computeIfAbsent(sku.getSkuCode(), it -> new ArrayList<>());
//            map.get(sku.getSkuCode()).add(RuleQueryResultVO.fail(SceneCheckResultEnum.RULE_NOT_EXIST, extId));
//        }));
//        return map;
//    }

}

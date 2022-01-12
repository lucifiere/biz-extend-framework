package com.lucifiere.biz.activities.constants;

/**
 * 所有领域Function名称常量定义
 *
 * @author 忠魂
 */
public class ThemisFunctionNameConstants {

    /**
     * 价格域
     */
    public interface PriceDomainExpressName {
        /**
         * 打折计算
         */
        String DISCOUNT = "func_discount";
        /**
         * 立减计算
         */
        String REDUCE = "func_reduce";

        /**
         * 减至计算
         */
        String REDUCE_TO = "func_reduceTo";

        /**
         * 最低票价加计算
         */
        String LOWEST_PLUS = "func_lowestPlus";

        /**
         * 渠道代收费计算
         */
        String AGENT_FEE = "func_agentFee";

        /**
         * 服务费计算
         */
        String SERVICE_FEE = "func_serviceFee";

        /**
         * 补贴计算
         */
        String SUBSIDY = "func_subsidy";
    }

    /**
     * 限购域
     */
    public interface LimitBuyDomainExpressName {

        /**
         * 商品优惠打标
         */
        String MARK_SKU_PROMOTION = "function_mark_promotion";
    }

    /**
     * 规则域
     */
    public interface RuleDomainExpressName {

        /**
         * 最优解
         */
        String OPTIMAL_SOLUTION = "function_optimal_solution";

        /**
         * 结果排序
         */
        String SORT_PROMOTION_DICT = "function_sort_promotion_rule";

        /**
         * 预测组合
         */
        String PREDICT_CASES = "function_predict_cases";

        /**
         * 计算前排他
         */
        String EXEC_EXCLUDE_RULE_BEFORE_CALC = "function_exec_exclude_rule_before_calc";

    }


    /**
     * 场景域
     */
    public interface SceneDomainExpressName {

        /**
         * 有效时间场景校验
         */
        String EFFECTIVE_TIME_CHECK = "function_effective_time_check";

        /**
         * 固定条件校验
         */
        String FIXED_CHECK = "function_fixed_check";

        /**
         * 自定义场景校验
         */
        String CUSTOM_CHECK = "function_custom_check";

        /**
         * 排除时间场景校验
         */
        String EXCLUDE_TIME_CHECK = "function_exclude_time_check";

        /**
         * 渠道校验
         */
        String CHANNEL_CHECK = "function_channel_check";

        /**
         * 商品校验
         */
        String ITEM_CHECK = "function_item_check";

        /**
         * 地点校验
         */
        String PLACE_CHECK = "function_place_check";

        /**
         * 参与人场景校验
         */
        String ACTOR_CHECK = "function_actor_check";

        /**
         * 校验结果
         */
        String FAIL_BLOCK_IF_NECESSARY = "function_fail_block_if_necessary";
    }

}

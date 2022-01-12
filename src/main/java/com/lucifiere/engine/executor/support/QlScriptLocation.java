package com.lucifiere.engine.executor.support;

import com.lucifiere.dme.bcp.ump.api.constant.common.SystemSource;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 维护脚本位置
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年7月13日
 */
final public class QlScriptLocation {

    /**
     * 卡业务本地脚本位置
     */
    public static final String CARD_SCRIPT_LOCATION = "ql_script/card";

    /**
     * 券业务本地脚本位置
     */
    public static final String COUPON_SCRIPT_LOCATION = "ql_script/coupon";

    /**
     * 营销业务本地销脚本位置
     */
    public static final String MARKETING_SCRIPT_LOCATION = "ql_script/marketing";

    /**
     * 可用规则查询脚本位置
     */
    public static final String GENERAL_SCENE_FILTER_SCRIPT_LOCATION = "ql_script/scene_filter";

    /**
     * 计算前规则处理
     */
    public static final String RULE_PRE_HANDLE_SCRIPT_LOCATION = "ql_script/rule_pre_handle";

    /**
     * 脚本位置
     */
    public static final Map<String, String> CAL_SCRIPT_EXPRESS_LOCATION = Maps.newHashMap();

    static {
        // 初始化本地脚本位置，不同的系统对应不同的脚本
        CAL_SCRIPT_EXPRESS_LOCATION.put(SystemSource.LARK_CARD, CARD_SCRIPT_LOCATION);
        CAL_SCRIPT_EXPRESS_LOCATION.put(SystemSource.LARK_MARKETING, MARKETING_SCRIPT_LOCATION);
        CAL_SCRIPT_EXPRESS_LOCATION.put(SystemSource.LARK_COUPON, COUPON_SCRIPT_LOCATION);
    }

}

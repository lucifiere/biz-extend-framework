package com.lucifiere.biz.constants;

import java.io.Serializable;

/**
 * 动作简码
 *
 * @author 和同
 */
public class ActionBrevityCode implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 服务费是否允许优惠
     * true表示允许，false表示不允许
     */
    public static final String SERVICEFEE_CAL = "SERVICEFEE_CAL";

    /**
     * 网络代收费是否允许优惠
     * true表示允许，false表示不允许
     */
    public static final String AGENTFEE_CAL = "AGENTFEE_CAL";

    /**
     * 低于最低票价补差方
     *
     */
    public static final String COMPENSATE_PARTY = "COMPENSATE_PARTY";

    /**
     * 是否可以突破最低票价
     * true表示允许，false表示不允许
     */
    public static final String BELOW_LEASTPRICE = "BELOW_LEASTPRICE";

    /**
     * 支付方式限制
     */
    public static final String PAYMENT_METHODS_LIMIT = "PAYMENT_METHODS_LIMIT";

    /**
     * 出票类型
     * com.lucifiere.dme.bcp.ump.api.constant.IssueTicketMethod
     */
    public static final String ISSUE_TICKET_METHOD = "ISSUE_TICKET_METHOD";

    /**
     * 减至动作
     */
    public static final String REDUCE_TO = "REDUCE_TO";

    /**
     * 立减动作
     */
    public static final String REDUCE = "REDUCE";

    /**
     * 打折动作
     */
    public static final String DISCOUNT = "DISCOUNT";

    /**
     * 兑换
     */
    public static final String EXCHANGE = "EXCHANGE";

    /**
     * 折扣计算策略
     * CalRoundingType
     */
    public static final String ROUNDING_TYPE = "ROUNDING_TYPE";

    /**
     * 优惠金额分摊方式
     */
    public static final String SUBSIDY_MODE = "SUBSIDY_MODE";

    /**
     * 指定出票金额
     */
    public static final String FIXED_TICKET_PRICE = "FIXED_TICKET_PRICE";
}

package com.lucifiere.biz.constants;

import java.io.Serializable;

/**
 * 共有自定义规则的keys
 * @author 和同
 */
public class CommonCustomConditionKeys implements Serializable {

    /**
     * 商品范围
     * 由于卖品编码在全租户内是一样的，也就是说在判断的时候需要影院+卖品编码一起判断
     * 所以，GOODS_POOL目前要求是cinema_link_id+“_”+item_code+";"
     */
    public static final String ITEM_POOL = "ITEM_POOL";

    /**
     * 满额条件
     */
    public static final String LIMIT_VALUE = "LIMIT_VALUE";

    /**
     * 计算级别
     * CalLevel
     */
    public static final String CAL_LEVEL = "CAL_LEVEL";

    /**
     * 优惠次数
     * -1 表示不限制
     */
    public static final String BENEFIT_TIMES = "BENEFIT_TIMES";

    /**
     * 比较的基准时间
     */
    public static final String BASE_TIME = "BASE_TIME";

}

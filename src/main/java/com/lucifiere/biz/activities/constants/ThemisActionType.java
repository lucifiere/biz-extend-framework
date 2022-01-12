package com.lucifiere.biz.activities.constants;

/**
 * 活动的优惠动作类型
 *
 * @author 忠魂
 */
public final class ThemisActionType {

    /**
     * 无
     */
    public static final int NONE = 0;

    /**
     * 打折, 1
     */
    public static final int DISCOUNT = 1;

    /**
     * 减钱, 4
     */
    public static final int DECREASE = 2 << 1;

    /**
     * 赠送, 8
     */
    public static final int GIFT = 2 << 2;

}

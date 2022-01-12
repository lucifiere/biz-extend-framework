package com.lucifiere.biz.model.action;

import java.util.HashMap;
import java.util.Map;

/**
 * 促销优惠动作
 * 包括立减、满减、满折、立折、减至
 * 以及优惠动作的控制条件（eg：是否收取）
 *
 * @author 沾清
 * @version 1.0 梯度
 * @since 1.0 2019年7月15日
 */
public class PricePromoAction implements Action {

    /**
     * 以及优惠动作的控制条件（eg：是否收取服务费）
     * {@link ActionBrevityCode}
     */
    private Map<String, String> priceActionMap;

    /**
     * 简单优惠动作类型，包含立减、立折、减至
     * {@link ActionBrevityCode}
     */
    private String pricePromoType;

    /**
     * 优惠值
     */
    private String value;

    public PricePromoAction() {
        priceActionMap = new HashMap<>();
    }


    public Map<String, String> getPriceActionMap() {
        return priceActionMap;
    }

    public void setPriceActionMap(Map<String, String> priceActionMap) {
        this.priceActionMap = priceActionMap;
    }

    public String getPricePromoType() {
        return pricePromoType;
    }

    public void setPricePromoType(String pricePromoType) {
        this.pricePromoType = pricePromoType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

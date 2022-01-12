package com.lucifiere.biz.model.action;

import java.util.HashMap;
import java.util.Map;

/**
 * 赠礼优惠动作
 * 包括赠送等
 *
 * @author 沾清
 * @version 1.0 梯度
 * @since 1.0 2019年7月15日
 */
public class GiftPromoAction implements Action {

    /**
     * 以及优惠动作的控制条件
     * {@link ActionBrevityCode}
     */
    private Map<String, String> giftActionMap;

    /**
     * 简单优惠动作类型，包括赠礼
     * {@link ActionBrevityCode}
     */
    private String giftPromoType;

    public GiftPromoAction(){
        giftActionMap = new HashMap<>();
    }

    public Map<String, String> getGiftActionMap() {
        return giftActionMap;
    }

    public void setGiftActionMap(Map<String, String> giftActionMap) {
        this.giftActionMap = giftActionMap;
    }

    public String getGiftPromoType() {
        return giftPromoType;
    }

    public void setGiftPromoType(String giftPromoType) {
        this.giftPromoType = giftPromoType;
    }
}

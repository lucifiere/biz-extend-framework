package com.lucifiere.biz.model;

import com.lucifiere.biz.model.action.Action;

import java.io.Serializable;

/**
 * 优惠梯度
 * 包括立减、满减、满折、立折、减至等
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年7月15日
 */
public class PromoGradient implements Serializable {

    /**
     * 金额限制
     */
    private String limit;

    /**
     * 优惠
     */
    private Action promo;

    public PromoGradient() {
    }

    public PromoGradient(String limit, Action promo) {
        this.limit = limit;
        this.promo = promo;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Action getPromo() {
        return promo;
    }

    public void setPromo(Action promo) {
        this.promo = promo;
    }

}

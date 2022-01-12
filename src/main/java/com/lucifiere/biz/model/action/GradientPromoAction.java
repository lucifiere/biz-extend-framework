package com.lucifiere.biz.model.action;

import com.lucifiere.biz.model.PromoGradient;

import java.util.ArrayList;
import java.util.List;

/**
 * 梯度优惠动作
 * 包括满减、满折等
 *
 * @author 沾清
 * @version 1.0 梯度
 * @since 1.0 2019年7月15日
 */
public class GradientPromoAction implements Action {

    /**
     * 梯度列表
     */
    private List<PromoGradient> gradients;

    public GradientPromoAction() {
        gradients = new ArrayList<>();
    }

    public GradientPromoAction(List<PromoGradient> gradients) {
        this.gradients = gradients;
    }

    public List<PromoGradient> getGradients() {
        return gradients;
    }

    public void setGradients(List<PromoGradient> gradients) {
        this.gradients = gradients;
    }
}

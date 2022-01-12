package com.lucifiere.biz.domain.ability;

import com.lucifiere.biz.domain.model.PriceModel;

/**
 * 价格能力接口
 *
 * @author 忠魂
 */
public interface IPriceAbility {

    /**
     * Discount price result.
     *
     * @param model the model
     */
    void discount(PriceModel model);

    /**
     * Sku reduce price result.
     *
     * @param model the model
     */
    void skuReduce(PriceModel model);

    /**
     * Sku reduce to price result.
     *
     * @param model the model
     */
    void skuReduceTo(PriceModel model);

    /**
     * Order reduce price result.
     *
     * @param model the model
     */
    void orderReduce(PriceModel model);

    /**
     * Order reduce to price result.
     *
     * @param model the model
     */
    void orderReduceTo(PriceModel model);

    /**
     * Lowest plus price result.
     *
     * @param model the model
     */
    void lowestPlus(PriceModel model);

    /**
     * Subsidy price result.
     *
     * @param model the model
     */
    void subsidy(PriceModel model);
}

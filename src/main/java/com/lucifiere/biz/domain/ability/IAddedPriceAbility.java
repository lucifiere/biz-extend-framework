package com.lucifiere.biz.domain.ability;

import com.lucifiere.biz.domain.model.PriceModel;

/**
 * 附加价值能力接口
 *
 * @author 忠魂
 */
public interface IAddedPriceAbility {

    /**
     * Agent fee price result.
     *
     * @param model the model
     */
    void agentFee(PriceModel model);

    /**
     * Service fee price result.
     *
     * @param model the model
     */
    void serviceFee(PriceModel model);
}

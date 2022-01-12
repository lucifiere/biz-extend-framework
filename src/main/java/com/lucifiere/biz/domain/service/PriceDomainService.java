package com.lucifiere.biz.domain.service;

import com.lucifiere.bef.annotation.DomainService;
import com.lucifiere.bef.specific.IDomainService;
import com.lucifiere.biz.domain.model.PriceModel;

/**
 * The interface Price domain service.
 *
 * @author 忠魂
 */
public interface PriceDomainService extends IDomainService {

    /**
     * The constant DOMAIN_SERVICE_DISCOUNT.
     */
    String DOMAIN_SERVICE_DISCOUNT = "PriceDomainService.discount";

    /**
     * Discount price result.
     *
     * @param model model
     */
    @DomainService(name = "[价格域]-计算打折优惠", code = DOMAIN_SERVICE_DISCOUNT)
    void discount(PriceModel model);

    /**
     * The constant DOMAIN_SERVICE_REDUCE.
     */
    String DOMAIN_SERVICE_REDUCE = "PriceDomainService.skuReduce";

    /**
     * Reduce price result.
     *
     * @param model model
     */
    @DomainService(name = "[价格域]-计算立减优惠", code = DOMAIN_SERVICE_REDUCE)
    void reduce(PriceModel model);

    /**
     * The constant DOMAIN_SERVICE_REDUCE_TO.
     */
    String DOMAIN_SERVICE_REDUCE_TO
        = "PriceDomainService.skuReduceTo";

    /**
     * Reduce to price result.
     *
     * @param model model
     */
    @DomainService(name = "[价格域]-计算减至优惠", code = DOMAIN_SERVICE_REDUCE_TO)
    void reduceTo(PriceModel model);

    /**
     * The constant DOMAIN_SERVICE_LOWESTPLUS.
     */
    String DOMAIN_SERVICE_LOWESTPLUS
        = "PriceDomainService.lowestPlus";

    /**
     * Lowest plus price result.
     *
     * @param model model
     */
    @DomainService(name = "[价格域]-计算最低价加钱优惠", code = DOMAIN_SERVICE_LOWESTPLUS)
    void lowestPlus(PriceModel model);

    /**
     * The constant DOMAIN_SERVICE_AGENTFEE.
     */
    String DOMAIN_SERVICE_AGENTFEE = "PriceDomainService.agentFee";

    /**
     * Agent fee price result.
     *
     * @param model model
     */
    @DomainService(name = "[价格域]-计算渠道代收费优惠", code = DOMAIN_SERVICE_LOWESTPLUS)
    void agentFee(PriceModel model);

    /**
     * The constant DOMAIN_SERVICE_SERVICEFEE.
     */
    String DOMAIN_SERVICE_SERVICEFEE
        = "PriceDomainService.serviceFee";

    /**
     * Service fee price result.
     *
     * @param model model
     */
    @DomainService(name = "[价格域]-计算服务费优惠", code = DOMAIN_SERVICE_SERVICEFEE)
    void serviceFee(PriceModel model);

    /**
     * The constant DOMAIN_SERVICE_SUBSIDY.
     */
    String DOMAIN_SERVICE_SUBSIDY = "PriceDomainService.subsidy";

    /**
     * Subsidy price result.
     *
     * @param model model
     */
    @DomainService(name = "[价格域]-计算补贴优惠", code = DOMAIN_SERVICE_SUBSIDY)
    void subsidy(PriceModel model);
}

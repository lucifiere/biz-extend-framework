package com.lucifiere.biz.product.card.benefit.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权益卡限购域扩展能力
 *
 * @author 和同
 */
public class BenefitCardBuyLimitBusinessExt extends PlatFormBuyLimitBusinessExt {

    /**
     * 商品打优惠标
     *
     * @param context
     * @return
     */
    @Override
    public List<EngineSku> benefitTimesJudge(BuyLimitModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//        List<EngineSku> engineSkuList = context.getEngineSkuList();
//
//
//        List<EngineSku> benefitSkuList = new ArrayList<>();
//        String productType = TypeConvertUtil.getAsString(paramMap, CommonSkuPropertyKeys.PRODUCT_TYPE, "");
//        if (ProductType.GOODS.equals(productType)) {
//            benefitSkuList = CardProductUtil.goodsBenefitTimes(engineSkuList, paramMap);
//        }
//
//        if (ProductType.TICKET.equals(productType)) {
//            benefitSkuList = CardProductUtil.ticketBenefitTimes(engineSkuList, paramMap);
//        }
//
//        return benefitSkuList;
    }

}

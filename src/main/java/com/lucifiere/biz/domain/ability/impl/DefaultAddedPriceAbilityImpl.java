package com.lucifiere.biz.domain.ability.impl;

import com.lucifiere.bef.annotation.Enrich;
import com.lucifiere.biz.domain.model.PriceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Default added price ability.
 *
 * @author 忠魂
 */
@Enrich(name = "默认附加价值能力实现")
public class DefaultAddedPriceAbilityImpl extends AbstractAddedPriceAbility {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAddedPriceAbilityImpl.class);

    @Override
    public void agentFee(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//
//        boolean agentFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.AGENTFEE_CAL, false);
//        // 处理可优惠的命中商品
//        List<EngineSku> engineSkuList = context.getEngineSkuList().stream().filter(EngineSku::getCanPromotion).collect(
//            Collectors.toList());
//
//        for (EngineSku engineSku : engineSkuList) {
//            EngineSkuSnapshot calSkuSnapshot = engineSku.getEngineSkuSnapshot();
//            calSkuSnapshot.setBeforeAgentFee(engineSku.getSkuRemainAgentFee().getCent());
//            Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//            if (agentFeeFlag) {
//                surplusPromCredit = PriceDomainUtil.calPrice(surplusPromCredit, engineSku.getSkuRemainAgentFee(),
//                    new Money(0));
//            }
//            calSkuSnapshot.setAfterAgentFee(engineSku.getSkuRemainAgentFee().getCent());
//            calSkuSnapshot.setSystemSource(paramMap.get(PromoKeys.SYSTEMSOURCE));
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//
//            if (calSkuSnapshot.getBeforeAgentFee() != calSkuSnapshot.getAfterAgentFee()) {
//                LOGGER.info("promocode:{},skuId:{},decrease:{}", paramMap.get(PromoKeys.PROMOCODE),
//                    engineSku.getId(), calSkuSnapshot.getBeforeAgentFee() - calSkuSnapshot.getAfterAgentFee());
//            }
//        }
    }

    @Override
    public void serviceFee(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//
//        boolean serviceFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.SERVICEFEE_CAL, true);
//        // 处理可优惠的命中商品
//        List<EngineSku> engineSkuList = context.getEngineSkuList().stream().filter(EngineSku::getCanPromotion).collect(
//            Collectors.toList());
//
//        for (EngineSku engineSku : engineSkuList) {
//            EngineSkuSnapshot calSkuSnapshot = engineSku.getEngineSkuSnapshot();
//            calSkuSnapshot.setBeforeServiceFee(engineSku.getSkuRemainServiceFee().getCent());
//            Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//            if (serviceFeeFlag) {
//                surplusPromCredit = PriceDomainUtil.calPrice(surplusPromCredit, engineSku.getSkuRemainServiceFee(),
//                    new Money(0));
//            }
//            calSkuSnapshot.setAfterServiceFee(engineSku.getSkuRemainServiceFee().getCent());
//            calSkuSnapshot.setSystemSource(paramMap.get(PromoKeys.SYSTEMSOURCE));
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//
//            if (calSkuSnapshot.getBeforeServiceFee() != calSkuSnapshot.getAfterServiceFee()) {
//                LOGGER.info("promocode:{},skuId:{},decrease:{}", paramMap.get(PromoKeys.PROMOCODE),
//                    engineSku.getId(), calSkuSnapshot.getBeforeServiceFee() - calSkuSnapshot.getAfterServiceFee());
//            }
//        }
    }
}

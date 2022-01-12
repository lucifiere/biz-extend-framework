package com.lucifiere.biz.domain.ability.impl;

import com.lucifiere.bef.annotation.Enrich;
import com.lucifiere.biz.domain.model.PriceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Default price ability.
 *
 * @author 忠魂
 */
@Enrich(name = "默认价格能力实现")
public class DefaultPriceAbilityImpl extends AbstractPriceAbility {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPriceAbilityImpl.class);

    @Override
    public void discount(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//        boolean lowestPriceFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.BELOW_LEASTPRICE, false);
//        // 处理可优惠的命中商品
//        List<EngineSku> engineSkuList = context.getEngineSkuList().stream().filter(
//            engineSku -> engineSku.getCanPromotion() && (lowestPriceFlag || engineSku.getSkuRemainPrice()
//                .greaterThanEqual(engineSku.getLowestPrice()))).collect(
//            Collectors.toList());
//        if (CollectionUtils.isEmpty(engineSkuList)) {
//            return;
//        }
//        // 折扣率
//        long discountRate = TypeConvertUtil.asLong(paramMap, CalParamBrevityCode.ACTION_VALUE, 100);
//        boolean agentFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.AGENTFEE_CAL, false);
//        boolean serviceFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.SERVICEFEE_CAL, true);
//        // 分配优惠额度
//        for (EngineSku engineSku : engineSkuList) {
//            Money baseMoney = engineSku.getCurrentDiscountMoney(agentFeeFlag, serviceFeeFlag);
//            Money remainMoney = reduceExecute(context,
//                IPriceBusinessExt.EXT_POINT_CAL_REMAIN_MONEY_BY_SKU_DISCOUNT,
//                extension -> extension
//                    .calRemainMoneyBySkuDiscount(engineSku, baseMoney, new BigDecimal(discountRate), paramMap));
//            Money surplusPromCredit = baseMoney.subtract(remainMoney);
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//        }
//        // 处理命中商品优惠
//        for (EngineSku engineSku : engineSkuList) {
//            EngineSkuSnapshot engineSkuSnapshot = engineSku.getEngineSkuSnapshot();
//            // 设置计算前价格
//            engineSkuSnapshot.setBeforeSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            // 获取当前优惠额度
//            Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//            // 价格计算
//            surplusPromCredit = PriceDomainUtil.calPrice(surplusPromCredit, engineSku.getSkuRemainPrice(),
//                engineSku.getLowestPrice());
//            // 设置计算后价格
//            engineSkuSnapshot.setAfterSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            engineSkuSnapshot.setSystemSource(paramMap.get(PromoKeys.SYSTEMSOURCE));
//            // 设置剩余优惠额度
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//
//            if (engineSkuSnapshot.getBeforeSkuPrice() != engineSkuSnapshot.getAfterSkuPrice()) {
//                LOGGER.info("promocode:{},skuId:{},decrease:{}", paramMap.get(PromoKeys.PROMOCODE), engineSku.getId(),
//                    engineSkuSnapshot.getBeforeSkuPrice() - engineSkuSnapshot.getAfterSkuPrice());
//            }
//        }
    }

    @Override
    public void skuReduce(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//        boolean lowestPriceFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.BELOW_LEASTPRICE, false);
//        // 处理可优惠的命中商品
//        List<EngineSku> engineSkuList = context.getEngineSkuList().stream().filter(
//            engineSku -> engineSku.getCanPromotion() && (lowestPriceFlag || engineSku.getSkuRemainPrice()
//                .greaterThanEqual(engineSku.getLowestPrice()))).collect(
//            Collectors.toList());
//        if (CollectionUtils.isEmpty(engineSkuList)) {
//            return;
//        }
//        // 立减金额
//        long originDecreaseMoney = TypeConvertUtil.asLong(paramMap, CalParamBrevityCode.ACTION_VALUE, 0);
//        boolean agentFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.AGENTFEE_CAL, false);
//        boolean serviceFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.SERVICEFEE_CAL, true);
//        // 分配优惠额度
//        for (EngineSku engineSku : engineSkuList) {
//            Money baseMoney = engineSku.getCurrentDiscountMoney(agentFeeFlag, serviceFeeFlag);
//            Money remainMoney = reduceExecute(context,
//                IPriceBusinessExt.EXT_POINT_CAL_REMAIN_MONEY_BY_SKU_REDUCE,
//                extension -> extension
//                    .calRemainMoneyBySkuReduce(engineSku, baseMoney, originDecreaseMoney, paramMap));
//            Money surplusPromCredit = baseMoney.subtract(remainMoney);
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//        }
//        // 处理命中商品优惠
//        for (EngineSku engineSku : engineSkuList) {
//            EngineSkuSnapshot calSkuSnapshot = engineSku.getEngineSkuSnapshot();
//            // 设置计算前价格
//            calSkuSnapshot.setBeforeSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            // 获取当前优惠额度
//            Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//            // 价格计算
//            surplusPromCredit = PriceDomainUtil.calPrice(surplusPromCredit, engineSku.getSkuRemainPrice(),
//                engineSku.getLowestPrice());
//            // 设置计算后价格
//            calSkuSnapshot.setAfterSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            calSkuSnapshot.setSystemSource(paramMap.get(PromoKeys.SYSTEMSOURCE));
//            // 设置剩余优惠额度
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//
//            if (calSkuSnapshot.getBeforeSkuPrice() != calSkuSnapshot.getAfterSkuPrice()) {
//                LOGGER.info("promocode:{},skuId:{},decrease:{}", paramMap.get(PromoKeys.PROMOCODE), engineSku.getId(),
//                    calSkuSnapshot.getBeforeSkuPrice() -
//                        calSkuSnapshot.getAfterSkuPrice());
//            }
//        }
    }

    @Override
    public void skuReduceTo(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//        boolean lowestPriceFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.BELOW_LEASTPRICE, false);
//        // 处理可优惠的命中商品
//        List<EngineSku> engineSkuList = context.getEngineSkuList().stream().filter(
//            engineSku -> engineSku.getCanPromotion() && (lowestPriceFlag || engineSku.getSkuRemainPrice()
//                .greaterThanEqual(engineSku.getLowestPrice()))).collect(
//            Collectors.toList());
//        if (CollectionUtils.isEmpty(engineSkuList)) {
//            return;
//        }
//        // 减至金额
//        long reduceToMoney = TypeConvertUtil.asLong(paramMap, CalParamBrevityCode.ACTION_VALUE, 0);
//        boolean agentFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.AGENTFEE_CAL, false);
//        boolean serviceFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.SERVICEFEE_CAL, true);
//        // 分配优惠额度
//        for (EngineSku engineSku : engineSkuList) {
//            Money baseMoney = engineSku.getCurrentDiscountMoney(agentFeeFlag, serviceFeeFlag);
//            Money remainMoney = reduceExecute(context,
//                IPriceBusinessExt.EXT_POINT_CAL_REMAIN_MONEY_BY_SKU_REDUCE_TO,
//                extension -> extension
//                    .calRemainMoneyBySkuReduceTo(engineSku, baseMoney, reduceToMoney, paramMap));
//            Money surplusPromCredit = baseMoney.subtract(remainMoney);
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//        }
//        // 处理命中商品优惠
//        for (EngineSku engineSku : engineSkuList) {
//            EngineSkuSnapshot engineSkuSnapshot = engineSku.getEngineSkuSnapshot();
//            // 设置计算前价格
//            engineSkuSnapshot.setBeforeSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            // 获取当前优惠额度
//            Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//            // 价格计算
//            surplusPromCredit = PriceDomainUtil.calPrice(surplusPromCredit, engineSku.getSkuRemainPrice(),
//                engineSku.getLowestPrice());
//            // 设置计算后价格
//            engineSkuSnapshot.setAfterSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            engineSkuSnapshot.setSystemSource(paramMap.get(PromoKeys.SYSTEMSOURCE));
//            // 设置剩余优惠额度
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//
//            if (engineSkuSnapshot.getBeforeSkuPrice() != engineSkuSnapshot.getAfterSkuPrice()) {
//                LOGGER.info("promocode:{},skuId:{},decrease:{}", paramMap.get(PromoKeys.PROMOCODE), engineSku.getId(),
//                    engineSkuSnapshot.getBeforeSkuPrice() - engineSkuSnapshot.getAfterSkuPrice());
//            }
//        }
    }

    @Override
    public void orderReduce(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//        // 分配优惠额度
//        List<EngineSku> engineSkuList = reduceExecute(context, IPriceBusinessExt.EXT_POINT_SHARE_CREDIT_BY_ORDER_REDUCE,
//            extension -> extension.shareCreditByOrderReduce(context));
//        if (CollectionUtils.isEmpty(engineSkuList)) {
//            return;
//        }
//        // 处理命中商品优惠
//        for (EngineSku engineSku : engineSkuList) {
//            EngineSkuSnapshot calSkuSnapshot = engineSku.getEngineSkuSnapshot();
//            // 设置计算前价格
//            calSkuSnapshot.setBeforeSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            // 获取当前优惠额度
//            Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//            // 价格计算
//            surplusPromCredit = PriceDomainUtil.calPrice(surplusPromCredit, engineSku.getSkuRemainPrice(),
//                engineSku.getLowestPrice());
//            // 设置计算后价格
//            calSkuSnapshot.setAfterSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            calSkuSnapshot.setSystemSource(paramMap.get(PromoKeys.SYSTEMSOURCE));
//            // 设置剩余优惠额度
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//
//            if (calSkuSnapshot.getBeforeSkuPrice() != calSkuSnapshot.getAfterSkuPrice()) {
//                LOGGER.info("promocode:{},skuId:{},decrease:{}", paramMap.get(PromoKeys.PROMOCODE), engineSku.getId(),
//                    calSkuSnapshot.getBeforeSkuPrice() - calSkuSnapshot.getAfterSkuPrice());
//            }
//        }
    }

    @Override
    public void orderReduceTo(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//        // 分配优惠额度
//        List<EngineSku> engineSkuList = reduceExecute(context,
//            IPriceBusinessExt.EXT_POINT_SHARE_CREDIT_BY_ORDER_REDUCE_TO,
//            extension -> extension.shareCreditByOrderReduceTo(context));
//        if (CollectionUtils.isEmpty(engineSkuList)) {
//            return;
//        }
//        // 处理命中商品优惠
//        for (EngineSku engineSku : engineSkuList) {
//            EngineSkuSnapshot calSkuSnapshot = engineSku.getEngineSkuSnapshot();
//            // 设置计算前价格
//            calSkuSnapshot.setBeforeSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            // 获取当前优惠额度
//            Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//            // 价格计算
//            surplusPromCredit = PriceDomainUtil.calPrice(surplusPromCredit, engineSku.getSkuRemainPrice(),
//                engineSku.getLowestPrice());
//            // 设置计算后价格
//            calSkuSnapshot.setAfterSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            calSkuSnapshot.setSystemSource(paramMap.get(PromoKeys.SYSTEMSOURCE));
//            // 设置剩余优惠额度
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//
//            if (calSkuSnapshot.getBeforeSkuPrice() != calSkuSnapshot.getAfterSkuPrice()) {
//                LOGGER.info("promocode:{},skuId:{},decrease:{}", paramMap.get(PromoKeys.PROMOCODE), engineSku.getId(),
//                    calSkuSnapshot.getBeforeSkuPrice() - calSkuSnapshot.getAfterSkuPrice());
//            }
//        }
    }

    @Override
    public void lowestPlus(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//        boolean lowestPriceFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.BELOW_LEASTPRICE, false);
//        // 处理可优惠的命中商品
//        List<EngineSku> engineSkuList = context.getEngineSkuList().stream().filter(
//            engineSku -> engineSku.getCanPromotion() && (lowestPriceFlag || engineSku.getSkuRemainPrice()
//                .greaterThanEqual(engineSku.getLowestPrice()))).collect(
//            Collectors.toList());
//        if (CollectionUtils.isEmpty(engineSkuList)) {
//            return;
//        }
//        // 最低价加钱金额
//        long lowestPlusMoney = TypeConvertUtil.asLong(paramMap, CalParamBrevityCode.ACTION_VALUE);
//        boolean agentFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.AGENTFEE_CAL, false);
//        boolean serviceFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.SERVICEFEE_CAL, true);
//        // 分配优惠额度
//        for (EngineSku engineSku : engineSkuList) {
//            Money baseMoney = engineSku.getCurrentDiscountMoney(agentFeeFlag, serviceFeeFlag);
//            Money surplusPromCredit = baseMoney.subtractFrom(engineSku.getLowestPrice().getCent() +
//                lowestPlusMoney);
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//        }
//        // 处理命中商品优惠
//        for (EngineSku engineSku : engineSkuList) {
//            EngineSkuSnapshot engineSkuSnapshot = engineSku.getEngineSkuSnapshot();
//            // 设置计算前价格
//            engineSkuSnapshot.setBeforeSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            // 获取当前优惠额度
//            Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//            // 价格计算
//            surplusPromCredit = PriceDomainUtil.calPrice(surplusPromCredit, engineSku.getSkuRemainPrice(),
//                engineSku.getLowestPrice());
//            // 设置计算后价格
//            engineSkuSnapshot.setAfterSkuPrice(engineSku.getSkuRemainPrice().getCent());
//            engineSkuSnapshot.setSystemSource(paramMap.get(PromoKeys.SYSTEMSOURCE));
//            // 设置剩余优惠额度
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//
//            if (engineSkuSnapshot.getBeforeSkuPrice() != engineSkuSnapshot.getAfterSkuPrice()) {
//                LOGGER.info("promocode:{},skuId:{},decrease:{}", paramMap.get(PromoKeys.PROMOCODE), engineSku.getId(),
//                    engineSkuSnapshot.getBeforeSkuPrice() - engineSkuSnapshot.getAfterSkuPrice());
//            }
//        }
    }

    @Override
    public void subsidy(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//        boolean lowestPriceFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.BELOW_LEASTPRICE, false);
//        // 处理可优惠的命中商品
//        List<EngineSku> engineSkuList = context.getEngineSkuList().stream().filter(EngineSku::getCanPromotion).collect(
//            Collectors.toList());
//
//        String promotionCode = TypeConvertUtil.getAsString(paramMap, PromoKeys.PROMOCODE);
//        String promotionType = TypeConvertUtil.getAsString(paramMap, PromoKeys.PROMOTYPE);
//        String extName = TypeConvertUtil.getAsString(paramMap, PromoKeys.EXTNAME);
//
//        String subSidyType = TypeConvertUtil.getAsString(paramMap, ActionBrevityCode.COMPENSATE_PARTY,
//            CompensateParty.CONSUME_CINEMA);
//        String subSidyCode = TypeConvertUtil.getAsString(paramMap, CalParamBrevityCode.SUBSIDY_CODE);
//        String subSidyPayment = TypeConvertUtil.getAsString(paramMap, "PaymentMethod", PaymentMethod.SUBSUDIDY_PAYMENT);
//
//        for (EngineSku engineSku : engineSkuList) {
//            EngineSkuSnapshot calSkuSnapshot = engineSku.getEngineSkuSnapshot();
//
//            Money subsidyMoney = new Money(0);
//            if (lowestPriceFlag) {
//                if (!CompensateParty.USER.equals(subSidyType)) {
//                    subsidyMoney = reduceExecute(context,
//                        IPriceBusinessExt.EXT_POINT_CAL_SUBSIDY_MONEY,
//                        extension -> extension.calSubSidyMoney(engineSku, paramMap));
//                    //                    PriceDomainUtil.calSubsidy(surplusPromCredit, engineSku.getSkuRemainPrice(),
//                    //                            subsidyMoney);
//                    if (subsidyMoney.greaterThan(new Money(0))) {
//                        calSkuSnapshot.setPayCode(subSidyCode);
//                        calSkuSnapshot.setPayType(subSidyType);
//                        calSkuSnapshot.setPayName(extName);
//                        calSkuSnapshot.setPaymentMethod(subSidyPayment);
//                        calSkuSnapshot.setPaymentAmount(subsidyMoney.getCent());
//
//                        LOGGER.info("promocode:{},skuId:{},decrease:{}", paramMap.get(PromoKeys.PROMOCODE),
//                            engineSku.getId(), calSkuSnapshot.getBeforeSkuPrice() - calSkuSnapshot.getAfterSkuPrice());
//                    }
//                    //如果出现补贴是负数，则认为用该支付方式代替补贴，作为支付
//                    else if(subsidyMoney.lessThan(new Money(0))){
//                        calSkuSnapshot.setPayCode(promotionCode);
//                        calSkuSnapshot.setPayType(promotionType);
//                        calSkuSnapshot.setPayName(extName);
//                        calSkuSnapshot.setPaymentMethod(subSidyPayment);
//                        calSkuSnapshot.setPaymentAmount(engineSku.getSurplusPromCredit());
//                    }
//                }
//            }
//
//            // 设置计算后价格
//            calSkuSnapshot.setAfterSkuPrice(engineSku.getSkuRemainPrice().getCent());
//
//            long skuDiscountAmount = (calSkuSnapshot.getBeforeSkuPrice() - calSkuSnapshot.getAfterSkuPrice()) + (
//                calSkuSnapshot.getBeforeAgentFee() - calSkuSnapshot.getAfterAgentFee()) + (
//                calSkuSnapshot.getBeforeServiceFee() - calSkuSnapshot.getAfterServiceFee())
//                - calSkuSnapshot.getPaymentAmount();
//            if (skuDiscountAmount >= 0) {
//                calSkuSnapshot.setPromotionCode(promotionCode);
//                calSkuSnapshot.setPromotionType(promotionType);
//                calSkuSnapshot.setPromotionName(extName);
//                calSkuSnapshot.setSystemSource(paramMap.get(PromoKeys.SYSTEMSOURCE));
//                calSkuSnapshot.setDiscountAmount(skuDiscountAmount);
//            }
//
//            engineSku.setSurplusPromCredit(0);
//        }
    }

}

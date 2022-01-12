package com.lucifiere.biz.domain.ext;

import com.lucifiere.biz.domain.model.PriceModel;
import com.lucifiere.biz.model.EngineSku;
import com.lucifiere.biz.model.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The type Abstract price domain business ext.
 *
 * @author 忠魂
 */
public abstract class AbstractPriceDomainBusinessExt implements PriceDomainBusinessExt {

    /**
     * Share credit by order reduce list.
     *
     * @param context the context
     * @return the list
     */
    @Override
    public List<EngineSku> shareCreditByOrderReduce(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//        boolean lowestPriceFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.BELOW_LEASTPRICE, true);
//        // 处理可优惠的命中商品
//        List<EngineSku> engineSkuList = context.getEngineSkuList().stream().filter(
//            engineSku -> engineSku.getCanPromotion() && (lowestPriceFlag || engineSku.getSkuRemainPrice()
//                .greaterThanEqual(engineSku.getLowestPrice()))).collect(
//            Collectors.toList());
//        if (CollectionUtils.isEmpty(engineSkuList)) {
//            return null;
//        }
//        engineSkuList.sort(new SkuPriceComparator<>());
//        // 立减金额
//        long originDecreaseMoney = TypeConvertUtil.asLong(paramMap, CalParamBrevityCode.ACTION_VALUE, 0);
//        // 商品最大可减金额
//        Money totalMoney = new Money(0);
//        engineSkuList.forEach(engineSku -> totalMoney.addTo(engineSku.getCurrentMoney()));
//        // 实际最大立减金额
//        Money totalDecreaseMoney = totalMoney.getCent() > originDecreaseMoney ? new Money(originDecreaseMoney)
//            : new Money(totalMoney.getCent());
//        // 叠加每次优惠金额
//        Money addDecreaseMoney = new Money(0);
//        for (int i = 0; i < engineSkuList.size(); i++) {
//            EngineSku engineSku = engineSkuList.get(i);
//            Money surplusPromCredit;
//            // 最后一件商品前
//            if (i < engineSkuList.size() - 1) {
//                surplusPromCredit = new Money(
//                    totalDecreaseMoney.getCent() * engineSku.getCurrentMoney().getCent() / totalMoney.getCent());
//            } else {
//                // 最后一件商品
//                surplusPromCredit = totalDecreaseMoney.subtract(addDecreaseMoney);
//            }
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//            addDecreaseMoney.addTo(surplusPromCredit);
//        }
//        return engineSkuList;
        return null;
    }

    /**
     * Share credit by order reduce to list.
     *
     * @param context the context
     * @return the list
     */
    @Override
    public List<EngineSku> shareCreditByOrderReduceTo(PriceModel context) {
//        Map<String, String> paramMap = context.getParamMap();
//        boolean lowestPriceFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.BELOW_LEASTPRICE, true);
//        // 处理可优惠的命中商品
//        List<EngineSku> engineSkuList = context.getEngineSkuList().stream().filter(
//            engineSku -> engineSku.getCanPromotion() && (lowestPriceFlag || engineSku.getSkuRemainPrice()
//                .greaterThanEqual(engineSku.getLowestPrice()))).collect(
//            Collectors.toList());
//        if (CollectionUtils.isEmpty(engineSkuList)) {
//            return null;
//        }
//        engineSkuList.sort(new SkuPriceComparator<>());
//        Money totalMoney = new Money(0);
//        engineSkuList.forEach(engineSku -> totalMoney.addTo(engineSku.getCurrentMoney()));
//        // 减至金额
//        long reduceToMoney = TypeConvertUtil.asLong(paramMap, CalParamBrevityCode.ACTION_VALUE, 0);
//        // 商品总价小于减至金额
//        if (totalMoney.getCent() <= reduceToMoney) {
//            return null;
//        }
//        // 实际最大立减金额
//        Money totalDecreaseMoney = totalMoney.subtract(new Money(reduceToMoney));
//        // 叠加每次优惠金额
//        Money addDecreaseMoney = new Money(0);
//        for (int i = 0; i < engineSkuList.size(); i++) {
//            EngineSku engineSku = engineSkuList.get(i);
//            Money surplusPromCredit;
//            // 最后一件商品前
//            if (i < engineSkuList.size() - 1) {
//                surplusPromCredit = new Money(
//                    totalDecreaseMoney.getCent() * engineSku.getCurrentMoney().getCent() / totalMoney.getCent());
//            } else {
//                // 最后一件商品
//                surplusPromCredit = totalDecreaseMoney.subtract(addDecreaseMoney);
//            }
//            engineSku.setSurplusPromCredit(surplusPromCredit.getCent());
//            addDecreaseMoney.addTo(surplusPromCredit);
//        }
//        return engineSkuList;
        return null;
    }

    @Override
    public Money calRemainMoneyBySkuDiscount(EngineSku engineSku, Money baseMoney, BigDecimal discountRate, Map<String, String> paramMap) {
//        String roundingType = TypeConvertUtil.getAsString(paramMap, ActionBrevityCode.ROUNDING_TYPE,
//            CalRoundingType.HALF_UP);
//        if (CalRoundingType.HALF_UP_KEEP_INTEGER.equals(roundingType)) {
//            BigDecimal baseMoneyDecimal = new BigDecimal(baseMoney.getCent());
//            BigDecimal remainMoneyDecimal = baseMoneyDecimal.multiply(discountRate).divide(new BigDecimal("100"), 0,
//                BigDecimal.ROUND_CEILING);
//            return new Money(100 * (Math.round(remainMoneyDecimal.doubleValue() / 100)));
//        }
//        if(CalRoundingType.KEEP_TWO_REMAIN.equals(roundingType)){
//            return baseMoney.multiply(discountRate).divide(new BigDecimal(100),BigDecimal.ROUND_CEILING);
//        }
//        //默认优惠四舍五入--UP，那剩余的钱就是--DOWN
//        return baseMoney.multiply(discountRate).divide(new BigDecimal(100),BigDecimal.ROUND_HALF_DOWN);
        return null;
    }

    @Override
    public Money calRemainMoneyBySkuReduce(EngineSku engineSku, Money baseMoney, long originDecreaseMoney,
                                           Map<String, String> paramMap) {
        return baseMoney.subtract(new Money(originDecreaseMoney));
    }

    @Override
    public Money calRemainMoneyBySkuReduceTo(EngineSku engineSku, Money baseMoney, long reduceToMoney,
                                             Map<String, String> paramMap) {
        return new Money(reduceToMoney);
    }

    @Override
    public Money calSubSidyMoney(EngineSku engineSku, Map<String, String> paramMap) {
//        String issueTicketMethod = TypeConvertUtil.getAsString(paramMap, ActionBrevityCode.ISSUE_TICKET_METHOD, IssueTicketMethod.USERPAYPRICE);
//
//        //原价出票
//        if(IssueTicketMethod.ORIGINALISSUE.equals(issueTicketMethod)){
//            return orginalTicketPriceSubsidy(engineSku);
//        }
//        //指定金额出票
//        if(IssueTicketMethod.FIXPRICE.equals(issueTicketMethod)){
//            return fixTicketPriceSubSidy(paramMap, engineSku);
//        }else{
//            Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//            Money subSidyMoney = new Money(0);
//            PriceDomainUtil.calSubsidy(surplusPromCredit,engineSku.getSkuRemainPrice(),subSidyMoney);
//            return subSidyMoney;
//        }
        return null;
    }

    private Money fixTicketPriceSubSidy(Map<String, String> paramMap, EngineSku engineSku) {
//        //固定出票金额
//        long fixTicketPrice = TypeConvertUtil.asLong(paramMap,ActionBrevityCode.FIXED_TICKET_PRICE);
//        Money remainTotalPrice = engineSku.getRemainTotalPrice();
//
//        //step1 调整出票金额
//        //如果固定出票金额比最低票价低，则出票金额为最低票价
//        if(fixTicketPrice<engineSku.getLowestPrice().getCent()){
//            fixTicketPrice = engineSku.getLowestPrice().getCent();
//        }
//        //如果固定出票金额比原价金额还要高，则出票金额为剩余价格
//        else if(fixTicketPrice> engineSku.getOrginalTotalPrice().getCent()){
//            fixTicketPrice = engineSku.getOrginalTotalPrice().getCent();
//        }
//
//        //step2 如果用户实付金额比指定出票金额高，则不需要补贴
//        if(remainTotalPrice.greaterThan(new Money(fixTicketPrice))){
//            return new Money(0);
//        }
//        //step3 如果用户实付金额没有指定票价高，则需要补贴
//        boolean agentFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.AGENTFEE_CAL, false);
//        boolean serviceFeeFlag = TypeConvertUtil.asBoolean(paramMap, ActionBrevityCode.SERVICEFEE_CAL, true);
//        //低于最低票价的补贴
//        Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//        Money subSidyMoney = new Money(0);
//        PriceDomainUtil.calSubsidy(surplusPromCredit,engineSku.getSkuRemainPrice(),subSidyMoney);
//
//        //判断当前出票金额中是否有强制收取费用，如果有，要去掉
//        if(!agentFeeFlag){
//            fixTicketPrice = fixTicketPrice - engineSku.getSkuRemainAgentFee().getCent();
//            remainTotalPrice = remainTotalPrice.subtract(engineSku.getSkuRemainAgentFee());
//        }
//
//        if(!serviceFeeFlag){
//            fixTicketPrice = fixTicketPrice - engineSku.getSkuRemainServiceFee().getCent();
//            remainTotalPrice = remainTotalPrice.subtract(engineSku.getSkuRemainServiceFee());
//        }
//
//
//        Money upLowestPriceSubsidy = new Money(fixTicketPrice).subtract(remainTotalPrice);
//        subSidyMoney = subSidyMoney.add(upLowestPriceSubsidy);
//
//        //step3 补贴金额
//        return subSidyMoney;
        return null;
    }

    //原价出票补贴
    private Money orginalTicketPriceSubsidy(EngineSku engineSku) {
//        Money surplusPromCredit = new Money(engineSku.getSurplusPromCredit());
//        Money subSidyMoney = new Money(0);
//        EngineSkuSnapshot engineSkuSnapshot = engineSku.getEngineSkuSnapshot();
//        long skuOrginalPrice = engineSkuSnapshot.getBeforeSkuPrice() + engineSkuSnapshot.getBeforeServiceFee() + engineSkuSnapshot.getBeforeAgentFee();
//
//        //低于最低票价的补贴
//        if (surplusPromCredit.greaterThan(new Money(0))) {
//            PriceDomainUtil.calSubsidy(surplusPromCredit, engineSku.getSkuRemainPrice(), subSidyMoney);
//            Money upLowestPriceSubsidy = new Money(skuOrginalPrice).subtract(engineSku.getLowestPrice());
//            subSidyMoney = subSidyMoney.add(upLowestPriceSubsidy);
//        } else {
//            long skuRemainPrice = engineSkuSnapshot.getAfterSkuPrice() + engineSkuSnapshot.getAfterServiceFee() + engineSkuSnapshot.getAfterAgentFee();
//            subSidyMoney = subSidyMoney.add(new Money(skuOrginalPrice - skuRemainPrice));
//        }
//
//
//        //step3 补贴金额
//        return subSidyMoney;
        return null;
    }
}

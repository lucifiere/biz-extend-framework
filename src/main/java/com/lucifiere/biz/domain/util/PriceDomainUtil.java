package com.lucifiere.biz.domain.util;

import com.lucifiere.dme.bcp.ump.biz.cal.model.Money;

/**
 * The type Price domain util.
 *
 * @author 忠魂
 */
public class PriceDomainUtil {

    /**
     * Cal price money.
     *
     * @param promCredit  the prom credit
     * @param calPrice    the cal price
     * @param lowestPrice the lowest price
     * @return the money
     */
    public static Money calPrice(Money promCredit, Money calPrice, Money lowestPrice) {
        if (promCredit.getCent() <= 0) {
            return promCredit;
        }

        if (calPrice.getCent() <= 0 || calPrice.lessThan(lowestPrice)) {
            return promCredit;
        }
        // 最大可优惠额度
        Long maxPromMoney = calPrice.subtract(lowestPrice).getCent();
        // 实际优惠额度
        Long remainPromMoney;
        // 如果最大可优惠金额大于优惠额度
        if (maxPromMoney.compareTo(promCredit.getCent()) >= 0) {
            remainPromMoney = promCredit.getCent();
        } else { // 如果最大可优惠金额小于优惠额度
            remainPromMoney = maxPromMoney;
        }
        // 实付款 = 实付款 - 实际优惠额度
        calPrice.subtractFrom(remainPromMoney);
        // 剩余优惠额度 = 剩余优惠额度 - 实际优惠额度
        return promCredit.subtractFrom(remainPromMoney);
    }

    /**
     * Cal subsidy money.
     *
     * @param promCredit   the prom credit
     * @param calPrice     the cal price
     * @param subsidyMoney the subsidy money
     * @return the money
     */
    public static Money calSubsidy(Money promCredit, Money calPrice, Money subsidyMoney) {
        if (promCredit.getCent() <= 0) {
            return promCredit;
        }

        if (calPrice.getCent() <= 0) {
            return promCredit;
        }
        // 最大可优惠额度
        Long maxPromMoney = calPrice.getCent();
        // 实际优惠额度
        Long remainPromMoney;
        // 如果最大可优惠金额大于优惠额度
        if (maxPromMoney.compareTo(promCredit.getCent()) >= 0) {
            remainPromMoney = promCredit.getCent();
        } else {
            // 如果最大可优惠金额小于优惠额度
            remainPromMoney = maxPromMoney;
        }
        // 商品实付款 = 商品实付款 - 实际优惠额度
        calPrice.subtractFrom(remainPromMoney);
        subsidyMoney.addForm(remainPromMoney);
        // 剩余优惠额度 = 剩余优惠额度 - 实际优惠额度
        return promCredit.subtractFrom(remainPromMoney);
    }
}

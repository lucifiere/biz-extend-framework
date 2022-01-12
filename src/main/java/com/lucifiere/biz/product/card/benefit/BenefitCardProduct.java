package com.lucifiere.biz.product.card.benefit;

import com.lucifiere.bef.annotation.Product;
import com.lucifiere.bef.template.AbstractTemplate;

/**
 * @author 忠魂
 */
@Product(name = "淘好票会员卡产品-权益卡", code = BenefitCardProduct.CODE,desc = "权益卡产品为无支付能力的优惠能力产品，作用在单品")
public class BenefitCardProduct extends AbstractTemplate<BenefitExtFacade> {

    public static final String CODE = "com.lucifiere.dme.bcp.ump.biz.product.card.benefit.BenefitCardProduct";

}

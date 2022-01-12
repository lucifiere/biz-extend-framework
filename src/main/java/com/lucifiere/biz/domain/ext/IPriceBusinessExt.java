package com.lucifiere.biz.domain.ext;

import com.lucifiere.bef.annotation.AbilityExtension;
import com.lucifiere.bef.specific.IExtensionPoints;
import com.lucifiere.biz.domain.model.PriceModel;
import com.lucifiere.biz.model.EngineSku;
import com.lucifiere.biz.model.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 价格业务扩展点
 *
 * @author 忠魂
 */
public interface IPriceBusinessExt extends IExtensionPoints {

    /**
     * The constant EXT_POINT_SHARE_CREDIT_BY_ORDER_REDUCE.
     */
    String EXT_POINT_SHARE_CREDIT_BY_ORDER_REDUCE = "com.lucifiere.dme.bcp.ump.biz.domain.price.ext.IPriceBusinessExt.shareCreditByOrderReduce";

    /**
     * Share credit by order reduce list.
     *
     * @param context the context
     * @return the list
     */
    @AbilityExtension(name = "【价格域-扩展点】分配优惠额度 订单立减优惠", code = EXT_POINT_SHARE_CREDIT_BY_ORDER_REDUCE,
        desc = "【价格域-扩展点】分配优惠额度 订单立减优惠 ")
    List<EngineSku> shareCreditByOrderReduce(PriceModel context);

    /**
     * The constant EXT_POINT_SHARE_CREDIT_BY_ORDER_REDUCE_TO.
     */
    String EXT_POINT_SHARE_CREDIT_BY_ORDER_REDUCE_TO
        = "com.lucifiere.dme.bcp.ump.biz.domain.price.ext.IPriceBusinessExt.shareCreditByOrderReduceTo";

    /**
     * Share credit by order reduce to list.
     *
     * @param context the context
     * @return the list
     */
    @AbilityExtension(name = "【价格域-扩展点】分配优惠额度 订单减至优惠", code = EXT_POINT_SHARE_CREDIT_BY_ORDER_REDUCE_TO,
        desc = "【价格域-扩展点】分配优惠额度 订单减至优惠")
    List<EngineSku> shareCreditByOrderReduceTo(PriceModel context);


    String EXT_POINT_CAL_REMAIN_MONEY_BY_SKU_DISCOUNT
            = "com.lucifiere.dme.bcp.ump.biz.domain.price.ext.IPriceBusinessExt.calRemainMoneyBySkuDiscount";

    /**
     * 计算实付金额，根据sku打折
     *
     * @param engineSku    the engine sku
     * @param baseMoney    the base money
     * @param discountRate the discount rate
     * @param paramMap     the param map
     * @return money money
     */
    @AbilityExtension(name = "【价格域-扩展点】计算实付金额，sku打折优惠", code = EXT_POINT_CAL_REMAIN_MONEY_BY_SKU_DISCOUNT,
            desc = "【价格域】计算实付金额，根据sku打折 业务扩展点")
    Money calRemainMoneyBySkuDiscount(EngineSku engineSku, Money baseMoney, BigDecimal discountRate,
                                      Map<String, String> paramMap);


    String EXT_POINT_CAL_REMAIN_MONEY_BY_SKU_REDUCE
        = "com.lucifiere.dme.bcp.ump.biz.domain.price.ext.IPriceBusinessExt.calRemainMoneyBySkuReduce";
    /**
     * 计算实付金额，根据sku立减
     *
     * @param engineSku    the engine sku
     * @param baseMoney    the base money
     * @param originDecreaseMoney originDecreaseMoney
     * @param paramMap     the param map
     * @return money money
     */
    @AbilityExtension(name = "【价格域-扩展点】计算实付金额，sku立减优惠", code = EXT_POINT_CAL_REMAIN_MONEY_BY_SKU_REDUCE,
        desc = "【价格域-扩展点】计算实付金额，sku立减优惠")
    Money calRemainMoneyBySkuReduce(EngineSku engineSku, Money baseMoney, long originDecreaseMoney,
                                      Map<String, String> paramMap);


    String EXT_POINT_CAL_REMAIN_MONEY_BY_SKU_REDUCE_TO
        = "com.lucifiere.dme.bcp.ump.biz.domain.price.ext.IPriceBusinessExt.calRemainMoneyBySkuReduceTo";
    /**
     * 计算实付金额，根据sku减至
     *
     * @param engineSku    the engine sku
     * @param baseMoney    the base money
     * @param reduceToMoney reduceToMoney
     * @param paramMap     the param map
     * @return money money
     */
    @AbilityExtension(name = "【价格域-扩展点】计算实付金额，sku减至优惠", code = EXT_POINT_CAL_REMAIN_MONEY_BY_SKU_REDUCE_TO,
        desc = "【价格域-扩展点】计算实付金额，sku减至优惠")
    Money calRemainMoneyBySkuReduceTo(EngineSku engineSku, Money baseMoney, long reduceToMoney,
                                      Map<String, String> paramMap);


    String EXT_POINT_CAL_SUBSIDY_MONEY
            = "com.lucifiere.dme.bcp.ump.biz.domain.price.ext.IPriceBusinessExt.calSubSidyMoney";

    /**
     * 计算补贴金额
     *
     * @param engineSku    the engine sku
     * @param paramMap     the param map
     * @return money money
     */
    @AbilityExtension(name = "【价格域-扩展点】计算补贴金额，sku补贴优惠", code = EXT_POINT_CAL_SUBSIDY_MONEY,
            desc = "【价格域-扩展点】计算补贴金额，sku补贴优惠")
    Money calSubSidyMoney(EngineSku engineSku, Map<String, String> paramMap);
}

package com.lucifiere.biz.activities.price;

import com.lucifiere.biz.activities.constants.ThemisFunctionNameConstants;
import com.lucifiere.engine.EngineContext;
import com.lucifiere.engine.annotation.ActivityFuncDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 价格域活动计算组件
 *
 * @author 忠魂
 */
@Component
public class PriceActivity {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(PriceActivity.class);
    
//    @Autowired
//    private PriceDomainService priceDomainService;

    @ActivityFuncDefine(code = "com.lucifiere.dme.bef.bactivities.price.PriceActivity.discount",
        macroName = ThemisFunctionNameConstants.PriceDomainExpressName.DISCOUNT, desc = "打折计算")
    public void discount(EngineContext context) {
//        PriceModel priceContext = PriceConverter.convertToPriceContext(context);
//        priceDomainService.discount(priceContext);
    }

    @ActivityFuncDefine(code = "PriceActivity.skuReduce",
        macroName = ThemisFunctionNameConstants.PriceDomainExpressName.REDUCE, desc = "立减计算")
    public void reduce(EngineContext context) {
//        PriceModel priceContext = PriceConverter.convertToPriceContext(context);
//        priceDomainService.reduce(priceContext);
    }

    @ActivityFuncDefine(code = "PriceActivity.skuReduceTo",
        macroName = ThemisFunctionNameConstants.PriceDomainExpressName.REDUCE_TO, desc = "减至计算")
    public void reduceTo(EngineContext context) {
//        PriceModel priceContext = PriceConverter.convertToPriceContext(context);
//        priceDomainService.reduceTo(priceContext);
    }

    @ActivityFuncDefine(code = "PriceActivity.lowestPlus",
        macroName = ThemisFunctionNameConstants.PriceDomainExpressName.LOWEST_PLUS, desc = "最低价格加计算")
    public void lowestPlus(EngineContext context) {
//        PriceModel priceContext = PriceConverter.convertToPriceContext(context);
//        priceDomainService.lowestPlus(priceContext);
    }

    @ActivityFuncDefine(code = "PriceActivity.agentFee",
        macroName = ThemisFunctionNameConstants.PriceDomainExpressName.AGENT_FEE, desc = "代收费计算")
    public void agentFee(EngineContext context) {
//        PriceModel priceContext = PriceConverter.convertToPriceContext(context);
//        priceDomainService.agentFee(priceContext);
    }

    @ActivityFuncDefine(code = "PriceActivity.serviceFee",
        macroName = ThemisFunctionNameConstants.PriceDomainExpressName.SERVICE_FEE, desc = "服务费计算")
    public void serviceFee(EngineContext context) {
//        PriceModel priceContext = PriceConverter.convertToPriceContext(context);
//        priceDomainService.serviceFee(priceContext);
    }

    @ActivityFuncDefine(code = "PriceActivity.subsidy",
        macroName = ThemisFunctionNameConstants.PriceDomainExpressName.SUBSIDY, desc = "补贴计算")
    public void subsidy(EngineContext context) {
//        PriceModel priceContext = PriceConverter.convertToPriceContext(context);
//        priceDomainService.subsidy(priceContext);
    }

}

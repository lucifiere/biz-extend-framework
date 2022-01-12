package com.lucifiere.biz.activities.promotion;

import com.lucifiere.biz.model.EngineSkuResult;
import com.lucifiere.engine.EngineContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 优惠域节点
 *
 * @author 沾清
 * @version 1.0
 * @since 1.0 2019年4月8日
 */
@Component
public class PromotionActivity {

//    @Autowired
//    private PromotionDomainService promotionDomainService;

//    public List<EngineContext> createCalPromoEngineContext(ExtRuleInfo extRuleInfo, List<EngineSkuResult> engineSkuResultList) {
//        CalPromoEngineContextVO model = PromotionRequestBuilder.buildCalPromoEngineContextReq(extRuleInfo, engineSkuResultList);
//        return promotionDomainService.createPromoEngineContext(model);
//    }

//    public List<EngineContext> createQueryPromoEngineContext(List<ThemisExtVO> ruleDetailList, SkuSearchCond queryCond) {
//        QueryPromoEngineContextVO model = PromotionRequestBuilder.buildQueryPromoEngineContextReq(ruleDetailList, queryCond);
//        return promotionDomainService.createPromoEngineContext(model);
//    }
//
//    public List<EngineContext> createPreHandlePromoEngineContext(List<EngineSku> engineSkus, List<ExtRuleInfo> extRuleInfos) {
//        PreHandlePromoEngineContextVO model = PromotionRequestBuilder.buildPreHandlePromoEngineContextVOReq(engineSkus, extRuleInfos);
//        return promotionDomainService.createPromoEngineContext(model);
//    }

}

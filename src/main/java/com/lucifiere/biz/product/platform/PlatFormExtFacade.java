package com.lucifiere.biz.product.platform;

import com.lucifiere.bef.specific.BusinessExtFacade;
import com.lucifiere.bef.template.TemplateExt;
import com.lucifiere.biz.domain.ext.PriceDomainBusinessExt;
import com.lucifiere.biz.product.platform.ext.PlatFormPriceBusinessExt;

/**
 * 平台各域扩展点SDK
 *
 * @author 忠魂
 */
@TemplateExt(code = PlatFormProduct.CODE)
public class PlatFormExtFacade implements BusinessExtFacade {

    /**
     * 价格域扩展点SDK
     */
    public PriceDomainBusinessExt getPriceDomainBusinessExt() {
        return new PlatFormPriceBusinessExt();
    }
//
//    /**
//     * 限购域扩展点SDK
//     */
//    public BuyLimitDomainBusinessExt getBuyLimitDomainBusinessExt() {
//        return new PlatFormBuyLimitBusinessExt();
//    }
//
//    /**
//     * 场景域扩展点SDK
//     * @return
//     */
//    public SceneDomainBusinessExt getSceneDomainBusinessExt(){
//        return new PlatFormSceneBusinessExt();
//    }
}

package com.lucifiere.biz.product.card.benefit.ext;

import java.util.List;

/**
 * 权益卡场景域
 *
 * @author 和同
 */
public class BenefitCardSceneBusinessExt extends PlatFormSceneBusinessExt {

    @Override
    protected List<BaseSceneCheckHandler> acquireHandlerChain() {
        List<BaseSceneCheckHandler> handlers = super.acquireHandlerChain();
        handlers.add(new UnavailableCardAllowPromoCheckHandler());
        return handlers;
    }

}

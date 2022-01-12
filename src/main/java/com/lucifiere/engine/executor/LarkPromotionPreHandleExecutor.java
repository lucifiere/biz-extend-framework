package com.lucifiere.engine.executor;

import com.lucifiere.dme.bcp.ump.biz.activities.promotion.PromotionActivity;
import com.lucifiere.dme.bcp.ump.biz.engine.EngineContext;
import com.lucifiere.dme.bcp.ump.biz.engine.EngineExecResult;
import com.lucifiere.dme.bcp.ump.biz.engine.PromotionInstance;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.context.PromotionExecContext;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.context.PromotionPreHandleContext;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.req.executor.PromotionExecPreHandleReq;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.req.executor.PromotionExecReq;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.resp.executor.PromotionExecResp;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.resp.executor.PromotionPreHandleExecResp;
import com.lucifiere.dme.bcp.ump.biz.interceptor.RtWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 淘好票计算前规则执行器
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年8月1日
 */
@Component
public class LarkPromotionPreHandleExecutor extends AbstractPromotionExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LarkPromotionPreHandleExecutor.class);

    @Autowired
    private PromotionActivity promotionActivity;

    @Override
    protected PromotionInstance.ExceptionStrategy getExceptionStrategy() {
        return PromotionInstance.ExceptionStrategy.BREAK;
    }

    @Override
    protected List<EngineContext> createEngineContext(PromotionExecContext context) {
        PromotionPreHandleContext preHandleContext = (PromotionPreHandleContext) context;
        return promotionActivity.createPreHandlePromoEngineContext(preHandleContext.getEngineSkuList(), preHandleContext.getRuleDict());
    }

    @Override
    protected void processAfterEngineExecute(EngineContext engineContext, PromotionExecContext context, EngineExecResult execResp) {
        PromotionPreHandleContext preHandleContext = (PromotionPreHandleContext) context;
        preHandleContext.setRuleCases(engineContext.getRuleContext().getRuleCases());
    }

    @Override
    @RtWatcher("计算前规则处理执行器")
    public PromotionExecResp<PromotionPreHandleExecResp> exec(PromotionExecReq request) {
        LOGGER.info("开始执行计算前规则处理...");
        PromotionExecPreHandleReq preHandleReq = (PromotionExecPreHandleReq) request;
        PromotionPreHandleContext context = new PromotionPreHandleContext();
        context.setEngineSkuList(preHandleReq.getEngineSkuList());
        context.setRuleDict(preHandleReq.getExtRuleInfo());
        super.calRulePromotion(context);
        return new PromotionExecResp<>(new PromotionPreHandleExecResp(context.getRuleCases(), context.getRuleDict()));
    }

}

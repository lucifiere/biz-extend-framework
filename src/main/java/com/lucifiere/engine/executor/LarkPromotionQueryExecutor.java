package com.lucifiere.engine.executor;

import com.lucifiere.biz.activities.promotion.PromotionActivity;
import com.lucifiere.engine.EngineContext;
import com.lucifiere.engine.PromotionInstance;
import com.lucifiere.engine.executor.context.PromotionExecContext;
import com.lucifiere.engine.executor.context.PromotionQueryContext;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 淘好票优惠执行器
 * 查询可用优惠
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年6月13日
 */
@Component
public class LarkPromotionQueryExecutor extends AbstractPromotionExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPromotionExecutor.class);

    @Autowired
    private PromotionActivity promotionActivity;

    @Override
    public PromotionExecResp<PromotionDetailResult> exec(PromotionExecReq req) {
        PromotionExecQueryReq queryReq = (PromotionExecQueryReq) req;
        PromotionQueryContext context = new PromotionQueryContext();
        context.setExtList(queryReq.getExtList());
        context.setSearchCond(queryReq.getSkuSearchCond());
        super.calRulePromotion(context);
        return new PromotionExecResp<>(context.getPromotionDetailResults());
    }

    @Override
    protected PromotionInstance.ExceptionStrategy getExceptionStrategy() {
        return PromotionInstance.ExceptionStrategy.IGNORE_BIZ;
    }

    @Override
    protected List<EngineContext> createEngineContext(PromotionExecContext context) {
        PromotionQueryContext req = (PromotionQueryContext) context;
        SkuSearchCond queryCond = req.getSearchCond();
        return promotionActivity.createQueryPromoEngineContext(req.getExtList(), queryCond);
    }

    @Override
    protected void processAfterEngineExecute(EngineContext engineContext, PromotionExecContext context, EngineExecResult execResp) {
        List<SceneResult> results = engineContext.getSceneContext().getSceneResults();
        List<SceneResult> allFailed = results.stream().filter(SceneResult::ineligible).collect(Collectors.toList());
        PromotionQueryContext queryContext = (PromotionQueryContext) context;
        PromotionDetailResult result = new PromotionDetailResult();
        result.setExtId(engineContext.getPromotionIdentity().getExtId());
        result.setSystemSource(engineContext.getSceneContext().getSceneQueryCond().getSystemSource());
        result.setBusinessLineCode(engineContext.getSceneContext().getSceneQueryCond().getBusinessLineCode());
        // 如果引擎执行后有失败的场景域Case，则将其包装成提示语送给上层
        if (CollectionUtils.isNotEmpty(allFailed)) {
            List<SceneCheckResultEnum> failMsg = allFailed.stream().map(SceneResult::getResult).collect(Collectors.toList());
            String tip = SceneTools.buildSceneCheckTip(failMsg);
            result.setFailMsgEnum(failMsg);
            result.setSuccess(false);
            result.setFailMsg(tip);
            AsyncTransientLogHelper.log("优惠" + engineContext.getPromotionIdentity() + "校验不通过 --> 原因：" + tip);
        }
        // 如果引擎执行后没有失败的场景域Case，表示校验通过，将规则包装成PromotionDetailInfo对象交给上层
        else {
            AsyncTransientLogHelper.log("优惠" + engineContext.getPromotionIdentity() + "校验通过...");
            result.setSuccess(true);
            result.setPromotionDetailInfo(SceneTools.buildPromotionDetailInfo(engineContext.getSceneContext(), engineContext.getPromotionContext()));
        }
        queryContext.addPromotionQueryResult(result);
    }

}

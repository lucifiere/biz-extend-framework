package com.lucifiere.engine.executor;

import com.lucifiere.dme.bcp.ump.api.request.lark.cal.ExtRuleInfo;
import com.lucifiere.dme.bcp.ump.api.result.ThemisSkuResult;
import com.lucifiere.dme.bcp.ump.biz.activities.promotion.PromotionActivity;
import com.lucifiere.dme.bcp.ump.biz.cal.model.EngineSku;
import com.lucifiere.dme.bcp.ump.biz.cal.model.EngineSkuResult;
import com.lucifiere.dme.bcp.ump.biz.cal.model.EngineSkuSnapshot;
import com.lucifiere.dme.bcp.ump.biz.engine.EngineContext;
import com.lucifiere.dme.bcp.ump.biz.engine.EngineExecResult;
import com.lucifiere.dme.bcp.ump.biz.engine.PromotionInstance;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.context.PromotionCalContext;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.context.PromotionExecContext;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.req.executor.PromotionExecCalReq;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.req.executor.PromotionExecReq;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.resp.executor.PromotionExecResp;
import com.lucifiere.dme.bcp.ump.biz.interceptor.RtWatcher;
import com.lucifiere.dme.bcp.ump.biz.util.EngineDomainConvertUtil;
import com.lucifiere.dme.bcp.ump.common.utils.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 淘好票优惠执行器
 * 循环有序的规则，为符合相应规则的商品执行优惠逻辑
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年6月13日
 */
@Component
public class LarkPromotionCalExecutor extends AbstractPromotionExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LarkPromotionCalExecutor.class);

    @Autowired
    private PromotionActivity promotionActivity;

    /**
     * 执行优惠
     *
     * @param req 执行器请求参数
     * @return 每个商品的优惠结果
     */
    @Override
    @RtWatcher("价格计算执行器")
    public PromotionExecResp<ThemisSkuResult> exec(PromotionExecReq req) {
        LOGGER.info("开始执价格计算...");
        PromotionExecCalReq calReq = (PromotionExecCalReq) req;
        List<ExtRuleInfo> sortedRules = calReq.getExtRuleInfo();
        List<EngineSku> engineSkuList = calReq.getEngineSkuList();
        // 目前只算上游要求算的，以后去掉
        engineSkuList = engineSkuList.stream().filter(EngineSku::isOriginal).collect(Collectors.toList());
        // 将所有sku进行计算
        List<EngineSkuResult> engineSkuResultList = EngineDomainConvertUtil.engineConvert2EngineResult(engineSkuList);
        Map<String, List<EngineSkuSnapshot>> skuSnapShotsMap = new HashMap<>(16);
        Map<String, EngineSkuResult> resultMap = MapUtils.singleGroupByFiled(engineSkuResultList, EngineSkuResult::getId);
        execPromotion(sortedRules, resultMap, skuSnapShotsMap);
        List<ThemisSkuResult> results = EngineDomainConvertUtil.engineResultConvert2SkuResult(new ArrayList<>(resultMap.values()), skuSnapShotsMap);
        return new PromotionExecResp<>(results);
    }

    @Override
    protected List<EngineContext> createEngineContext(PromotionExecContext context) {
        PromotionCalContext req = (PromotionCalContext) context;
        return promotionActivity.createCalPromoEngineContext(req.getCurRule(), new ArrayList<>(req.getResultMap().values()));
    }

    @Override
    protected void processAfterEngineExecute(EngineContext engineContext, PromotionExecContext context, EngineExecResult execResp) {
        PromotionCalContext calContext = (PromotionCalContext) context;
        List<EngineSku> skuList = engineContext.getPromotionContext().getEngineSkuList();
        refreshSkuResult(skuList, calContext.getResultMap(), calContext.getCurRule());
        refreshSkuSnapshot(skuList, calContext.getSkuSnapShotsMap());
    }

    @Override
    protected PromotionInstance.ExceptionStrategy getExceptionStrategy() {
        return PromotionInstance.ExceptionStrategy.BREAK;
    }

    private void execPromotion(List<ExtRuleInfo> rules, Map<String, EngineSkuResult> skuMap, Map<String, List<EngineSkuSnapshot>> snapShotsMap) {
        // 循环，叠加运算结果
        for (ExtRuleInfo curRule : rules) {
            PromotionCalContext context = new PromotionCalContext();
            context.setResultMap(skuMap);
            context.setSkuSnapShotsMap(snapShotsMap);
            context.setCurRule(curRule);
            super.calRulePromotion(context);
        }
    }

    /**
     * 通过引擎计算结果来更新商品快照信息，更新后的快照数据将用于下一轮规则的计算
     *
     * @param engineSkuList   计算结果
     * @param skuSnapShotsMap 商品的快照
     */
    private void refreshSkuSnapshot(List<EngineSku> engineSkuList, Map<String, List<EngineSkuSnapshot>> skuSnapShotsMap) {
        for (EngineSku engineSku : engineSkuList) {
            List<EngineSkuSnapshot> skuSnapshotList = skuSnapShotsMap.get(engineSku.getId());
            if (!CollectionUtils.isEmpty(skuSnapshotList)) {
                skuSnapshotList.add(engineSku.getEngineSkuSnapshot());
            } else {
                List<EngineSkuSnapshot> engineSkuSnapshotList = new ArrayList<>();
                engineSkuSnapshotList.add(engineSku.getEngineSkuSnapshot());
                skuSnapShotsMap.put(engineSku.getId(), engineSkuSnapshotList);
            }
        }
    }

    /**
     * 通过引擎计算结果来更新商品信息，更新后的商品数据将用于下一轮规则的计算
     *
     * @param curRule       当前执行的规则
     * @param engineSkuList 计算结果
     * @param resultMap     商品的信息
     */
    private void refreshSkuResult(List<EngineSku> engineSkuList, Map<String, EngineSkuResult> resultMap, ExtRuleInfo curRule) {
        for (EngineSku engineSku : engineSkuList) {
            if (engineSku.getCanPromotion()) {
                EngineSkuResult engineSkuResult = EngineDomainConvertUtil.engineConvert2EngineResult(engineSku);
                assert engineSkuResult != null;
                engineSkuResult.addPartakeType(curRule.getRuleType());
                resultMap.put(engineSkuResult.getId(), engineSkuResult);
            }
        }
    }

}

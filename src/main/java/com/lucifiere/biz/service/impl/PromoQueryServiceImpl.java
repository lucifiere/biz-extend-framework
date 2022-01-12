package com.lucifiere.biz.service.impl;

import com.lucifiere.dme.bcp.ump.api.constant.common.SceneCheckResultEnum;
import com.lucifiere.dme.bcp.ump.api.request.PromoToolsRequest;
import com.lucifiere.dme.bcp.ump.api.request.SkuRequest;
import com.lucifiere.dme.bcp.ump.api.request.lark.cal.LarkCalculateContext;
import com.lucifiere.dme.bcp.ump.api.result.PromotionDetailResult;
import com.lucifiere.dme.bcp.ump.api.result.SkuPromotionResult;
import com.lucifiere.dme.bcp.ump.api.result.ThemisSkuResult;
import com.lucifiere.dme.bcp.ump.api.result.lark.LarkCalculateResult;
import com.lucifiere.dme.bcp.ump.bef.util.SpringApplicationContextHolder;
import com.lucifiere.dme.bcp.ump.biz.cal.model.EngineSku;
import com.lucifiere.dme.bcp.ump.biz.domain.vo.*;
import com.lucifiere.dme.bcp.ump.biz.functions.price.util.PricePromotionTools;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.executor.LarkPromotionCalExecutor;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.executor.LarkPromotionPreHandleExecutor;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.executor.LarkPromotionQueryExecutor;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.executor.PromotionExecutor;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.req.executor.PromotionExecCalReq;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.req.executor.PromotionExecPreHandleReq;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.req.executor.PromotionExecQueryReq;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.resp.executor.PromotionExecResp;
import com.lucifiere.dme.bcp.ump.biz.functions.promotion.model.resp.executor.PromotionPreHandleExecResp;
import com.lucifiere.dme.bcp.ump.biz.functions.scene.utils.RuleFilter;
import com.lucifiere.dme.bcp.ump.biz.functions.scene.utils.SceneTools;
import com.lucifiere.dme.bcp.ump.biz.interceptor.RtWatcher;
import com.lucifiere.dme.bcp.ump.biz.service.PromoQueryService;
import com.lucifiere.dme.bcp.ump.biz.service.ThemisExtQueryService;
import com.lucifiere.dme.bcp.ump.biz.util.EngineDomainConvertUtil;
import com.lucifiere.dme.bcp.ump.biz.util.FacadeResultBuilder;
import com.lucifiere.dme.bcp.ump.biz.util.ThemisCheckUtil;
import com.lucifiere.dme.bcp.ump.common.csp.AsyncTransientLogHelper;
import com.lucifiere.dme.bcp.ump.common.utils.MapUtils;
import com.lucifiere.dme.bcp.ump.repository.entity.ThemisExt;
import com.lucifiere.dme.bcp.ump.repository.entity.ThemisRuleDetail;
import com.lucifiere.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.lucifiere.dme.bcp.ump.biz.functions.scene.utils.RuleFilter.filter;

/**
 * 价格计算接口
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年4月1日
 */
@Service
public class PromoQueryServiceImpl implements PromoQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PromoQueryServiceImpl.class);

    @Autowired
    private ThemisExtQueryService themisRuleQueryService;

    @Override
    public LarkCalculateResult calPromoPrice(LarkCalculateContext calContext) {
        List<EngineSku> engineSkuList = EngineDomainConvertUtil.skuDTOConvert2Engine(calContext.getSkuDTOList());
        // 计算前规则处理
        PromotionExecutor<PromotionPreHandleExecResp> preHandleExecutor = SpringApplicationContextHolder.getSpringBean(LarkPromotionPreHandleExecutor.class);
        PromotionExecPreHandleReq preHandleReq = new PromotionExecPreHandleReq();
        preHandleReq.setEngineSkuList(engineSkuList);
        preHandleReq.setExtRuleInfo(calContext.getRuleInfoList());
        PromotionExecResp<PromotionPreHandleExecResp> preHandleResult = preHandleExecutor.exec(preHandleReq);
        // 入参转换完毕后，组装优惠价格计算参数
        PromotionExecutor<ThemisSkuResult> calExecutor = SpringApplicationContextHolder.getSpringBean(LarkPromotionCalExecutor.class);
        PromotionExecCalReq calReq = new PromotionExecCalReq();
        calReq.setExtRuleInfo(preHandleResult.getSingleModel().getRuleDict());
        calReq.setEngineSkuList(PricePromotionTools.convertRuleCaseToEngineSkuList(preHandleResult.getSingleModel().getRuleCases()));
        PromotionExecResp<ThemisSkuResult> calResults = calExecutor.exec(calReq);
        // 拿到结果返回
        return FacadeResultBuilder.buildLarkCalculateResult(calResults.getMultiModel());
    }

    @Override
    public List<SkuPromotionResult> findAvailablePromoTool(List<SkuRequest> skuList, List<PromoToolsRequest> tools) {
        Stopwatch watchFilterRule = Stopwatch.createStarted();
        Map<String, List<RuleQueryResultVO>> skuRuleMap = filterAvailableThemisExt(skuList, tools);
        LOGGER.info("从表过滤耗时为：" + watchFilterRule.elapsed(TimeUnit.MILLISECONDS) + "ms");
        if (MapUtils.isEmpty(skuRuleMap)) {
            LOGGER.warn("未能查到任何可用规则数据！");
            return null;
        }
        // 每个SKU算一次适用规则
        List<SkuPromotionResult> results = Lists.newCopyOnWriteArrayList();
        Stopwatch sceneDomainWatcher = Stopwatch.createStarted();
        skuList.parallelStream().forEach(skuReq -> {
            List<PromotionDetailResult> ruleFilterResult = new ArrayList<>();
            // 处理从表过滤结果
            List<RuleQueryResultVO> unmatchedResult = skuRuleMap.get(skuReq.getSkuCode()).stream().filter(it -> !it.getSuccess()).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(unmatchedResult)) {
                ruleFilterResult.addAll(FacadeResultBuilder.convertToUnmatchedQueryResult(unmatchedResult));
            }
            // 处理场景域过滤结果
            List<ThemisExtVO> matchedResult = skuRuleMap.get(skuReq.getSkuCode()).stream().filter(RuleQueryResultVO::getSuccess).map(RuleQueryResultVO::getResults).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(matchedResult)) {
                List<PromotionDetailResult> ruleSceneCheckResults = findAvailablePromoTools4Sku(skuReq, matchedResult);
                if (CollectionUtils.isNotEmpty(ruleSceneCheckResults)) {
                    ruleFilterResult.addAll(ruleSceneCheckResults);
                }
            }
            // 将处理后的结果包装成出参
            SkuPromotionResult queryResult = FacadeResultBuilder.buildSkuPromotionResultList(ruleFilterResult, skuReq);
            results.add(queryResult);
        });
        LOGGER.info("场景域过滤总耗时为：" + sceneDomainWatcher.elapsed(TimeUnit.MILLISECONDS) + "ms");
        return results;
    }

    @Override
    public List<PromotionDetailResult> findAvailablePromoTools4Sku(SkuRequest sku, List<ThemisExtVO> extList) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ThemisCheckUtil.skuRequestCheck4AvailablePromoTools(sku);
        // 入参转换完毕后，组装优惠查询计算参数
        PromotionExecutor<PromotionDetailResult> queryExecutor = SpringApplicationContextHolder.getSpringBean(LarkPromotionQueryExecutor.class);
        PromotionExecQueryReq execQueryReq = new PromotionExecQueryReq();
        execQueryReq.setExtList(extList);
        execQueryReq.setSkuSearchCond(SkuSearchCond.toSearchCond(sku));
        PromotionExecResp<PromotionDetailResult> executeResults = queryExecutor.exec(execQueryReq);
        AsyncTransientLogHelper.debug("【性能测试】：商品" + sku.getSkuCode() + "的场景域处理总耗时为：" + stopwatch.elapsed(TimeUnit.MILLISECONDS) +
                "；处理的规则数为：" + extList.stream().filter(Objects::nonNull).flatMap(it -> it.getThemisRuleVOList().stream())
                .filter(Objects::nonNull).mapToLong(it -> it.getThemisRuleDetailVOList().size()).sum());
        // 拿到结果返回
        return executeResults.getMultiModel();
    }

    /**
     * 从数据库过滤出指定商品可用的规则
     *
     * @param skuReqs  商品
     * @param toolReqs 未过滤的营销工具
     * @return 可用规则
     */
    @Override
    @RtWatcher("从表过滤")
    public Map<String, List<RuleQueryResultVO>> filterAvailableThemisExt(List<SkuRequest> skuReqs, List<PromoToolsRequest> toolReqs) {
        // 根据外部传来的EXT-ID查出所有规则，并标识出未查询到的规则
        List<String> extIds = toolReqs.stream().map(PromoToolsRequest::getExtId).collect(Collectors.toList());
        List<ThemisExt> extQueryCond = toolReqs.stream().map(it -> {
            ThemisExt query = new ThemisExt();
            query.setBusinessLineCode(it.getBusinessLineCode());
            query.setExtId(it.getExtId());
            query.setSystemSource(it.getSystemSource());
            return query;
        }).collect(Collectors.toList());
        List<ThemisExtVO> extList = themisRuleQueryService.getThemisExtList(extQueryCond);
        List<String> missingExtId = new ArrayList<>(extIds);
        if (CollectionUtils.isNotEmpty(extList)) {
            List<String> existExtId = extList.stream().map(ThemisExtVO::getExtId).collect(Collectors.toList());
            LOGGER.info("参与从表条件过滤的EXT规则ID --> " + existExtId);
            missingExtId.removeAll(existExtId);
        }
        // 初始化查询结果对象
        Map<String, List<RuleQueryResultVO>> results = SceneTools.initRuleQueryResultMapWithMissingExt(skuReqs, missingExtId);
        if (CollectionUtils.isEmpty(extList)) {
            return results;
        }
        List<Long> existIds = extList.stream().map(ThemisExtVO::getId).collect(Collectors.toList());
        Map<Long, List<ThemisRuleVO>> ruleMapping = themisRuleQueryService.getThemisRuleMapping(existIds);

        LOGGER.info("待过滤的Rule为 --> " + JSON.toJSONString(ruleMapping));

        List<Long> ruleIds = ruleMapping.values().stream().flatMap(Collection::stream).map(ThemisRuleVO::getId).collect(Collectors.toList());
        // 根据外界参数补全EXT规则缺失的信息
        Map<String, PromoToolsRequest> toolMap = MapUtils.singleGroupByFiled(toolReqs, PromoToolsRequest::getExtId);
        extList.forEach(ext -> {
            PromoToolsRequest tool = toolMap.get(ext.getExtId());
            if (tool != null) {
                ext.setToolQueryCond(ToolSearchCond.toToolSearchCond(tool));
            }
        });
        extList.forEach(ext -> ext.setThemisRuleVOList(ruleMapping.get(ext.getId())));
        // 建立商品和规则的映射关系，用于后续过滤
        Map<SkuRequest, List<ThemisExtVO>> skuRuleMap = createSkuAndThemisExtMapping(skuReqs, extList);
        // 过滤渠道
        Map<Long, List<ThemisRuleChannelVO>> channelMapping = themisRuleQueryService.getThemisRuleChannelMapping(ruleIds);
        filter(results, skuRuleMap, SceneCheckResultEnum.CHANNEL_NOT_MATCH, context -> RuleFilter.filterChannel(context, channelMapping));

        // 过滤地点
        Map<Long, List<ThemisRulePlaceVO>> placeMapping = themisRuleQueryService.getThemisRulePlaceMapping(ruleIds);
        filter(results, skuRuleMap, SceneCheckResultEnum.PLACE_NOT_MATCH, context -> RuleFilter.filterPlace(context, placeMapping));

        // 过滤商品
        Map<Long, List<ThemisRuleItemVO>> itemMapping = themisRuleQueryService.getThemisRuleItemMapping(ruleIds);
        filter(results, skuRuleMap, SceneCheckResultEnum.ITEM_NOT_MATCH, context -> RuleFilter.filterItem(context, itemMapping));

        // 为通过过滤的ThemisRuleVO查询Detail成员
        List<ThemisRuleVO> availableRule = skuRuleMap.values().stream().flatMap(Collection::stream).filter(it -> CollectionUtils.isNotEmpty(it.getThemisRuleVOList())).flatMap(it -> it.getThemisRuleVOList().stream()).filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(availableRule)) {
            List<Long> availableRuleIds = availableRule.stream().map(ThemisRuleVO::getId).collect(Collectors.toList());
            Map<Long, List<ThemisRuleDetail>> detailMap = themisRuleQueryService.getThemisRuleDetailMapping(availableRuleIds);

            // 过滤适用商品
            filter(results, skuRuleMap, SceneCheckResultEnum.PRODUCT_TYPE_NOT_MATCH, context -> RuleFilter.filterDetail(context, detailMap));
            availableRule = skuRuleMap.values().stream().flatMap(Collection::stream).filter(it -> CollectionUtils.isNotEmpty(it.getThemisRuleVOList())).flatMap(it -> it.getThemisRuleVOList().stream()).filter(Objects::nonNull).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(availableRule)) {
                availableRule.forEach(rule -> rule.setThemisRuleDetailVOList(detailMap.get(rule.getId())));
            }
        }
        return results;
    }

    /**
     * 根据SKU-CODE过滤掉重复的SKU
     *
     * @param skuReqs 查询条件
     * @return 不重复的SKU
     */
    private List<SkuRequest> filterDuplicateSkuCondition(List<SkuRequest> skuReqs) {
        if (CollectionUtils.isEmpty(skuReqs)) {
            return skuReqs;
        }
        Set<String> skuCodeSet = new HashSet<>();
        // 这种写法不好，直接用Set类add方法的返回值就能确定重复性了，下期优化更简单的写法
        return skuReqs.stream().filter(it -> {
            if (skuCodeSet.contains(it.getSkuCode())) {
                return false;
            } else {
                skuCodeSet.add(it.getSkuCode());
                return true;
            }
        }).collect(Collectors.toList());
    }

    /**
     * 组装SKU和外部规则的映射
     *
     * @param skuReqs SKU信息
     * @param list    外部规则
     * @return 映射
     */
    private Map<SkuRequest, List<ThemisExtVO>> createSkuAndThemisExtMapping(List<SkuRequest> skuReqs, List<ThemisExtVO> list) {
        // 从库里面查原始数据，所有目标规则活动EXT
        if (CollectionUtils.isEmpty(skuReqs) || CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        // 转为MAP
        skuReqs = filterDuplicateSkuCondition(skuReqs);
        Map<SkuRequest, List<ThemisExtVO>> results = Maps.newHashMap();
        skuReqs.forEach(req -> results.put(req, newTheSameExtListCopies(list)));
        return results;
    }

    private List<ThemisExtVO> newTheSameExtListCopies(List<ThemisExtVO> source) {
        return source.stream().map(s -> {
            ThemisExtVO extVO = new ThemisExtVO();
            BeanUtils.copyProperties(s, extVO);
            return extVO;
        }).collect(Collectors.toList());
    }

}

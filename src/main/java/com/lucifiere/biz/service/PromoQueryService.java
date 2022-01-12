package com.lucifiere.biz.service;

import com.lucifiere.dme.bcp.ump.api.request.PromoToolsRequest;
import com.lucifiere.dme.bcp.ump.api.request.SkuRequest;
import com.lucifiere.dme.bcp.ump.api.request.lark.cal.LarkCalculateContext;
import com.lucifiere.dme.bcp.ump.api.result.PromotionDetailResult;
import com.lucifiere.dme.bcp.ump.api.result.SkuPromotionResult;
import com.lucifiere.dme.bcp.ump.api.result.lark.LarkCalculateResult;
import com.lucifiere.dme.bcp.ump.biz.domain.vo.RuleQueryResultVO;
import com.lucifiere.dme.bcp.ump.biz.domain.vo.ThemisExtVO;

import java.util.List;
import java.util.Map;

/**
 * 价格计算接口
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年4月1日
 */
public interface PromoQueryService {

    /**
     * 价格计算接口
     *
     * @param calContext 规则信息和商品信息
     * @return 各商品的计算结果
     */
    LarkCalculateResult calPromoPrice(LarkCalculateContext calContext);

    /**
     * 该接口为每个商品过滤出可使用的营销工具（规则）
     * 若发现过滤后无任何可用的规则，返回不可用原因；否则返回可用营销规则的详细信息
     * QWQ
     *
     * @param skuList 指定的商品（批量）
     * @param tools   待查询的营销工具
     * @return 可用的营销工具/不可用原因
     */
    List<SkuPromotionResult> findAvailablePromoTool(List<SkuRequest> skuList, List<PromoToolsRequest> tools);

    /**
     * 该接口为每个商品过滤出可使用的营销工具（规则）
     * 若发现过滤后无任何可用的规则，返回不可用原因；否则返回可用营销规则的详细信息
     *
     * @param sku     指定的商品（单个）
     * @param extList 待判断的营销规则
     * @return 可用的营销工具/不可用原因
     */
    List<PromotionDetailResult> findAvailablePromoTools4Sku(SkuRequest sku, List<ThemisExtVO> extList);

    /**
     * 从数据库过滤出指定商品可用的规则
     *
     * @param skuReqs  商品
     * @param toolReqs 未过滤的营销工具
     * @return 可用规则
     */
    Map<String, List<RuleQueryResultVO>> filterAvailableThemisExt(List<SkuRequest> skuReqs, List<PromoToolsRequest> toolReqs);

}

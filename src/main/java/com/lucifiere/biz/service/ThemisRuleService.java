package com.lucifiere.biz.service;


import com.lucifiere.dme.bcp.ump.api.request.BaseRuleQueryParam;
import com.lucifiere.dme.bcp.ump.api.request.DisableRuleRequest;
import com.lucifiere.dme.bcp.ump.api.request.PromoToolsRequest;
import com.lucifiere.dme.bcp.ump.api.request.SkuRequest;
import com.lucifiere.dme.bcp.ump.api.request.SyncRuleRequest;
import com.lucifiere.dme.bcp.ump.biz.domain.vo.RuleQueryResultResultVO;
import com.lucifiere.dme.bcp.ump.repository.entity.ThemisRuleDetail;
import com.alipic.common.result.BaseResult;

import java.util.List;
import java.util.Map;

/**
 * 规则服务
 *
 * @author arnold.hl
 */
public interface ThemisRuleService {
    /**
     * 同步规则列表
     *
     * @param syncRuleRequest 同步规则
     * @return 结果
     */
    BaseResult syncRule(SyncRuleRequest syncRuleRequest);

    /**
     * 停用规则
     *
     * @param disableRuleRequest 停用规则请求
     * @return
     */
    BaseResult disableRule(DisableRuleRequest disableRuleRequest);

    /**
     * 查询规则--判断当前规则是否满足主规则
     *
     * @param baseRuleQueryParam
     * @return
     */
    RuleQueryResultResultVO queryRuleFilterBaseCondition(BaseRuleQueryParam baseRuleQueryParam);

    /**
     * 按照商品信息查询满足条件的优惠信息
     *
     * @param skuRuleSearch
     * @return
     */
    List<ThemisRuleDetail> queryRuleDetailBySkuCondition(Map<String, Object> skuRuleSearch);

    /**
     * 谨慎使用
     * @param businessLineCode 业务线
     * @param systemSource 系统
     * @param leaseCode 租户
     * @param placeCode 地点
     * @return
     */
    BaseResult deleteRuleRelationshipByPlaceCode(Integer businessLineCode, String systemSource,
                                                 String leaseCode, String placeCode);

}

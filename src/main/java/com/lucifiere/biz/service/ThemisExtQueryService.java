package com.lucifiere.biz.service;

import com.lucifiere.dme.bcp.ump.biz.domain.vo.*;
import com.lucifiere.dme.bcp.ump.repository.entity.ThemisExt;
import com.lucifiere.dme.bcp.ump.repository.entity.ThemisRuleDetail;

import java.util.List;
import java.util.Map;

/**
 * 规则查询服务
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年7月13日
 */
public interface ThemisExtQueryService {

    /**
     * 根据外部ID列表查询完整的外部规则对象
     *
     * @param queryCond 外部ID
     * @return 规则对象
     */
    List<ThemisExtVO> queryThemisExtListByExtIds(List<ThemisExt> queryCond);

    /**
     * 根据规则ID查询规则列表
     *
     * @param ruleIds 规则ID
     * @return 规则ID->规则细节
     */
    List<ThemisRuleVO> getThemisRuleList(List<Long> ruleIds);

    /**
     * 根据规则ID查询规则列表
     *
     * @param queryCond 规则ID
     * @return 规则ID->规则细节
     */
    List<ThemisExtVO> getThemisExtList(List<ThemisExt> queryCond);

    /**
     * 根据规则ID查询规则列表
     *
     * @param ruleIds 规则ID
     * @return 规则ID->规则细节
     */
    Map<Long, List<ThemisRuleVO>> getThemisRuleMapping(List<Long> ruleIds);

    /**
     * 根据规则ID查询 规则ID->规则细节 映射
     *
     * @param ruleIds 规则ID
     * @return 规则ID->规则细节
     */
    Map<Long, List<ThemisRuleDetail>> getThemisRuleDetailMapping(List<Long> ruleIds);

    /**
     * 根据规则ID查询 规则ID->规则适用商品 映射
     *
     * @param ruleIds 规则ID
     * @return 规则ID->规则适用商品
     */
    Map<Long, List<ThemisRuleItemVO>> getThemisRuleItemMapping(List<Long> ruleIds);

    /**
     * 根据规则ID查询 规则ID->规则适用渠道 映射
     *
     * @param ruleIds 规则ID
     * @return 规则ID->规则适用渠道
     */
    Map<Long, List<ThemisRuleChannelVO>> getThemisRuleChannelMapping(List<Long> ruleIds);

    /**
     * 根据规则ID查询 规则ID->规则适用地点 映射
     *
     * @param ruleIds 规则ID
     * @return 规则ID->规则适用地点
     */
    Map<Long, List<ThemisRulePlaceVO>> getThemisRulePlaceMapping(List<Long> ruleIds);

}

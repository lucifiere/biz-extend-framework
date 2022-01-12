package com.lucifiere.biz.service.impl;

import com.lucifiere.dme.bcp.ump.biz.domain.vo.*;
import com.lucifiere.dme.bcp.ump.biz.interceptor.RtWatcher;
import com.lucifiere.dme.bcp.ump.biz.service.ThemisExtQueryService;
import com.lucifiere.dme.bcp.ump.common.utils.MapUtils;
import com.lucifiere.dme.bcp.ump.repository.entity.*;
import com.lucifiere.dme.bcp.ump.repository.mapper.*;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 规则查询服务
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年7月13日
 */
@Service
public class ThemisRuleQueryServiceImpl implements ThemisExtQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThemisRuleQueryServiceImpl.class);

    @Autowired
    private ThemisRuleMapper themisRuleMapper;

    @Autowired
    private ThemisRuleDetailMapper themisRuleDetailMapper;

    @Autowired
    private ThemisRuleChannelMapper themisRuleChannelMapper;

    @Autowired
    private ThemisRuleItemMapper themisRuleItemMapper;

    @Autowired
    private ThemisRulePlaceMapper themisRulePlaceMapper;

    @Autowired
    private ThemisExtMapper themisExtMapper;

    @Override
    @RtWatcher("themis_rule_detail表")
    public Map<Long, List<ThemisRuleDetail>> getThemisRuleDetailMapping(List<Long> ruleIds) {
        List<ThemisRuleDetail> detail = themisRuleDetailMapper.selectByRuleIds(ruleIds);
        if (CollectionUtils.isEmpty(detail)) {
            return Maps.newHashMap();
        }
        return MapUtils.multiGroupByFiled(detail, ThemisRuleDetail::getRuleId);
    }

    @Override
    @RtWatcher("themis_rule_item表")
    public Map<Long, List<ThemisRuleItemVO>> getThemisRuleItemMapping(List<Long> ruleIds) {
        List<ThemisRuleItem> items = themisRuleItemMapper.selectByRuleIds(ruleIds);
        if (CollectionUtils.isEmpty(items)) {
            return Maps.newHashMap();
        }
        List<ThemisRuleItemVO> itemVOList = items.stream().map(entity -> {
            ThemisRuleItemVO vo = new ThemisRuleItemVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
        return MapUtils.multiGroupByFiled(itemVOList, ThemisRuleItemVO::getRuleId);
    }

    @Override
    @RtWatcher("themis_rule_channel表")
    public Map<Long, List<ThemisRuleChannelVO>> getThemisRuleChannelMapping(List<Long> ruleIds) {
        List<ThemisRuleChannel> channels = themisRuleChannelMapper.selectByRuleIds(ruleIds);
        if (CollectionUtils.isEmpty(channels)) {
            return Maps.newHashMap();
        }
        List<ThemisRuleChannelVO> channelVOList = channels.stream().map(entity -> {
            ThemisRuleChannelVO vo = new ThemisRuleChannelVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
        return MapUtils.multiGroupByFiled(channelVOList, ThemisRuleChannelVO::getRuleId);
    }

    @Override
    @RtWatcher("themis_rule_place表")
    public Map<Long, List<ThemisRulePlaceVO>> getThemisRulePlaceMapping(List<Long> ruleIds) {
        List<ThemisRulePlace> places = themisRulePlaceMapper.selectByRuleIds(ruleIds);
        if (CollectionUtils.isEmpty(places)) {
            return Maps.newHashMap();
        }
        List<ThemisRulePlaceVO> placesVOList = places.stream().map(entity -> {
            ThemisRulePlaceVO vo = new ThemisRulePlaceVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
        return MapUtils.multiGroupByFiled(placesVOList, ThemisRulePlaceVO::getRuleId);
    }

    @Override
    @RtWatcher("themis_rule表")
    public Map<Long, List<ThemisRuleVO>> getThemisRuleMapping(List<Long> ruleIds) {
        List<ThemisRuleVO> rules = getThemisRuleList(ruleIds);
        return MapUtils.multiGroupByFiled(rules, ThemisRuleVO::getThemisExtId);
    }

    @Override
    public List<ThemisRuleVO> getThemisRuleList(List<Long> ruleIds) {
        List<ThemisRule> rules = themisRuleMapper.selectRuleListByThemisExtIds(ruleIds);
        if (CollectionUtils.isEmpty(rules)) {
            return Lists.emptyList();
        }
        return rules.stream().map(entity -> {
            ThemisRuleVO detailVO = new ThemisRuleVO();
            BeanUtils.copyProperties(entity, detailVO);
            return detailVO;
        }).collect(Collectors.toList());
    }

    @Override
    @RtWatcher("themis_ext表")
    public List<ThemisExtVO> getThemisExtList(List<ThemisExt> queryCond) {
        List<ThemisExt> extList = themisExtMapper.queryExtList(queryCond);
        if (CollectionUtils.isEmpty(extList)) {
            return Lists.emptyList();
        }
        return extList.stream().map(entity -> {
            ThemisExtVO extVO = new ThemisExtVO();
            BeanUtils.copyProperties(entity, extVO);
            return extVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ThemisExtVO> queryThemisExtListByExtIds(List<ThemisExt> queryCond) {
        // 从数据库查出原始数据
        List<ThemisExt> extList = themisExtMapper.queryExtList(queryCond);
        if (CollectionUtils.isEmpty(extList)) {
            return Lists.emptyList();
        }
        List<ThemisRule> rules = themisRuleMapper.selectRuleListByThemisExtIds(extList.stream().map(ThemisExt::getId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(rules)) {
            return Lists.emptyList();
        }
        List<ThemisRuleDetail> details = themisRuleDetailMapper.selectByRuleIds(rules.stream().map(ThemisRule::getId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(details)) {
            return Lists.emptyList();
        }
        List<ThemisRuleChannel> channels = themisRuleChannelMapper.selectByRuleIds(rules.stream().map(ThemisRule::getId).collect(Collectors.toList()));
        List<ThemisRuleItem> items = themisRuleItemMapper.selectByRuleIds(rules.stream().map(ThemisRule::getId).collect(Collectors.toList()));
        List<ThemisRulePlace> places = themisRulePlaceMapper.selectByRuleIds(rules.stream().map(ThemisRule::getId).collect(Collectors.toList()));
        // 将转Detail、Rule、Ext从DO对象为VO
        List<ThemisRuleVO> ruleVOList = rules.stream().map(entity -> {
            ThemisRuleVO detailVO = new ThemisRuleVO();
            BeanUtils.copyProperties(entity, detailVO);
            return detailVO;
        }).collect(Collectors.toList());
        List<ThemisExtVO> extVOList = extList.stream().map(entity -> {
            ThemisExtVO detailVO = new ThemisExtVO();
            BeanUtils.copyProperties(entity, detailVO);
            return detailVO;
        }).collect(Collectors.toList());
        List<ThemisRuleChannelVO> channelVOList = null;
        if (CollectionUtils.isNotEmpty(channels)) {
            channelVOList = channels.stream().map(entity -> {
                ThemisRuleChannelVO vo = new ThemisRuleChannelVO();
                BeanUtils.copyProperties(entity, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        List<ThemisRuleItemVO> itemVOList = null;
        if (CollectionUtils.isNotEmpty(items)) {
            itemVOList = items.stream().map(entity -> {
                ThemisRuleItemVO vo = new ThemisRuleItemVO();
                BeanUtils.copyProperties(entity, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        List<ThemisRulePlaceVO> placeVOList = null;
        if (CollectionUtils.isNotEmpty(items)) {
            placeVOList = places.stream().map(entity -> {
                ThemisRulePlaceVO vo = new ThemisRulePlaceVO();
                BeanUtils.copyProperties(entity, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        // 根据Detail、Rule和Ext的关系组装各个VO
        Map<Long, List<ThemisRuleDetail>> detailVOMap = MapUtils.multiGroupByFiled(details, ThemisRuleDetail::getRuleId);
        ruleVOList.forEach(rule -> rule.setThemisRuleDetailVOList(detailVOMap.get(rule.getId())));
        if (CollectionUtils.isNotEmpty(channelVOList)) {
            Map<Long, List<ThemisRuleChannelVO>> channelVOMap = MapUtils.multiGroupByFiled(channelVOList, ThemisRuleChannelVO::getRuleId);
            ruleVOList.forEach(rule -> rule.setThemisRuleChannelVOList(channelVOMap.get(rule.getId())));
        }
        if (CollectionUtils.isNotEmpty(itemVOList)) {
            Map<Long, List<ThemisRuleItemVO>> itemVOMap = MapUtils.multiGroupByFiled(itemVOList, ThemisRuleItemVO::getRuleId);
            ruleVOList.forEach(rule -> rule.setThemisRuleItemVOList(itemVOMap.get(rule.getId())));
        }
        if (CollectionUtils.isNotEmpty(placeVOList)) {
            Map<Long, List<ThemisRulePlaceVO>> placeVOMap = MapUtils.multiGroupByFiled(placeVOList, ThemisRulePlaceVO::getRuleId);
            ruleVOList.forEach(rule -> rule.setThemisRulePlaceVOList(placeVOMap.get(rule.getId())));
        }
        Map<Long, List<ThemisRuleVO>> ruleVOMap = MapUtils.multiGroupByFiled(ruleVOList, ThemisRuleVO::getThemisExtId);
        extVOList.forEach(ext -> ext.setThemisRuleVOList(ruleVOMap.get(ext.getId())));
        return extVOList;
    }

}

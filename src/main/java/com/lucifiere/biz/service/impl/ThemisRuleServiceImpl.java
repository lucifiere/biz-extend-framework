package com.lucifiere.biz.service.impl;

import com.lucifiere.dme.bcp.ump.api.constant.common.BusinessLineCode;
import com.lucifiere.dme.bcp.ump.api.constant.common.SystemSource;
import com.lucifiere.dme.bcp.ump.api.constant.rule.RuleStatus;
import com.lucifiere.dme.bcp.ump.api.request.*;
import com.lucifiere.dme.bcp.ump.biz.domain.vo.RuleQueryResultResultVO;
import com.lucifiere.dme.bcp.ump.api.constant.common.SceneCheckResultEnum;
import com.lucifiere.dme.bcp.ump.biz.service.ThemisRuleService;
import com.lucifiere.dme.bcp.ump.repository.entity.*;
import com.lucifiere.dme.bcp.ump.repository.mapper.*;
import com.lucifiere.fastjson.JSON;
import com.alipic.common.result.BaseResult;
import com.taobao.txc.common.TxcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 规则服务实现
 *
 * @author arnold.hl
 */
@Service
public class ThemisRuleServiceImpl implements ThemisRuleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThemisRuleServiceImpl.class);

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

    /**
     * 插入工作详情使用的线程池
     */
    private final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            10,
            10,
            60,
            TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(3000),
            (runnable) -> {
                Thread myThread = new Thread(runnable);
                myThread.setDaemon(false);
                myThread.setName("插入规则详情线程");
                myThread.setPriority(1);
                return myThread;
            }

    );

    /**
     * 插入/删除规则使用的线程池
     */
    private final ThreadPoolExecutor THREAD_POOL_RULE_EXECUTOR = new ThreadPoolExecutor(
            10,
            10,
            60,
            TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(3000),
            (runnable) -> {
                Thread myThread = new Thread(runnable);
                myThread.setDaemon(false);
                myThread.setName("插入主规则线程");
                myThread.setPriority(1);
                return myThread;
            }

    );

    /**
     * 同步规则列表
     * <p>
     * TODO 确认TXC是否正常，以及是否需要增加本地事务注解，
     * 插入期间没有本地事务，可能读到脏数据？
     * 线程池内异常抛出，TXC是否回滚？
     * 性能问题
     * 线程隔离,限流，降级
     * <p>
     * TODO 幂等
     *
     * @param syncRuleRequest 同步规则请求
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult syncRule(SyncRuleRequest syncRuleRequest) {
        String currentXid = TxcContext.getCurrentXid();
        String nextSvrAddr = TxcContext.getTxcNextSvrAddr();
    	LOGGER.info("syncRule执行,TxcContext.getCurrentXid:"+currentXid+",nextSvrAddr:"+nextSvrAddr);
        long beginExecTime = System.currentTimeMillis();
        //查找themisRule
        ThemisExt themisExtExisted = themisExtMapper.queryExt(syncRuleRequest.getBusinessLineCode(),
                syncRuleRequest.getSystemSource(),
                syncRuleRequest.getExtId());
        //如果有旧的themisRule,则删除themisRule及其相关的rules
        if (themisExtExisted != null) {
            LOGGER.info("DEBUG,查找存在rule前:" + (System.currentTimeMillis() - beginExecTime) + "ms");
            //1.根据themisExtId查出关联的ruleList
            List<ThemisRule> existedThemisRuleList = themisRuleMapper
                    .selectRuleListByThemisExtIdIgnoreStatus(themisExtExisted.getId());
            LOGGER.info("DEBUG,查找存在rule后:" + (System.currentTimeMillis() - beginExecTime) + "ms");
            if (existedThemisRuleList != null && !existedThemisRuleList.isEmpty()) {
                this.deleteOldRule(existedThemisRuleList,beginExecTime,currentXid,nextSvrAddr);
            } else {
                LOGGER.info("不存在旧规则,themisExtId:" + themisExtExisted.getExtId());
            }
            
            LOGGER.info("删除旧的themisExt,themisExtId:" + themisExtExisted.getId());
            themisExtMapper.deleteByPrimaryKey(themisExtExisted.getId());
            LOGGER.info("DEBUG,删除旧themisExt相关数据完毕,themisExtId:" + themisExtExisted.getId() + "," + (System.currentTimeMillis() - beginExecTime) + "ms");

        } else {
            LOGGER.info("不存在旧的themisExt,extId:" + syncRuleRequest.getExtId());
        }
        //插入themisRule
        ThemisExt newThemisExt = this.createThemeExtOnly(syncRuleRequest);
        long newThemisExtId = newThemisExt.getId();
        LOGGER.info("DEBUG,添加themisExt:" + (System.currentTimeMillis() - beginExecTime) + "ms,newThemisExtId:"+newThemisExtId);

        //插入rule详情和其他
        List<BaseRuleRequest> ruleRequestList = syncRuleRequest.getBaseRuleRequestList();
        if (ruleRequestList != null) {
            LOGGER.info("ruleRequestList size:" + ruleRequestList.size());
            this.insertRuleData(ruleRequestList,newThemisExtId,beginExecTime,currentXid,nextSvrAddr);
        }
        long execTime = System.currentTimeMillis() - beginExecTime;
        LOGGER.info("同步Rule执行时间:" + execTime + "ms");
        return BaseResult.success();
    }

    /**
     * 删除旧的规则数据
     * @param existedThemisRuleList 规则列表
     * @param beginExecTime 时间
     */
    private void deleteOldRule(List<ThemisRule> existedThemisRuleList,long beginExecTime,String currentXid,String nextSvrAddr){
        CountDownLatch deleteRuleCountDownLatch = new CountDownLatch(existedThemisRuleList.size());
        final AtomicInteger failedInsertNum = new AtomicInteger(0);
        LOGGER.info("多线程处理DeleteRule,poolSize:{},activeCount:{},corePoolSize:{},maxPoolSize:{},queueRemainCapacity:{},queueSize:{}",
                THREAD_POOL_RULE_EXECUTOR.getPoolSize(),
                THREAD_POOL_RULE_EXECUTOR.getActiveCount(),
                THREAD_POOL_RULE_EXECUTOR.getCorePoolSize(),
                THREAD_POOL_RULE_EXECUTOR.getMaximumPoolSize(),
                THREAD_POOL_RULE_EXECUTOR.getQueue().remainingCapacity(),
                THREAD_POOL_RULE_EXECUTOR.getQueue().size());
        for (ThemisRule themisRule : existedThemisRuleList) {
            THREAD_POOL_RULE_EXECUTOR.execute(() -> {
                TxcContext.bind(currentXid,nextSvrAddr);
                try {
                    //如果存在旧数据, 删除旧的
                    long oldRuleId = themisRule.getId();
                    LOGGER.info("删除旧的规则,ruleId:" + oldRuleId);
                    themisRuleMapper.deleteByPrimaryKey(oldRuleId);
                    LOGGER.info("删除旧的规则详情,ruleId:" + oldRuleId);
                    themisRuleDetailMapper.deleteByRuleId(oldRuleId);
                    LOGGER.info("删除旧的渠道,ruleId:" + oldRuleId);
                    themisRuleChannelMapper.deleteByRuleId(oldRuleId);
                    LOGGER.info("删除旧的规则业务地点,ruleId:" + oldRuleId);
                    themisRulePlaceMapper.deleteByRuleId(oldRuleId);
                    LOGGER.info("删除旧的规则商品,ruleId:" + oldRuleId);
                    themisRuleItemMapper.deleteByRuleId(oldRuleId);
                    LOGGER.info("DEBUG,删除旧rule,ruleId:" + oldRuleId + "," + (System.currentTimeMillis() - beginExecTime) + "ms");
                } catch (Exception ex) {
                    LOGGER.error("插入主规则线程池异常", ex);
                    failedInsertNum.incrementAndGet();
                } finally {
                    deleteRuleCountDownLatch.countDown();
                    TxcContext.unbind();
                }
            });
        }
        try {
            //防止获取不到线程死锁
            LOGGER.info("等待删除旧rule结束");
            deleteRuleCountDownLatch.await(10,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            LOGGER.error("删除规则插入闭锁错误", e);
            throw new RuntimeException("主规则插入闭锁错误");
        }
        if (failedInsertNum.get() > 0) {
            LOGGER.error("删除规则失败" + failedInsertNum.get() + "次");
            throw new RuntimeException("删除规则失败" + failedInsertNum.get() + "次");
        }
    }

    /**
     * 多线程插入规则
     * @param ruleRequestList 规则请求
     * @param newThemisExtId id
     * @param beginExecTime 开始时间
     * @param currentXid txc xid
     * @param nextSvrAddr 服务地址
     */
    private void insertRuleData(List<BaseRuleRequest> ruleRequestList,long newThemisExtId,long beginExecTime,
                                String currentXid,String nextSvrAddr){
        CountDownLatch ruleCountDownLatch = new CountDownLatch(ruleRequestList.size());
        final AtomicInteger failedInsertNum = new AtomicInteger(0);
        LOGGER.info("多线程处理InsertRule,poolSize:{},activeCount:{},corePoolSize:{},maxPoolSize:{},queueRemainCapacity:{},queueSize:{}",
                THREAD_POOL_RULE_EXECUTOR.getPoolSize(),
                THREAD_POOL_RULE_EXECUTOR.getActiveCount(),
                THREAD_POOL_RULE_EXECUTOR.getCorePoolSize(),
                THREAD_POOL_RULE_EXECUTOR.getMaximumPoolSize(),
                THREAD_POOL_RULE_EXECUTOR.getQueue().remainingCapacity(),
                THREAD_POOL_RULE_EXECUTOR.getQueue().size());
        for (BaseRuleRequest baseRuleRequest : ruleRequestList) {
            THREAD_POOL_RULE_EXECUTOR.execute(() -> {
                TxcContext.bind(currentXid,nextSvrAddr);
                try {
                    LOGGER.info("添加主rule开始");
                    //1.添加rule数据
                    long newRuleId = this.createRuleOnly(baseRuleRequest, newThemisExtId);
                    LOGGER.info("DEBUG,添加主rule:" + (System.currentTimeMillis() - beginExecTime) + "ms,ruleId:" + newRuleId);
                    //2.批量添加rule相关
                    this.batchCreateRuleDetail(baseRuleRequest, newRuleId,currentXid,nextSvrAddr);
                    LOGGER.info("DEBUG,添加ruleDetail:" + (System.currentTimeMillis() - beginExecTime) + "ms");
                    this.batchCreateChannelAndItemAndPlaceRule(baseRuleRequest, newRuleId);
                    LOGGER.info("DEBUG,添加rule其他:" + (System.currentTimeMillis() - beginExecTime) + "ms");
                } catch (Exception ex) {
                    LOGGER.error("插入主规则线程池异常", ex);
                    failedInsertNum.incrementAndGet();
                } finally {
                    ruleCountDownLatch.countDown();
                    TxcContext.unbind();
                }
            });
        }
        try {
            //防止获取不到线程死锁
            LOGGER.info("等待处理themisExtId:{}结束", newThemisExtId);
            ruleCountDownLatch.await(10,TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            LOGGER.error("主规则插入闭锁错误", e);
            throw new RuntimeException("主规则插入闭锁错误");
        }
        if (failedInsertNum.get() > 0) {
            LOGGER.error("插入主规则失败" + failedInsertNum.get() + "次");
            throw new RuntimeException("插入主规则失败" + failedInsertNum.get() + "次");
        }
    }

    /**
     * 创建themeExt
     *
     * @param syncRuleRequest 请求对象
     * @return 创建的对象
     */
    private ThemisExt createThemeExtOnly(SyncRuleRequest syncRuleRequest) {
        ThemisExt themisExt = new ThemisExt();
        BeanUtils.copyProperties(syncRuleRequest, themisExt);
        themisExtMapper.insert(themisExt);
        return themisExt;
    }

    /**
     * 仅仅创建规则
     *
     * @param ruleRequest 请求对象
     * @return 新规则Id
     */
    private long createRuleOnly(BaseRuleRequest ruleRequest, long newThemisExtId) {
        ThemisRule themisRule = new ThemisRule();
        BeanUtils.copyProperties(ruleRequest, themisRule);
        //如果没设置版本，默认从1开始
        if (themisRule.getVersion() == null) {
            themisRule.setVersion(1);
        }
        themisRule.setThemisExtId(newThemisExtId);
        themisRuleMapper.insert(themisRule);
        return themisRule.getId();
    }


    /**
     * 停用规则
     *
     * @param disableRuleRequest 停用规则请求
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult disableRule(DisableRuleRequest disableRuleRequest) {
    	LOGGER.info("syncRule执行,TxcContext.getCurrentXid:"+TxcContext.getCurrentXid());
        LOGGER.info("禁用规则,extId:" + disableRuleRequest.getExtId());
        Date modifiedDate = disableRuleRequest.getGmtModified();
        if (modifiedDate == null) {
            modifiedDate = new Date();
        }

        //查询要更新的extId
        ThemisExt themisExt = themisExtMapper.queryExt(disableRuleRequest.getBusinessLineCode(),
                disableRuleRequest.getSystemSource(),
                disableRuleRequest.getExtId());
        if (themisExt == null) {
            LOGGER.error("找不到themisExt,extId:" + disableRuleRequest.getExtId());
            //可能并行处理的时候已经被删除了
            return BaseResult.success();
        }

        //更新rule状态
        themisRuleMapper.updateStatusByThemisExtId(themisExt.getId(),
                disableRuleRequest.getModifyUser(),
                modifiedDate,
                RuleStatus.INEFFECTIVE);
        LOGGER.info("txcXid:" + TxcContext.getCurrentXid());
        return BaseResult.success();
    }

    /**
     * 查询规则
     *
     * @param baseRuleQueryParam
     * @return
     */
    @Override
    public RuleQueryResultResultVO queryRuleFilterBaseCondition(BaseRuleQueryParam baseRuleQueryParam) {

        Integer status = baseRuleQueryParam.getStatus();
        Integer businessLineCode = baseRuleQueryParam.getBusinessLineCode();
        String leaseCode = baseRuleQueryParam.getLeaseCode();

        RuleQueryResultResultVO resultVO = new RuleQueryResultResultVO();
        if (BusinessLineCode.LARK.equals(baseRuleQueryParam.getBusinessLineCode())) {
            LarkRuleQueryRequest larkRuleQueryRequest = (LarkRuleQueryRequest) baseRuleQueryParam;
            Long ruleId = larkRuleQueryRequest.getRuleId();
            String targetPlaceCode = larkRuleQueryRequest.getPlaceCode();
            String targetChannelCode = larkRuleQueryRequest.getChannelCode();
            Date queryTime = larkRuleQueryRequest.getQueryTime();

            //step1 规则本身判断
            ThemisRule themisRule = themisRuleMapper.filterRule(leaseCode, queryTime, status, businessLineCode);
            if (themisRule == null) {
                resultVO.setSuccess(Boolean.FALSE);
                resultVO.getReasons().add(SceneCheckResultEnum.DATE_NOT_MATCHED.getMsg());
                return resultVO;
            }

            //step2 过滤渠道
            ThemisRuleChannel ruleChannel = themisRuleChannelMapper.filterByChannelCode(leaseCode, ruleId, targetChannelCode);
            if (ruleChannel == null) {
                resultVO.setSuccess(Boolean.FALSE);
                resultVO.getReasons().add(SceneCheckResultEnum.WEEK_NOT_MATCH.getMsg());
                return resultVO;
            }

            //step3 过滤影院
            ThemisRulePlace rulePlace = themisRulePlaceMapper.filterByPlaceCode(leaseCode, ruleId, targetPlaceCode);
            if (rulePlace == null) {
                resultVO.setSuccess(Boolean.FALSE);
                resultVO.getReasons().add(SceneCheckResultEnum.TIME_NOT_MATCH.getMsg());
                return resultVO;
            }
            //step4 获取商品范围
            List<ThemisRuleItem> ruleItemList = themisRuleItemMapper.selectByRuleId(leaseCode, ruleId);
            //step5 获取规则详情，更细粒度的规则过滤交规规则域
            ThemisRuleDetail ruleDetail = themisRuleDetailMapper.selectByRuleId(ruleId);

            resultVO.setThemisRuleItemList(ruleItemList);
            resultVO.setThemisRuleDetail(ruleDetail);
        }

        return resultVO;
    }


    /**
     * 按照商品信息查询满足条件的优惠信息
     *
     * @param skuRuleSearch
     * @return
     */
    @Override
    public List<ThemisRuleDetail> queryRuleDetailBySkuCondition(Map<String, Object> skuRuleSearch) {
//        List<ThemisRuleDetail> themisRuleDetailList = new ArrayList<>();
//        String leaseCode = skuRuleSearch.getLeaseCode();
//        String skuCode = skuRuleSearch.getSkuCode();
//        String productType = skuRuleSearch.getRuleType();
//        Map<String, String> searchParamMap = skuRuleSearch.getSearchParamMap();
//
//        //先过滤商品满不满足商品范围
//        List<ThemisRuleItem> ruleItemList = themisRuleItemMapper.selectBySearchCondition(leaseCode, skuCode, productType, null);
//        if (!CollectionUtils.isEmpty(ruleItemList)) {
//            Set<Long> finalIdSet = new HashSet<>();
//
//            List<Long> ruleIdList = ruleItemList.stream().map(ThemisRuleItem::getRuleId).collect(Collectors.toList());
//            //过滤主规则状态
//            List<ThemisRule> themisRuleList = themisRuleMapper.selectRuleList(leaseCode, RuleStatus.EFFECTIVE, ruleIdList);
//            Set<Long> filterRuleIdList = themisRuleList.stream().map(ThemisRule::getId).collect(Collectors.toSet());
//            if (!CollectionUtils.isEmpty(themisRuleList)) {
//                for (Long ruleId : filterRuleIdList) {
//                    //过滤渠道
//                    String targetChannelCode = skuRuleSearch.getChannelCode();
//                    ThemisRuleChannel ruleChannel = themisRuleChannelMapper.filterByChannelCode(leaseCode, ruleId, targetChannelCode);
//
//                    //过滤地点
//                    String targetPlaceCode = skuRuleSearch.getPlaceCode();
//                    ThemisRulePlace rulePlace = themisRulePlaceMapper.filterByPlaceCode(leaseCode, ruleId, targetPlaceCode);
//
//                    if (ruleChannel != null && rulePlace != null) {
//                        finalIdSet.add(ruleId);
//                    }
//                }
//            }
//
//            //如果所有的外置条件都已过滤，则可认为该条件基本满足优惠
//            //如果有更细粒度的判断需求，则需要在detail的返回结果里判断
//            if (!CollectionUtils.isEmpty(finalIdSet)) {
//                List<Long> idList = new ArrayList<>(finalIdSet);
//                themisRuleDetailList = themisRuleDetailMapper.selectByRuleIds(idList);
//            }
//
//        }
//        return themisRuleDetailList;
        return null;
    }

    /**
     * 谨慎使用
     * @param businessLineCode 业务线
     * @param systemSource 系统
     * @param leaseCode 租户
     * @param placeCode 地点
     * @return
     */
    @Override
    public BaseResult deleteRuleRelationshipByPlaceCode(Integer businessLineCode, String systemSource, String leaseCode, String placeCode) {
        if(BusinessLineCode.LARK.equals(businessLineCode)
                && SystemSource.LARK_CARD.equals(systemSource)){
            long beginId = 0L;
            int limit = 200;
            int count=0;

            List<ThemisRulePlace> themisRulePlaceList =
                    themisRulePlaceMapper.queryRuleListByPlaceCode(leaseCode,placeCode,beginId,limit);
            while(themisRulePlaceList!=null && themisRulePlaceList.size()>0){
                for (ThemisRulePlace themisRulePlace:themisRulePlaceList) {
                    count=count+1;
                    //重新设置新的查询ID
                    beginId = themisRulePlace.getId();
                    ThemisRule themisRule = themisRuleMapper.selectByPrimaryKey(themisRulePlace.getRuleId());
                    if(themisRule!=null){
                        ThemisExt themisExt = themisExtMapper.selectByPrimaryKey(themisRule.getThemisExtId());
                        if(themisExt!=null){
                            if(leaseCode.equals(themisExt.getLeaseCode())
                                    &&businessLineCode.equals(themisExt.getBusinessLineCode())
                                    &&systemSource.equals(themisExt.getSystemSource())){
                                LOGGER.info("placeCode符合条件,删除对应数据关系themis_place_code,id:{}"
                                        ,themisRulePlace.getId());
                                themisRulePlaceMapper.deleteByPrimaryKey(themisRulePlace.getId());
                            }else{
                                LOGGER.info("placeCode符合，但业务线，系统，租户可能不符合,themisExt表id:"
                                        +themisExt.getId());
                            }
                        }else{
                            LOGGER.error("找不到业务线对应的ThemisExt,ruleId:{},themisExtId:{}",
                                    themisRule.getId(),themisRule.getThemisExtId());
                        }

                    }else{
                        LOGGER.error("找不到对应的Rule,ruleId:{}",themisRulePlace.getRuleId());
                    }
                }

                themisRulePlaceList =
                        themisRulePlaceMapper.queryRuleListByPlaceCode(leaseCode,placeCode,beginId,limit);

            }
            LOGGER.info("处理完毕，共处理符合placeCode数据{}条",count);

        }else{
            LOGGER.error("不支持的业务线或者系统,业务线:{},系统{}",businessLineCode,systemSource);
            throw new RuntimeException("不支持的业务线或者系统");
        }
        return BaseResult.success();
    }

    /**
     * 批量保存规则详情相关数据
     *
     * @param ruleRequest 规则
     * @param newRuleId   新规则ID
     * @param currentXid txcXid
     * @param nextSvrAddr serverAddress
     */
    private void batchCreateRuleDetail(BaseRuleRequest ruleRequest, long newRuleId,String currentXid,String nextSvrAddr) {
        //保存规则详情
        if (ruleRequest.getRuleDetailRequestList() != null
                && !ruleRequest.getRuleDetailRequestList().isEmpty()) {

            List<List<ThemisRuleDetail>> splitBatchedRuleDetail = this.getBatchedRuleDetail(ruleRequest, newRuleId);
            //大于下面设置的批次才启用多线程
            int useThreadMinBatchNum = 1;
            if (splitBatchedRuleDetail.size() > useThreadMinBatchNum) {
                LOGGER.info("多线程插入数据...，newRuleId:" + newRuleId);
                LOGGER.info("多线程多处理Detail,poolSize:{},activeCount:{},corePoolSize:{},maxPoolSize:{},queueRemainCapacity:{},queueSize:{}",
                        THREAD_POOL_EXECUTOR.getPoolSize(),
                        THREAD_POOL_EXECUTOR.getActiveCount(),
                        THREAD_POOL_EXECUTOR.getCorePoolSize(),
                        THREAD_POOL_EXECUTOR.getMaximumPoolSize(),
                        THREAD_POOL_EXECUTOR.getQueue().remainingCapacity(),
                        THREAD_POOL_EXECUTOR.getQueue().size());
                int batchNum = splitBatchedRuleDetail.size();
                final CountDownLatch countDownLatch = new CountDownLatch(batchNum);
                final AtomicInteger failedInsertNum = new AtomicInteger(0);

                for (List<ThemisRuleDetail> oneBatchedDetail : splitBatchedRuleDetail) {
                    //处理线程异常
                    THREAD_POOL_EXECUTOR.execute(() -> {
                        TxcContext.bind(currentXid,nextSvrAddr);
                        try {
                            long beginExecTime = System.currentTimeMillis();
                            themisRuleDetailMapper.batchInsert(oneBatchedDetail);
                            LOGGER.info("DEBUG,插入一批ruleDetail(" + oneBatchedDetail.size() + "条):单次耗时"
                                    + (System.currentTimeMillis() - beginExecTime) + "ms");
                        } catch (Exception ex) {
                            LOGGER.error("插入线索详情线程池异常", ex);
                            failedInsertNum.incrementAndGet();
                        } finally {
                            countDownLatch.countDown();
                            TxcContext.unbind();
                        }
                    });
                }
                try {
                    //防止获取不到线程死锁
                    LOGGER.info("等待处理ruleId:{}结束", newRuleId);
                    countDownLatch.await(10,TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    LOGGER.error("闭锁错误", e);
                    throw new RuntimeException("闭锁错误");
                }
                if (failedInsertNum.get() > 0) {
                    LOGGER.error("插入规则详情失败" + failedInsertNum.get() + "次");
                    throw new RuntimeException("插入规则详情失败" + failedInsertNum.get() + "次");
                }
            } else {
                int i = 1;
                for (List<ThemisRuleDetail> oneBatchedDetail : splitBatchedRuleDetail) {
                    LOGGER.info("正在插入第" + i + "批数据,数量:" + oneBatchedDetail.size());
                    themisRuleDetailMapper.batchInsert(oneBatchedDetail);
                    i = i + 1;
                }
            }
        }
    }

    /**
     * 把规则详情按照制定的数量分批
     *
     * @param ruleRequest 请求对象
     * @param newRuleId   新规则Id
     * @return 分好批的规则详情列表
     */
    private List<List<ThemisRuleDetail>> getBatchedRuleDetail(BaseRuleRequest ruleRequest, long newRuleId) {
        //当前正在处理的数量
        int currentCount = 0;
        //每多少个detail作为一个批量插入
        int splitNum = 200;
        //分好批的规则详情列表
        List<List<ThemisRuleDetail>> splitBatchedRuleDetail = new ArrayList<>(16);
        List<ThemisRuleDetail> themisRuleDetailList = null;

        for (RuleDetailRequest ruleDetailRequest : ruleRequest.getRuleDetailRequestList()) {
            //每splitNum个当成一批插入
            if (currentCount % splitNum == 0) {
                themisRuleDetailList = new ArrayList<>(16);
                splitBatchedRuleDetail.add(themisRuleDetailList);
            }
            currentCount = currentCount + 1;
            ThemisRuleDetail themisRuleDetail = new ThemisRuleDetail();
            BeanUtils.copyProperties(ruleDetailRequest, themisRuleDetail);
            themisRuleDetail.setAction(
                    JSON.toJSONString(ruleDetailRequest.getActionMap())
            );
            themisRuleDetail.setCommonCondition(
                    JSON.toJSONString(ruleDetailRequest.getCommonConditionMap())
            );
            if (ruleDetailRequest.getFeatureConditionMap() != null) {
                themisRuleDetail.setFeatureCondition(
                        JSON.toJSONString(ruleDetailRequest.getFeatureConditionMap())
                );
            } else {
                themisRuleDetail.setFeatureCondition("");
            }
            if (ruleDetailRequest.getExtension() == null) {
                themisRuleDetail.setExtension("");
            }
            themisRuleDetail.setRuleId(newRuleId);
            themisRuleDetailList.add(themisRuleDetail);
        }
        return splitBatchedRuleDetail;
    }

    /**
     * 批量保存渠道，商品，业务地点规则
     *
     * @param ruleRequest 规则请求
     * @param newRuleId   新规则id
     */
    private void batchCreateChannelAndItemAndPlaceRule(BaseRuleRequest ruleRequest, long newRuleId) {
        //保存渠道规则
        if (ruleRequest.getRuleChannelRequestList() != null
                && !ruleRequest.getRuleChannelRequestList().isEmpty()) {
            List<ThemisRuleChannel> themisRuleChannelList = new ArrayList<>(16);
            for (RuleChannelRequest channelRequest : ruleRequest.getRuleChannelRequestList()) {
                ThemisRuleChannel ruleChannel = new ThemisRuleChannel();
                BeanUtils.copyProperties(channelRequest, ruleChannel);
                ruleChannel.setRuleId(newRuleId);
                themisRuleChannelList.add(ruleChannel);
            }

            themisRuleChannelMapper.batchInsert(themisRuleChannelList);
        }
        //保存商品规则
        if (ruleRequest.getRuleItemRequestList() != null
                && !ruleRequest.getRuleItemRequestList().isEmpty()) {
            List<ThemisRuleItem> ruleItemList = new ArrayList<>(16);
            for (RuleItemRequest ruleItemRequest : ruleRequest.getRuleItemRequestList()) {
                ThemisRuleItem themisRuleItem = new ThemisRuleItem();
                BeanUtils.copyProperties(ruleItemRequest, themisRuleItem);
                themisRuleItem.setRuleId(newRuleId);
                ruleItemList.add(themisRuleItem);
            }
            themisRuleItemMapper.batchInsert(ruleItemList);
        }

        //保存影院规则
        if (ruleRequest.getRulePlaceRequestList() != null
                && !ruleRequest.getRulePlaceRequestList().isEmpty()) {
            List<ThemisRulePlace> themisRulePlaceList = new ArrayList<>(16);
            for (RulePlaceRequest rulePlaceRequest : ruleRequest.getRulePlaceRequestList()) {
                ThemisRulePlace themisRulePlace = new ThemisRulePlace();
                BeanUtils.copyProperties(rulePlaceRequest, themisRulePlace);
                themisRulePlace.setRuleId(newRuleId);
                themisRulePlaceList.add(themisRulePlace);
            }
            themisRulePlaceMapper.batchInsert(themisRulePlaceList);
        }
    }

}

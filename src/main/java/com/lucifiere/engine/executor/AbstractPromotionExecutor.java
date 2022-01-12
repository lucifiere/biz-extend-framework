package com.lucifiere.engine.executor;

import com.lucifiere.bef.specific.impl.BizInstanceId;
import com.lucifiere.engine.CalculateEngine;
import com.lucifiere.engine.EngineContext;
import com.lucifiere.engine.EngineExecResult;
import com.lucifiere.engine.PromotionInstance;
import com.lucifiere.engine.executor.context.PromotionExecContext;
import com.lucifiere.engine.executor.support.FunctionInfo;
import com.lucifiere.engine.executor.support.FunctionInfoCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * 优惠执行器
 *
 * @author XD.Wang
 * @version 1.0`
 * @since 1.0 2019年6月13日
 */
public abstract class AbstractPromotionExecutor implements PromotionExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PromoQueryFacadeImpl.class);

    @Autowired
    private FunctionInfoCreator functionInfoCreator;

    @Resource
    private CalculateEngine calculateEngine;

    /**
     * 获取异常处理方式
     *
     * @return 异常处理方式
     */
    protected abstract PromotionInstance.ExceptionStrategy getExceptionStrategy();

    /**
     * 创建引擎执行上下文
     *
     * @param context 优惠域上下文
     * @return 引擎上下文
     */
    protected abstract List<EngineContext> createEngineContext(PromotionExecContext context);

    /**
     * 处理引擎计算结果
     *
     * @param engineContext 引擎计算结果
     * @param context       优惠域执行上下文
     * @param execResp      就散结果
     */
    protected abstract void processAfterEngineExecute(EngineContext engineContext, PromotionExecContext context, EngineExecResult execResp);

    /**
     * 执行器负责调用QL引擎进行脚本计算
     * 1 组装QL表达式执行上下文
     *
     * @param req 引擎计算入参
     */
    void calRulePromotion(PromotionExecContext req) {
        // 找出符合【curRule】规则的商品后，为其初始化价格计算上下文
        List<EngineContext> engineContextList = createEngineContext(req);
        for (EngineContext engineContext : engineContextList) {
            execEngine(req, engineContext);
        }
    }

    private void execEngine(PromotionExecContext req, EngineContext engineContext) {
        // 为当前规则所属的【产品】找到对应的QL表达式，并根据QL表达式调用功能节点进行计算
        FunctionInfo function = functionInfoCreator.createFunctionInfo(req, engineContext);
        PromotionInstance promotion = new PromotionInstance();
        promotion.setExpress(function.getQlExpress());
        // 控制脚本执行过程中遇到异常时的处理方式，由子类决定
        promotion.setExecuteStrategy(getExceptionStrategy());
        // 执行上下文的BizInstanceId决定了各域采用哪个产品的扩展点
        engineContext.setBizInstanceId(BizInstanceId.of(function.getInstanceCode(), ""));
        // 执行QL表达式，通过表达式逻辑调用域服务
        EngineExecResult execResp = calculateEngine.execute(promotion, engineContext);
        processAfterEngineExecute(engineContext, req, execResp);
    }

}

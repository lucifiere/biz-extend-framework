package com.lucifiere.engine.executor.support;

import com.google.common.collect.Maps;
import com.lucifiere.bef.interfaces.ExpressLoader;
import com.lucifiere.common.constants.CommonInnerCode;
import com.lucifiere.common.exception.BusinessException;
import com.lucifiere.engine.EngineContext;
import com.lucifiere.engine.executor.context.PromotionCalContext;
import com.lucifiere.engine.executor.context.PromotionExecContext;
import com.lucifiere.engine.executor.context.PromotionPreHandleContext;
import com.lucifiere.engine.executor.context.PromotionQueryContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 创建表达式对象
 *
 * @author XD.Wang
 * @version 1.0
 * @since 1.0 2019年6月3日
 */
@Component
public class FunctionInfoCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionInfoCreator.class);

    /**
     * 表达式加载工具
     */
    private final ExpressLoader expressLoader;

    @Autowired
    public FunctionInfoCreator(@Qualifier(value = "fileExpressLoader") ExpressLoader expressLoader) {
        this.expressLoader = expressLoader;
    }

    /**
     * 产品对应的产品编码
     */
    private static final Map<String, String> PRODUCT_INSTANCE_CODE_MAPPING = Maps.newHashMap();

    static {
        // 初始化产品编码，产品编码取决于产品类型
        PRODUCT_INSTANCE_CODE_MAPPING.put(ThemisCouponType.REPIRCE_COUPON, RepriceCouponProduct.CODE);
        PRODUCT_INSTANCE_CODE_MAPPING.put(ThemisCouponType.CASH_COUPON, CashCouponProduct.CODE);
        PRODUCT_INSTANCE_CODE_MAPPING.put(ThemisCouponType.DISCOUNT_COUPON, DiscountCouponProduct.CODE);
        PRODUCT_INSTANCE_CODE_MAPPING.put(ThemisCardType.DEPOSIT_CARD, DepositCardProduct.CODE);
        PRODUCT_INSTANCE_CODE_MAPPING.put(ThemisCardType.METER_CARD, MeterCardProduct.CODE);
        PRODUCT_INSTANCE_CODE_MAPPING.put(ThemisCardType.BENEFIT_CARD, BenefitCardProduct.CODE);
        PRODUCT_INSTANCE_CODE_MAPPING.put(ThemisCardType.GIFT_CARD, GiftCardProduct.CODE);
        PRODUCT_INSTANCE_CODE_MAPPING.put(ActivityType.MIX, LarkMarketingPackageActProduct.CODE);
        PRODUCT_INSTANCE_CODE_MAPPING.put(ActivityType.GOODS, LarkMarketingGoodsActProduct.CODE);
        PRODUCT_INSTANCE_CODE_MAPPING.put(ActivityType.TICKET, LarkMarketingTicketActProduct.CODE);
    }

    /**
     * 组装表达式信息
     *
     * @param context 优惠执行上下文
     * @return 表达式信息
     */
    public FunctionInfo createFunctionInfo(PromotionExecContext context, EngineContext engineContext) {
        if (context instanceof PromotionCalContext) {
            String ruleType = engineContext.getPromotionIdentity().getRuleType();
            PromotionCalContext calContext = ((PromotionCalContext) context);
            return createCalFunctionInfo(calContext.getCurRule().getSystemSource(), ruleType);
        }
        if (context instanceof PromotionQueryContext) {
            String ruleType = engineContext.getPromotionIdentity().getRuleType();
            return createQueryFunctionInfo(ruleType);
        }
        if (context instanceof PromotionPreHandleContext) {
            return createPreHandleFunctionInfo(null);
        }
        throw new BusinessException(CommonInnerCode.UNSUPPORTED_BUSINESS_CONFIG);
    }

    /**
     * 组装计算表达式信息
     *
     * @param systemSource 系统来源
     * @param ruleType     规则类型
     * @return 表达式信息
     */
    private FunctionInfo createCalFunctionInfo(String systemSource, String ruleType) {
        try {
            paramPreCheck(systemSource, ruleType);
            FunctionInfo functionInfo = new FunctionInfo();
            String qlExpress = expressLoader.getExpress(QlScriptLocation.CAL_SCRIPT_EXPRESS_LOCATION.get(systemSource));
            functionInfo.setQlExpress(qlExpress);
            functionInfo.setInstanceCode(PRODUCT_INSTANCE_CODE_MAPPING.get(ruleType));
            functionInfoCheck(functionInfo);
            return functionInfo;
        } catch (Exception e) {
            LOGGER.error("加载脚本异常！", e);
            throw new BusinessException(CommonInnerCode.QL_SCRIPT_ERROR, e.getMessage());
        }
    }

    /**
     * 组装规则查询表达式信息
     *
     * @param ruleType 产品类型
     * @return 表达式信息
     */
    private FunctionInfo createQueryFunctionInfo(String ruleType) {
        try {
            FunctionInfo functionInfo = new FunctionInfo();
            String qlExpress = expressLoader.getExpress(QlScriptLocation.GENERAL_SCENE_FILTER_SCRIPT_LOCATION);
            functionInfo.setQlExpress(qlExpress);
            functionInfo.setInstanceCode(PRODUCT_INSTANCE_CODE_MAPPING.get(ruleType));
            functionInfoCheck(functionInfo);
            return functionInfo;
        } catch (Exception e) {
            LOGGER.error("加载脚本异常！", e);
            throw new BusinessException(CommonInnerCode.QL_SCRIPT_ERROR, e.getMessage());
        }
    }

    /**
     * 组装规则查询表达式信息
     *
     * @param ruleType 产品类型
     * @return 表达式信息
     */
    private FunctionInfo createPreHandleFunctionInfo(String ruleType) {
        try {
            FunctionInfo functionInfo = new FunctionInfo();
            String qlExpress = expressLoader.getExpress(QlScriptLocation.RULE_PRE_HANDLE_SCRIPT_LOCATION);
            functionInfo.setQlExpress(qlExpress);
            functionInfo.setInstanceCode(null);
            functionInfoCheck(functionInfo);
            return functionInfo;
        } catch (Exception e) {
            LOGGER.error("加载脚本异常！", e);
            throw new BusinessException(CommonInnerCode.QL_SCRIPT_ERROR, e.getMessage());
        }
    }

    /**
     * 入参校验
     *
     * @param systemSource 系统来源
     * @param ruleType     规则类型
     */
    private void paramPreCheck(String systemSource, String ruleType) {
        ExceptionUtils.notNullCheck(systemSource, "系统来源");
        ExceptionUtils.notNullCheck(ruleType, "产品类型");
    }

    /**
     * 组装结果校验
     *
     * @param func 外部规则
     */
    private void functionInfoCheck(FunctionInfo func) {
        ExceptionUtils.bizCheck(StringUtils.isNotEmpty(func.getQlExpress()), "获取计算表达式失败，请检查业务系统配置");
    }

}

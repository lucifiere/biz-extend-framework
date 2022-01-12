package com.lucifiere.engine;

import com.lucifiere.common.constants.CommonInnerCode;
import com.lucifiere.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * 优惠计算引擎
 *
 * @author 忠魂
 */
@Component
public class CalculateEngine {
    /**
     * The constant logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateEngine.class);

    /**
     * The Ql express runner.
     */
    @Autowired
    private UmpQlExpressRunner qlExpressRunner;

    /**
     * Execute.
     *
     * @param promotion     the promotion
     * @param engineContext the engine context
     */
    public EngineExecResult execute(PromotionInstance promotion, EngineContext engineContext) {
        try {
            Object obj = qlExpressRunner.execute(promotion.getExpress(), engineContext);
            return EngineExecResult.suc(obj);
        } catch (Exception e) {
            switch (promotion.getExecuteStrategy()) {
                case BREAK:
                    handleWithBreakStrategy(e);
                    return EngineExecResult.error();
                case IGNORE_ALL:
                    return handleWithIgnoreAllStrategy(e);
                case IGNORE_BIZ:
                    return handleWithIgnoreBizStrategy(e);
                default:
                    throw new RuntimeException("unknown result!");
            }
        }
    }

    private void handleWithBreakStrategy(Exception e) {
        if (e.getCause() instanceof InvocationTargetException) {
            InvocationTargetException invocationTargetException = (InvocationTargetException) e.getCause();
            Throwable targetExceptionCause = invocationTargetException.getCause();
            if (targetExceptionCause instanceof BusinessException) {
                throw (BusinessException) targetExceptionCause;
            } else {
                LOGGER.error("execute throw Throwable with BreakStrategy", targetExceptionCause);
            }
        }
        LOGGER.error("execute throw Throwable with unknown reason", e);
        throw new BusinessException(CommonInnerCode.QL_SCRIPT_ERROR, e.getMessage());
    }

    private EngineExecResult handleWithIgnoreAllStrategy(Exception e) {
        if (e.getCause() instanceof InvocationTargetException) {
            InvocationTargetException invocationTargetException = (InvocationTargetException) e.getCause();
            Throwable targetExceptionCause = invocationTargetException.getCause();
            if (targetExceptionCause instanceof BusinessException) {
                BusinessException businessException = (BusinessException) targetExceptionCause;
                return EngineExecResult.fail(businessException.getMessage());
            }
        }
        LOGGER.error("execute throw Throwable with IgnoreAllStrategy", e);
        return EngineExecResult.error();
    }

    private EngineExecResult handleWithIgnoreBizStrategy(Exception e) {
        if (e.getCause() instanceof InvocationTargetException) {
            InvocationTargetException invocationTargetException = (InvocationTargetException) e.getCause();
            Throwable targetExceptionCause = invocationTargetException.getCause();
            if (targetExceptionCause instanceof BusinessException) {
                BusinessException businessException = (BusinessException) targetExceptionCause;
                return EngineExecResult.fail(businessException.getMessage());
            } else {
                LOGGER.error("execute throw Throwable with IgnoreBizStrategy", targetExceptionCause);
            }
        }
        LOGGER.error("execute throw Throwable with unknown reason", e);
        throw new BusinessException(CommonInnerCode.QL_SCRIPT_ERROR, e.getMessage());
    }

}

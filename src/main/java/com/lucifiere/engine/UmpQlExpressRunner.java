package com.lucifiere.engine;

import com.lucifiere.engine.register.ActivityFuncSpec;
import com.ql.util.express.DefaultExpressResourceLoader;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * （1）打通了spring容器，通过扩展IExpressContext->CalculateExpressContext
 * 获取本地变量的时候，可以获取到spring的bean
 * （2）在runner初始化的时候，使用了函数映射功能：addFunctionOfServiceMethod
 * （3）在runner初始化的时候，使用了代码映射功能：addMacro
 *
 * @author 忠魂
 */
@Component
public class UmpQlExpressRunner implements ApplicationContextAware {

    /**
     * The Express runner.
     */
    private ExpressRunner expressRunner = new ExpressRunner();

    /**
     * The Register.
     */
    private AtomicBoolean register = new AtomicBoolean();
    /**
     * spring上下文
     */
    private ApplicationContext applicationContext;

    /**
     * The Express resource loader.
     */
    private DefaultExpressResourceLoader expressResourceLoader = new DefaultExpressResourceLoader();

    /**
     * 预编译产品脚本
     *
     * @param express the express
     * @throws Exception the exception
     */
    public void compileExpress(String express) throws Exception {
        expressRunner.getInstructionSetFromLocalCache(express);
    }

    /**
     * 执行脚本
     *
     * @param statement 执行语句
     * @param context   上下文
     * @return the object
     * @throws Exception the exception
     */
    public Object execute(String statement, EngineContext context) throws Exception {
        registerRunner();
        IExpressContext expressContext = new CalculateExpressContext(context, applicationContext);
        return expressRunner.execute(statement, expressContext, null, true, true);
    }

    /**
     * Register runner.
     *
     * @throws Exception the exception
     */
    private synchronized void registerRunner() throws Exception {
        if (Boolean.FALSE.equals(register.get())) {
            List<ActivityFuncSpec> funcSpecs = ActivityFuncSpecManager.getInstance().getAllActivityFuncSpec();
            if (CollectionUtils.isEmpty(funcSpecs)) {
                throw new RuntimeException("======初始化表达式失败======= ActivityFuncSpec list is null!!!!!");
            }
            for (ActivityFuncSpec funcSpec : funcSpecs) {
                expressRunner.addMacro(funcSpec.getMacroName(), funcSpec.getExpress());
            }
            expressRunner.addFunctionOfClassMethod("ACTIVITY_PARAM_MATCH", UmpQlExpressRunner.class.getName(),
                    "activityParamMatch", new Class[]{EngineContext.class, String.class, String.class}, null);
            expressRunner.addFunctionOfClassMethod("SET_ACTIVITY_PARAM_VALUE",
                    UmpQlExpressRunner.class.getName(),
                    "setActivityParamValue", new Class[]{EngineContext.class, String.class, String.class},
                    null);
            register.set(true);
        }
    }

    /**
     * 处理上下文中参数的匹配情况
     * 当一个activity组件被多次调用时，key值是不同的，需要映射到标准key定义中
     *
     * @param context   引擎上线文中的
     * @param stdKey    activity组件的标准key定义
     * @param sourceKey 当前变量key
     */
    public static void activityParamMatch(EngineContext context, String stdKey, String sourceKey) {
        if (context != null) {
            Map params = context.getPromotionContext().getParamMap();
            Object value = params.get(sourceKey);
            params.put(stdKey, value);
        }
    }

    /**
     * Sets application context.
     *
     * @param applicationContext the application context
     * @throws BeansException the beans exception
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 读取脚本文件内容
     *
     * @param expressName the express name
     * @return the string
     * @throws Exception the exception
     */
    public String loadExpress(String expressName) throws Exception {
        return expressResourceLoader.loadExpress(expressName);
    }

}

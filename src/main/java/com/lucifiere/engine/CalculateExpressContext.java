package com.lucifiere.engine;

import com.ql.util.express.IExpressContext;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * qlExpress计算上下文封装，集成spring context 便于bean管理
 *
 * @author 忠魂
 */
public class CalculateExpressContext implements IExpressContext<String, Object> {

    /**
     * 统一业务对象模型，所有的activity使用统一的入参
     */
    private EngineContext context;

    /**
     * The Application context.
     */
    private ApplicationContext applicationContext;

    /**
     * The constant beanMap.
     */
    private static Map<Object, Object> beanMap = new ConcurrentHashMap<>(512);

    /**
     * Instantiates a new Calculate express context.
     *
     * @param engineContext      the engine context
     * @param applicationContext the application context
     */
    public CalculateExpressContext(EngineContext engineContext, ApplicationContext applicationContext) {
        this.context = engineContext;
        this.applicationContext = applicationContext;
    }

    /**
     * 根据key从容器里面获取对象
     *
     * @param name the name
     * @return the object
     */
    @Override
    public Object get(Object name) {
        Object result = getValue(name);
        try {
            if (result == null) {
                result = beanMap.get(name);
                if (result == NullObject.getInstance()) {
                    return null;
                }
                if (result == null) {
                    if (this.applicationContext != null
                        && this.applicationContext.containsBean((String)name)) {
                        // 如果在Spring容器中包含bean，则返回String的Bean
                        result = this.applicationContext.getBean((String)name);
                        beanMap.put(name, result);
                    } else {
                        beanMap.put(name, NullObject.getInstance());
                    }
                }
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException("表达式容器获取对象失败 beanName=" + name, e);
        }
        //对boolean值进行特殊处理
        boolean check = result.equals(Boolean.TRUE.toString()) || result.equals(
            Boolean.FALSE.toString());
        if (check) {
            result = Boolean.valueOf(result.toString());
        }
        return result;
    }

    /**
     * 把key-value放到容器里面去
     *
     * @param name   the name
     * @param object the object
     * @return the object
     */
    @Override
    public Object put(String name, Object object) {
        Object obj = null;
        if (this.context != null) {
            Map<String, String> paramMap = context.getPromotionContext().getParamMap();
            if (paramMap != null) {
                obj = paramMap.put(name, object.toString());
            }
        }
        return obj;
    }

    /**
     * Gets value.
     *
     * @param name the name
     * @return the value
     */
    private Object getValue(Object name) {
        String key = (String)name;
        String s = "context";
        if (s.equals(key)) {
            return this.context;
        }
        Object obj = null;
        if (this.context != null) {
            Map<String, String> paramMap = context.getPromotionContext().getParamMap();
            if (paramMap != null) {
                obj = paramMap.get(name);
            }
        }
        return obj;
    }
}

package com.lucifiere.bef.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;

/**
 * Bean 工具包
 *
 * @author 沾清
 */
public class BefBeanUtils {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BefBeanUtils.class);

    /**
     * Gets bean via class.
     *
     * @param <T>       the type parameter
     * @param beanClass the bean class
     * @return the bean via class
     */
    public static <T> T getBeanViaClass(Class<?> beanClass) {
        if (null == beanClass) {
            return null;
        }
        //尝试从Spring注册的Bean中获取
        try {
            SpringAnnotationResult result = getSpringAnnotationResult(beanClass);
            if (result.isHasAnnotation()) {
                return StringUtils.isEmpty(result.getValue()) ? SpringApplicationContextHolder.getSpringBean(beanClass)
                    : SpringApplicationContextHolder.getSpringBean(result.getValue());
            }
        } catch (Throwable throwable) {
            LOGGER.error("Failed to Find SpringBean", throwable);
        }
        //没找到，再尝试用反射方式构建
        return createBeanInstance(beanClass);
    }

    /**
     * Gets spring annotation result.
     *
     * @param beanClass the bean class
     * @return the spring annotation result
     */
    private static SpringAnnotationResult getSpringAnnotationResult(Class<?> beanClass) {
        Service service = getAnnotation(beanClass, Service.class);
        if (null != service) {
            return new SpringAnnotationResult(true, service.value());
        }
        Repository repository = getAnnotation(beanClass, Repository.class);
        if (null != repository) {
            return new SpringAnnotationResult(true, repository.value());
        }
        Component component = getAnnotation(beanClass, Component.class);
        if (null != component) {
            return new SpringAnnotationResult(true, component.value());
        }
        return new SpringAnnotationResult(false, null);
    }

    /**
     * Create bean instance t.
     *
     * @param <T>       the type parameter
     * @param beanClass the bean class
     * @return the t
     */
    private static <T> T createBeanInstance(Class<?> beanClass) {
        try {
            return (T)beanClass.newInstance();
        } catch (Throwable e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("Failed to CreateBeanInstance", e);
            }
            return null;
        }
    }
}

/**
 * The type Spring annotation result.
 */
class SpringAnnotationResult {

    /**
     * The Has annotation.
     */
    private boolean hasAnnotation;

    /**
     * The Value.
     */
    private String value;

    /**
     * Instantiates a new Spring annotation result.
     *
     * @param hasAnnotation the has annotation
     * @param value         the value
     */
    public SpringAnnotationResult(boolean hasAnnotation, String value) {
        this.hasAnnotation = hasAnnotation;
        this.value = value;
    }

    /**
     * Gets hasAnnotation
     *
     * @return the hasAnnotation
     */
    public boolean isHasAnnotation() {
        return hasAnnotation;
    }

    /**
     * Sets hasAnnotation
     *
     * @param hasAnnotation the hasAnnotation
     */
    public void setHasAnnotation(boolean hasAnnotation) {
        this.hasAnnotation = hasAnnotation;
    }

    /**
     * Gets value
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }
}


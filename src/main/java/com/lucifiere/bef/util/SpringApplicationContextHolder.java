package com.lucifiere.bef.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context Holder
 *
 * @author 沾清
 */
@Component("befSpringContextHolder")
public class SpringApplicationContextHolder implements ApplicationContextAware {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringApplicationContextHolder.class);

    /**
     * spring application context.
     */
    private static ApplicationContext context;

    /**
     * Sets application context.
     *
     * @param context the context
     * @throws BeansException the beans exception
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringApplicationContextHolder.context = context;
    }

    /**
     * get spring bean via name.
     *
     * @param <T>      the generic of the spring bean.
     * @param beanName the bean's name.
     * @return the found spring bean.
     */
    public static <T> T getSpringBean(String beanName) {
        StringUtils.isNotBlank(beanName);
        if (null == context) {
            LOGGER.warn("spring application context is not injected");
            return null;
        }
        return (T)context.getBean(beanName);
    }

    /**
     * get the spring bean via the Class.
     *
     * @param <T>       the generic type of the bean.
     * @param beanClass the bean class.
     * @return the found bean.
     */
    public static <T> T getSpringBean(Class beanClass) {
        String beanName = StringUtils.uncapitalize(beanClass.getSimpleName());
        T bean = null;
        try {
            bean = (T)context.getBean(beanClass);
        } catch (BeansException e) {
            LOGGER.warn("spring application context is not injected by class：" + beanClass.getName());
            try {
                bean = getSpringBean(beanName);
            } catch (BeansException ex) {
                LOGGER.warn("spring application context is not injected by name：" + beanName);
            }
        }
        return bean;
    }

    /**
     * get spring bean names.
     *
     * @return the string [ ]
     */
    public static String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }

    /**
     * Sets context
     *
     * @param context the context
     */
    public static void setContext(ApplicationContext context) {
        SpringApplicationContextHolder.context = context;
    }
}

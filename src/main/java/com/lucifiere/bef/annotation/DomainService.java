package com.lucifiere.bef.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 域服务
 *
 * @author 沾清
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DomainService {

    /**
     * 代码
     *
     * @return the string
     */
    String code();

    /**
     * 名字
     *
     * @return the string
     */
    String name() default "";

    /**
     * 描述
     *
     * @return the string
     */
    String desc() default "";
}

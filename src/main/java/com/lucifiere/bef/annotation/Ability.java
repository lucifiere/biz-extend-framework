package com.lucifiere.bef.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 域能力扫描注解
 *
 * @author 沾清
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Ability {

    /**
     * 命名空间前缀
     *
     * @return the string
     */
    String parent();

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

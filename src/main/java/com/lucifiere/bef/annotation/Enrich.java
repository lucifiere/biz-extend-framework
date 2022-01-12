package com.lucifiere.bef.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 命名
 *
 * @author 沾清
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Enrich {

    /**
     * Name string.
     *
     * @return the name of current meta.
     */
    String name();

    /**
     * Priority int.
     *
     * @return the priority of current meta.
     */
    int priority() default 1000;
}

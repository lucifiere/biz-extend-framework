package com.lucifiere.bef.util;

import org.apache.commons.lang3.ClassUtils;

/**
 * The type Bef class utils.
 *
 * @author 沾清
 */
public class BefClassUtils extends ClassUtils {

    /**
     * Is sub class of boolean.
     *
     * @param subClass         the sub class
     * @param targetSuperClass the target super class
     * @return the boolean
     */
    public static boolean isSubClassOf(Class<?> subClass, Class targetSuperClass) {
        if (ClassUtils.isAssignable(subClass, targetSuperClass)) {
            return true;
        }

        Class<?> superClass = subClass.getSuperclass();
        while (null != superClass) {
            if (superClass.equals(Object.class)) {
                return false;
            }
            if (ClassUtils.isAssignable(superClass, targetSuperClass)) {
                return true;
            }
            superClass = superClass.getSuperclass();
        }

        return false;
    }

}

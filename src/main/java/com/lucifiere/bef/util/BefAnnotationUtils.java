package com.lucifiere.bef.util;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Annotation工具类
 *
 * @author 沾清
 */
public class BefAnnotationUtils extends AnnotationUtils {

    /**
     * The constant ANNOTATED_INTERFACE_CACHE.
     */
    private static final Map<Class<?>, Boolean> ANNOTATED_INTERFACE_CACHE = new WeakHashMap<>();

    /**
     * Find annotation a.
     *
     * @param <A>            the type parameter
     * @param method         the method
     * @param annotationType the annotation type
     * @return the a
     */
    public static <A extends Annotation> A findAnnotation(Method method, Class<A> annotationType) {
        A annotation = AnnotationUtils.findAnnotation(method, annotationType);
        if (null != annotation) { return annotation; }

        Class<?> clazz = method.getDeclaringClass();
        annotation = searchOnInterfaces(method, annotationType, clazz.getInterfaces());

        while (annotation == null) {
            clazz = clazz.getSuperclass();
            if (clazz == null || clazz.equals(Object.class)) {
                break;
            }
            try {
                Method equivalentMethod = clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
                annotation = getAnnotation(equivalentMethod, annotationType);
            } catch (NoSuchMethodException ex) {
                // No equivalent method found
            }
            if (annotation == null) {
                annotation = searchOnInterfaces(method, annotationType, clazz.getInterfaces());
            }
        }
        return annotation;
    }

    /**
     * Search on interfaces a.
     *
     * @param <A>            the type parameter
     * @param method         the method
     * @param annotationType the annotation type
     * @param ifcs           the ifcs
     * @return the a
     */
    private static <A extends Annotation> A searchOnInterfaces(Method method, Class<A> annotationType,
                                                               Class<?>[] ifcs) {
        A annotation = null;
        for (Class<?> iface : ifcs) {
            if (isInterfaceWithAnnotatedMethods(iface)) {
                try {
                    Method equivalentMethod = iface.getMethod(method.getName(), method.getParameterTypes());
                    annotation = getAnnotation(equivalentMethod, annotationType);
                } catch (NoSuchMethodException ex) {
                    // if not find method, may be param is generic.
                    Method[] methods = iface.getMethods();
                    for (Method oneMethod : methods) {
                        if (oneMethod.getName().equals(method.getName())) {
                            annotation = getAnnotation(oneMethod, annotationType);
                            if (annotation != null) {
                                break;
                            }
                        }
                    }
                }
                if (annotation != null) {
                    break;
                }

                Class<?>[] superIfcs = iface.getInterfaces();
                annotation = searchOnInterfaces(method, annotationType, superIfcs);
            }
        }
        return annotation;
    }

    /**
     * Is interface with annotated methods boolean.
     *
     * @param iface the iface
     * @return the boolean
     */
    private static boolean isInterfaceWithAnnotatedMethods(Class<?> iface) {
        synchronized (ANNOTATED_INTERFACE_CACHE) {
            Boolean flag = ANNOTATED_INTERFACE_CACHE.get(iface);
            if (flag != null) {
                return flag;
            }
            boolean found = false;
            for (Method ifcMethod : iface.getMethods()) {
                if (ifcMethod.getAnnotations().length > 0) {
                    found = true;
                    break;
                }
            }
            ANNOTATED_INTERFACE_CACHE.put(iface, found);
            return found;
        }
    }
}

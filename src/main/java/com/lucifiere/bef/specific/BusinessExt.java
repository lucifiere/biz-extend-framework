package com.lucifiere.bef.specific;

import com.lucifiere.bef.annotation.AbilityExtension;
import com.lucifiere.bef.annotation.BusinessExtension;
import com.lucifiere.bef.annotation.ScanSkip;
import com.lucifiere.bef.util.BefAnnotationUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

/**
 * The interface Business ext.
 *
 * @author 沾清
 */
public interface BusinessExt extends IExtensionPointsFacade {

    /**
     * The constant logger.
     */
    Logger logger = LoggerFactory.getLogger(BusinessExt.class);

    /**
     * The constant CODE_MAP.
     */
    Map<Class, Set<String>> CODE_MAP = new ConcurrentHashMap<>(16);

    /**
     * The constant CLASS_POINT_MAP.
     */
    Map<Class, Map<Key, IExtensionPoints>> CLASS_POINT_MAP = new ConcurrentHashMap<>(16);

    /**
     * The type Key.
     */
    class Key {
        /**
         * The Scenario.
         */
        private String scenario;
        /**
         * The Extension code.
         */
        private String extensionCode;

        /**
         * Instantiates a new Key.
         *
         * @param scenario      the scenario
         * @param extensionCode the extension code
         */
        public Key(String scenario, String extensionCode) {
            this.scenario = scenario;
            this.extensionCode = extensionCode;
        }

        /**
         * Equals boolean.
         *
         * @param o the o
         * @return the boolean
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            Key key = (Key)o;

            if (!Objects.equals(scenario, key.scenario)) { return false; }
            return Objects.equals(extensionCode, key.extensionCode);
        }

        /**
         * Hash code int.
         *
         * @return the int
         */
        @Override
        public int hashCode() {
            int result = scenario != null ? scenario.hashCode() : 0;
            result = 31 * result + (extensionCode != null ? extensionCode.hashCode() : 0);
            return result;
        }
    }

    /**
     * The type Key 2.
     */
    class Key2 {
        /**
         * The Klass.
         */
        private Class klass;
        /**
         * The Extension code.
         */
        private String extensionCode;

        /**
         * Instantiates a new Key 2.
         *
         * @param klass         the klass
         * @param extensionCode the extension code
         */
        public Key2(Class klass, String extensionCode) {
            this.klass = klass;
            this.extensionCode = extensionCode;
        }

        /**
         * Equals boolean.
         *
         * @param o the o
         * @return the boolean
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }

            Key2 key = (Key2)o;

            if (!klass.equals(key.klass)) { return false; }
            return extensionCode.equals(key.extensionCode);
        }

        /**
         * Hash code int.
         *
         * @return the int
         */
        @Override
        public int hashCode() {
            int result = klass.hashCode();
            result = 31 * result + extensionCode.hashCode();
            return result;
        }
    }

    /**
     * Gets extension points by code.
     *
     * @param extensionCode the extension code
     * @return the extension points by code
     */
    @Override
    @ScanSkip
    default IExtensionPoints getExtensionPointsByCode(String extensionCode) {
        String scenario = "None#";

        Key extensionCodeKey = new Key(scenario, extensionCode);

        Map<Key, IExtensionPoints> pointMap = CLASS_POINT_MAP.get(this.getClass());
        if (pointMap != null) {
            IExtensionPoints point = pointMap.get(extensionCodeKey);
            if (point != null) {
                return point;
            }
        }
        IExtensionPoints point = innerGetExtensionPointsByCode(extensionCode);
        if (point != null) {
            if (pointMap == null) {
                pointMap = new ConcurrentHashMap<>(16);
                pointMap.put(extensionCodeKey, point);
                CLASS_POINT_MAP.put(this.getClass(), pointMap);
            } else {
                pointMap.put(extensionCodeKey, point);
            }
        }
        return point;
    }

    /**
     * do get the extension points realization by extension CODE.
     *
     * @param extensionCode the extension point's CODE.
     * @return IExtensionPoints extension points
     */
    @ScanSkip
    default IExtensionPoints innerGetExtensionPointsByCode(String extensionCode) {
        if (StringUtils.isEmpty(extensionCode)) {
            return null;
        }
        boolean isMatch = supportedExtCodes().contains(extensionCode);
        if (!isMatch) {
            return null;
        }

        //递归看是否有子Facade
        for (BusinessExt facade : getAllSubBusinessExt()) {
            IExtensionPoints extension = facade.innerGetExtensionPointsByCode(extensionCode);
            if (null == extension) {
                continue;
            }
            return extension;
        }
        return this;
    }

    /**
     * Supported ext codes set.
     *
     * @return the set
     */
    default Set<String> supportedExtCodes() {
        Class key = getClass();
        Set<String> result = CODE_MAP.get(key);
        if (CollectionUtils.isEmpty(result)) {
            Set<String> supportedCodes = getAllSubBusinessExt().stream()
                .filter(Objects::nonNull)
                .flatMap(p -> p.supportedExtCodes().stream()).collect(Collectors.toSet());
            List<String> extensionCodes = newArrayList();
            Method[] methods = this.getClass().getMethods();
            for (Method method : methods) {
                AbilityExtension abilityExtension = BefAnnotationUtils.findAnnotation(method,
                    AbilityExtension.class);
                String code = null == abilityExtension ? null : abilityExtension.code();
                if (null == abilityExtension) {
                    BusinessExtension businessExtension = BefAnnotationUtils.findAnnotation(method,
                        BusinessExtension.class);
                    code = null == businessExtension ? null : businessExtension.code();
                }
                if (StringUtils.isNotEmpty(code)) {
                    extensionCodes.add(code);
                }
            }
            supportedCodes.addAll(extensionCodes);
            CODE_MAP.putIfAbsent(key, supportedCodes);
            return supportedCodes;
        } else {
            return result;
        }
    }

    /**
     * the realization cache key.
     *
     * @param extensionCode extensionCode
     * @return key of cache.
     */
    default Key2 realizationKey(String extensionCode) {
        return new Key2(getClass(), extensionCode);
    }

    /**
     * Gets all sub business ext.
     *
     * @return the all sub business ext
     */
    @ScanSkip
    default List<BusinessExt> getAllSubBusinessExt() {
        List<BusinessExt> children = new ArrayList<>();
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (ClassUtils.isAssignable(method.getReturnType(), BusinessExt.class)) {
                ScanSkip scanSkip = AnnotationUtils.findAnnotation(method, ScanSkip.class);
                if (null != scanSkip) {
                    continue;
                }
                try {
                    method.setAccessible(true);
                    BusinessExt businessExt = (BusinessExt)method.invoke(this);
                    if (children.stream().noneMatch(p -> isSubBusinessClassMatched(p, businessExt))) {
                        children.add(businessExt);
                    }
                } catch (Throwable th) {
                    logger.warn(th.getMessage(), th);
                }
            }
        }
        return children;
    }

    /**
     * Is sub business class matched boolean.
     *
     * @param child       the child
     * @param businessExt the business ext
     * @return the boolean
     */
    default boolean isSubBusinessClassMatched(BusinessExt child, BusinessExt businessExt) {
        if (null == child || null == businessExt) {
            return false;
        }
        return child.getClass().equals(businessExt.getClass());
    }

    /**
     * Clear.
     */
    static void clear() {
        CODE_MAP.clear();
        CLASS_POINT_MAP.clear();
    }
}

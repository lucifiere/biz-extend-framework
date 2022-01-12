package com.lucifiere.bef.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

/**
 * The type Template spec.
 *
 * @author 沾清
 */
public class TemplateSpec extends BaseSpec {

    /**
     * 模板类型
     */
    private String type;
    /**
     * The Sub type.
     */
    private String subType;
    /**
     * The Realizations.
     */
    private List<Realization> realizations = Lists.newArrayList();
    /**
     * The Related extension points.
     */
    private Set<ExtensionPointSpec> relatedExtensionPoints = Sets.newHashSet();
    /**
     * The Provide extension points.
     */
    private Set<ExtensionPointSpec> provideExtensionPoints = Sets.newHashSet();

    /**
     * Is business boolean.
     *
     * @return the boolean
     */
    public boolean isBusiness() {
        return "BUSINESS".equals(type);
    }

    /**
     * Is product boolean.
     *
     * @return the boolean
     */
    public boolean isProduct() {
        return "PRODUCT".equals(type);
    }

    /**
     * The type Realization.
     */
    public static class Realization {
        /**
         * 模板编码
         */
        private String templateCode;

        /**
         * 顶层实现类
         */
        private String classPath;

        /**
         * Gets templateCode
         *
         * @return the templateCode
         */
        public String getTemplateCode() {
            return templateCode;
        }

        /**
         * Sets templateCode
         *
         * @param templateCode the templateCode
         */
        public void setTemplateCode(String templateCode) {
            this.templateCode = templateCode;
        }

        /**
         * Gets classPath
         *
         * @return the classPath
         */
        public String getClassPath() {
            return classPath;
        }

        /**
         * Sets classPath
         *
         * @param classPath the classPath
         */
        public void setClassPath(String classPath) {
            this.classPath = classPath;
        }
    }
}

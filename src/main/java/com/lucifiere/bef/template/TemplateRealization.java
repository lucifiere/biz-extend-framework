package com.lucifiere.bef.template;

import com.google.common.collect.Lists;
import com.lucifiere.bef.specific.IExtensionPointsFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板关联
 *
 * @param <T> the type parameter
 * @author 沾清
 */
public class TemplateRealization<T extends IExtensionPointsFacade> {

    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateRealization.class);

    /**
     * The Template code.
     */
    private String templateCode;

    /**
     * The Scenario.
     */
    private String scenario;

    /**
     * The Extension realization.
     */
    private T extensionRealization;

    /**
     * Of list.
     *
     * @param targetClass the target class
     * @return the list
     */
    public static List<TemplateRealization> of(Class<?> targetClass) {
        if (null == targetClass) { return null; }
        TemplateExt templateExt = AnnotationUtils.findAnnotation(targetClass, TemplateExt.class);
        if (null == templateExt) { return null; }
        try {
            List<TemplateRealization> templateRealizations = Lists.newArrayList();
            TemplateRealization realization = TemplateRealization.of(templateExt.code(), templateExt.scenario(),
                targetClass);
            if (null != realization) {
                templateRealizations.add(realization);
            }
            return templateRealizations;
        } catch (Throwable th) {
            LOGGER.error(th.getMessage(), th);
            return new ArrayList<>();
        }
    }

    /**
     * Of template realization.
     *
     * @param templateCode the template code
     * @param scenario     the scenario
     * @param targetClass  the target class
     * @return the template realization
     */
    public static TemplateRealization of(String templateCode, String scenario, Class<?> targetClass) {
        if (StringUtils.isEmpty(templateCode) || null == targetClass) { return null; }
        try {
            TemplateRealization realization = new TemplateRealization();
            realization.setTemplateCode(templateCode);
            if (StringUtils.isNotEmpty(scenario)) { realization.scenario = scenario; }
            realization.setExtensionRealization((IExtensionPointsFacade)targetClass.newInstance());
            return realization;
        } catch (Throwable th) {
            LOGGER.error(
                "The templateCode [" + templateCode + "]'s realization target class [" + targetClass.getName()
                    + "] is error!");
            LOGGER.error(th.getMessage(), th);
            return null;
        }
    }

    /**
     * Gets scenario
     *
     * @return the scenario
     */
    public String getScenario() {
        return scenario;
    }

    /**
     * Sets scenario
     *
     * @param scenario the scenario
     */
    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    /**
     * Gets extensionRealization
     *
     * @return the extensionRealization
     */
    public T getExtensionRealization() {
        return extensionRealization;
    }

    /**
     * Sets extensionRealization
     *
     * @param extensionRealization the extensionRealization
     */
    public void setExtensionRealization(T extensionRealization) {
        this.extensionRealization = extensionRealization;
    }

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
}

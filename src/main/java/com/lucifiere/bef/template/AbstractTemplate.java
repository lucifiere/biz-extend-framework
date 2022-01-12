package com.lucifiere.bef.template;

import com.lucifiere.bef.model.BaseSpec;
import com.lucifiere.bef.specific.IExtensionPointsFacade;
import com.lucifiere.bef.util.SpringApplicationContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 配置模板基类.
 *
 * @param <ExtensionFacade> the type parameter
 * @author 沾清
 */
public abstract class AbstractTemplate<ExtensionFacade extends IExtensionPointsFacade> {
    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTemplate.class);
    /**
     * The code
     */
    private String code;
    /**
     * the name of template.
     */
    private String name;

    /**
     * The Priority.
     */
    private int priority = 1000;

    /**
     * template's description.
     */
    private String description;

    /**
     * the template sub type.
     */
    private String subType;

    /**
     * The Realizations.
     */
    private List<TemplateRealization> realizations = new ArrayList<>();
    /**
     * The Realizations in array.
     */
    private TemplateRealization[] realizationsInArray;

    /**
     * The Template realization template code.
     */
    private Map<String, TemplateRealization> templateRealizationTemplateCode = new HashMap<>();
    /**
     * The Template ext realization.
     */
    private volatile TemplateRealization[] templateExtRealization = null;

    /**
     * check current Template whether is effect.
     *
     * @param <T> the generic type of the input object.
     * @param obj the input object for checking.
     * @return true or false.
     */
    public <T> boolean isEffect(T obj) {
        return false;
    }

    /**
     * check current Template whether is enabled..
     * (Since the template is Effect, but the business can custom to disable it).
     *
     * @param <T> the generic type of the input object.
     * @param obj the input object for checking.
     * @return true or false.
     */
    public <T> boolean isEnabled(T obj) {
        return true;
    }

    /**
     * Clone template.
     *
     * @return the template
     */
    @Deprecated
    @Override
    public AbstractTemplate clone() {
        try {
            return (AbstractTemplate)super.clone();
        } catch (CloneNotSupportedException var2) {
            return this;
        }
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

        AbstractTemplate template = (AbstractTemplate)o;

        return StringUtils.equals(getCode(), template.getCode());
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        if (null == getCode()) {
            return super.hashCode();
        }
        int result = getCode().hashCode();
        result = 31 * result;
        return result;
    }

    /**
     * create the template form the specific class.
     *
     * @param baseSpec the product specification.
     * @param aClass   the template class.
     * @return the created Template instance.
     */
    public static AbstractTemplate createFromClass(BaseSpec baseSpec, Class<?> aClass) {
        AbstractTemplate template = null;

        if (AbstractTemplate.class.isAssignableFrom(aClass)) {
            try {
                template = SpringApplicationContextHolder.getSpringBean(aClass);
                if (template == null) {
                    template = (AbstractTemplate)aClass.newInstance();
                }
            } catch (Exception e) {
                LOGGER.info(e.getMessage(), e);
                try {
                    template = (AbstractTemplate)aClass.newInstance();
                    if (template == null) {
                        LOGGER.error("Failed to create Class from Spring or Reflect. " + aClass.getName(), e);
                    }
                } catch (InstantiationException | IllegalAccessException e1) {
                    LOGGER.error("Failed to create Class from Spring or Reflect. " + aClass.getName(), e);
                }
            }

            if (template != null) {
                template.setName(baseSpec.getName());
                template.setCode(baseSpec.getCode());
                template.setDescription(baseSpec.getDescription());
            }
        }
        return template;
    }

    /**
     * Add realizations.
     *
     * @param templateRealizations the template realizations
     */
    public void addRealizations(List<TemplateRealization> templateRealizations) {
        for (TemplateRealization templateRealization : templateRealizations) {
            this.realizations.add(templateRealization);
            this.templateRealizationTemplateCode.put(templateRealization.getTemplateCode(), templateRealization);
        }
    }

    /**
     * Contains realization by template code boolean.
     *
     * @param templateCode the template code
     * @return the boolean
     */
    public boolean containsRealizationByTemplateCode(String templateCode) {
        return this.templateRealizationTemplateCode.containsKey(templateCode);
    }

    /**
     * Gets template realization by template code.
     *
     * @param templateCode the template code
     * @return the template realization by template code
     */
    public TemplateRealization getTemplateRealizationByTemplateCode(String templateCode) {
        return this.templateRealizationTemplateCode.get(templateCode);
    }

    /**
     * Gets realizations.
     *
     * @return the realizations
     */
    public List<TemplateRealization> getRealizations() {
        return Collections.unmodifiableList(this.realizations);
    }

    /**
     * Generate.
     */
    public void generate() {
        List<TemplateRealization> tempRealizations = new ArrayList<>(this.realizations.size());
        for (TemplateRealization p : this.realizations) {
            if (null != p.getExtensionRealization() &&
                TemplateManager.templateCodeMatched(p.getTemplateCode(), this.getCode())) {
                tempRealizations.add(p);
            }
        }

        this.templateExtRealization = tempRealizations.toArray(new TemplateRealization[0]);
        this.realizationsInArray = this.realizations.toArray(new TemplateRealization[0]);
    }

    /**
     * Get template ext realization template realization [ ].
     *
     * @return the template realization [ ]
     */
    TemplateRealization[] getTemplateExtRealization() {
        TemplateRealization[] templateRealizations = this.templateExtRealization;
        if (templateRealizations != null) {
            return templateRealizations;
        }
        this.generate();
        return this.templateExtRealization;
    }

    /**
     * Get realizations in array template realization [ ].
     *
     * @return the template realization [ ]
     */
    public TemplateRealization[] getRealizationsInArray() {
        TemplateRealization[] templateRealizations = this.realizationsInArray;
        if (templateRealizations == null) {
            this.generate();
        }
        return this.realizationsInArray;
    }

    /**
     * Gets name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets priority
     *
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets priority
     *
     * @param priority the priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Gets description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets subType
     *
     * @return the subType
     */
    public String getSubType() {
        return subType;
    }

    /**
     * Sets subType
     *
     * @param subType the subType
     */
    public void setSubType(String subType) {
        this.subType = subType;
    }

    /**
     * Gets code
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }
}

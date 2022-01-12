package com.lucifiere.bef.model;

import com.lucifiere.bef.specific.impl.ExtensionPointType;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 能力示例定义
 *
 * @author 沾清
 */
public class AbilityInstanceSpec extends BaseSpec {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbilityInstanceSpec.class);

    /**
     * the ability's unique code which current instance belong to.
     */
    private String abilityCode;

    /**
     * the instance class of the ability.
     */
    private String instanceClass;

    /**
     * The priority of the ability instance.
     */
    private int priority = 1000;

    /**
     * the extension points of current ability.
     */
    private Set<ExtensionPointSpec> systemExtPoints = Sets.newHashSet();

    /**
     * The Business ext points.
     */
    private Set<ExtensionPointSpec> businessExtPoints = Sets.newHashSet();

    /**
     * The All ext points.
     */
    private Set<ExtensionPointSpec> allExtPoints = Sets.newHashSet();

    /**
     * add the extension point's specification to current domain ability.
     *
     * @param extensionPointSpec extension point specification.
     */
    public void addExtensionPoint(ExtensionPointSpec extensionPointSpec) {
        if (null == extensionPointSpec) {
            return;
        }
        Set<ExtensionPointSpec> addToList = extensionPointSpec.getType() == ExtensionPointType.SYSTEM ?
            systemExtPoints : businessExtPoints;
        if (extensionPointSpec.inList(addToList)) {
            LOGGER.warn(String
                .format("ExtensionPoint重复注册，[%s-%s]", extensionPointSpec.getName(), extensionPointSpec.getCode()));
            return;
        }
        addToList.add(extensionPointSpec);
    }

    /**
     * 构建全量AllExtPointsCache
     */
    public void buildAllExtPoints() {
        allExtPoints.clear();
        allExtPoints.addAll(this.systemExtPoints);
        allExtPoints.addAll(this.businessExtPoints);
    }

    /**
     * Equals boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof AbilityInstanceSpec)) {
            return false;
        }
        AbilityInstanceSpec target = (AbilityInstanceSpec)obj;
        return StringUtils.equals(target.getCode(), this.getCode());
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        if (StringUtils.isNotEmpty(getCode())) {
            return getCode().hashCode();
        }
        return super.hashCode();
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return getCode();
    }

    /**
     * All extension points set.
     *
     * @return the set
     */
    public Set<ExtensionPointSpec> allExtensionPoints() {
        return this.allExtPoints;
    }

    /**
     * Gets abilityCode
     *
     * @return the abilityCode
     */
    public String getAbilityCode() {
        return abilityCode;
    }

    /**
     * Sets abilityCode
     *
     * @param abilityCode the abilityCode
     */
    public void setAbilityCode(String abilityCode) {
        this.abilityCode = abilityCode;
    }

    /**
     * Gets instanceClass
     *
     * @return the instanceClass
     */
    public String getInstanceClass() {
        return instanceClass;
    }

    /**
     * Sets instanceClass
     *
     * @param instanceClass the instanceClass
     */
    public void setInstanceClass(String instanceClass) {
        this.instanceClass = instanceClass;
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
     * Gets systemExtPoints
     *
     * @return the systemExtPoints
     */
    public Set<ExtensionPointSpec> getSystemExtPoints() {
        return systemExtPoints;
    }

    /**
     * Sets systemExtPoints
     *
     * @param systemExtPoints the systemExtPoints
     */
    public void setSystemExtPoints(Set<ExtensionPointSpec> systemExtPoints) {
        this.systemExtPoints = systemExtPoints;
    }

    /**
     * Gets businessExtPoints
     *
     * @return the businessExtPoints
     */
    public Set<ExtensionPointSpec> getBusinessExtPoints() {
        return businessExtPoints;
    }

    /**
     * Sets businessExtPoints
     *
     * @param businessExtPoints the businessExtPoints
     */
    public void setBusinessExtPoints(Set<ExtensionPointSpec> businessExtPoints) {
        this.businessExtPoints = businessExtPoints;
    }

    /**
     * Gets allExtPoints
     *
     * @return the allExtPoints
     */
    public Set<ExtensionPointSpec> getAllExtPoints() {
        return allExtPoints;
    }

    /**
     * Sets allExtPoints
     *
     * @param allExtPoints the allExtPoints
     */
    public void setAllExtPoints(Set<ExtensionPointSpec> allExtPoints) {
        this.allExtPoints = allExtPoints;
    }
}

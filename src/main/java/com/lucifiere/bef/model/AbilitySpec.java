package com.lucifiere.bef.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 能力定义
 *
 * @author 沾清
 */
public class AbilitySpec extends BaseSpec {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbilitySpec.class);

    /**
     * the domain code current ability belong to.
     */
    private String parentCode;

    /**
     * The Ability class.
     */
    private Class<?> abilityClass;

    /**
     * the ability instances of current ability.
     */
    private final Set<AbilityInstanceSpec> abilityInstances = Sets.newConcurrentHashSet();

    /**
     * abilityInstance map for cache.
     */
    private volatile Map<String, AbilityInstanceSpec> abilityInstanceSpecMap
        = null;

    /**
     * add the ability instance's specification to current domain ability.
     *
     * @param abilityInstanceSpec ability instance specification.
     */
    public void addAbilityInstance(AbilityInstanceSpec abilityInstanceSpec) {
        if (null == abilityInstanceSpec) {
            return;
        }
        if (abilityInstanceSpec.inList(abilityInstances)) {
            LOGGER.warn(String
                .format("AbilityInstance重复注册，[%s-%s]", abilityInstanceSpec.getName(), abilityInstanceSpec.getCode()));
            return;
        }
        abilityInstanceSpec.setAbilityCode(this.getCode());
        abilityInstances.add(abilityInstanceSpec);
        rebuildAbilityInstanceSpecMap();
    }

    /**
     * Add ability instance.
     *
     * @param instanceSpecs the instance specs
     */
    public void addAbilityInstance(List<AbilityInstanceSpec> instanceSpecs) {
        if (CollectionUtils.isEmpty(instanceSpecs)) {
            return;
        }
        for (AbilityInstanceSpec abilityInstanceSpec : instanceSpecs) {
            if (abilityInstanceSpec.inList(abilityInstances)) {
                LOGGER.warn(String
                    .format("AbilityInstance重复注册，[%s-%s]", abilityInstanceSpec.getName(),
                        abilityInstanceSpec.getCode()));
                continue;
            }
            abilityInstanceSpec.setAbilityCode(this.getCode());
            abilityInstances.add(abilityInstanceSpec);
        }
        rebuildAbilityInstanceSpecMap();
    }

    /**
     * get the ability instance via instance CODE.
     *
     * @param instanceCode the instance unique CODE.
     * @return the found DomainAbilityInstanceSpec.
     */
    public AbilityInstanceSpec getDomainAbilityInstanceSpec(String instanceCode) {
        Map<String, AbilityInstanceSpec> abilityInstanceSpecMap = this.abilityInstanceSpecMap;
        if (null == abilityInstanceSpecMap) {
            if (CollectionUtils.isEmpty(abilityInstances)) {
                this.abilityInstanceSpecMap = Maps.newConcurrentMap();
                return null;
            }
            abilityInstanceSpecMap = rebuildAbilityInstanceSpecMap();
        }
        return abilityInstanceSpecMap.get(instanceCode);
    }

    /**
     * Rebuild ability instance spec map map.
     *
     * @return the map
     */
    private Map<String, AbilityInstanceSpec> rebuildAbilityInstanceSpecMap() {
        this.abilityInstanceSpecMap = Maps.newConcurrentMap();
        if (CollectionUtils.isEmpty(abilityInstances)) {
            return abilityInstanceSpecMap;
        }
        for (AbilityInstanceSpec abilityInstance : abilityInstances) {
            if (StringUtils.isBlank(abilityInstance.getInstanceClass())) { continue; }
            abilityInstanceSpecMap.put(abilityInstance.getInstanceClass(), abilityInstance);
        }
        return abilityInstanceSpecMap;
    }

    /**
     * create a domain ability specification from unique ability CODE and description.
     *
     * @param abilityCode the unique CODE.
     * @param abilityName the name of ability.
     * @param abilityDesc the description.
     * @return the created DomainAbilitySpec.
     */
    public static AbilitySpec of(String abilityCode, String abilityName, String abilityDesc) {
        AbilitySpec abilitySpec = new AbilitySpec();
        abilitySpec.setCode(abilityCode);
        abilitySpec.setName(abilityName);
        abilitySpec.setDescription(abilityDesc);
        return abilitySpec;
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
        if (!(obj instanceof AbilitySpec)) {
            return false;
        }
        AbilitySpec target = (AbilitySpec)obj;
        return StringUtils.equals(target.getCode(), this.getCode());
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        if (StringUtils.isEmpty(this.getCode())) {
            return super.hashCode();
        }
        return this.getCode().hashCode();
    }

    /**
     * Gets abilityInstances
     *
     * @return the abilityInstances
     */
    public Set<AbilityInstanceSpec> getAbilityInstances() {
        return abilityInstances;
    }

    /**
     * Gets parentCode
     *
     * @return the parentCode
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * Sets parentCode
     *
     * @param parentCode the parentCode
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * Gets abilityClass
     *
     * @return the abilityClass
     */
    public Class<?> getAbilityClass() {
        return abilityClass;
    }

    /**
     * Sets abilityClass
     *
     * @param abilityClass the abilityClass
     */
    public void setAbilityClass(Class<?> abilityClass) {
        this.abilityClass = abilityClass;
    }
}

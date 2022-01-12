package com.lucifiere.bef.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lucifiere.bef.annotation.Domain;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 领域定义
 *
 * @author 沾清
 */
public class DomainSpec extends BaseSpec {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainSpec.class);

    /**
     * The Parent code.
     */
    private String parentCode;

    /**
     * the abilities of current domain.
     */
    private Set<AbilitySpec> domainAbilities = Sets.newHashSet();

    /**
     * the services of current domain.
     */
    private List<DomainServiceSpec> domainServices = Lists.newArrayList();

    /**
     * create a DomainSpec.
     *
     * @param parentCode        the parent code
     * @param domainCode        the domain unique code.
     * @param domainName        the name of domain.
     * @param domainDescription the domain description.
     * @return the created FunctionDomainSpec.
     */
    public static DomainSpec of(String parentCode, String domainCode, String domainName, String domainDescription) {
        DomainSpec domainSpec = new DomainSpec();
        if (StringUtils.isNotEmpty(parentCode)) {
            domainSpec.setParentCode(parentCode);
        }
        domainSpec.setCode(domainCode);
        domainSpec.setName(domainName);
        domainSpec.setDescription(domainDescription);
        return domainSpec;
    }

    /**
     * create a FunctionDomainSpec from annotation.
     *
     * @param domain the FunctionDomain annotation.
     * @return the created FunctionDomainSpec.
     */
    public static DomainSpec of(Domain domain) {
        DomainSpec domainSpec = new DomainSpec();
        domainSpec.setCode(domain.code());
        domainSpec.setName(domain.name());
        domainSpec.setDescription(domain.desc());
        return domainSpec;
    }

    /**
     * add ability to current domain.
     *
     * @param domainAbility the ability specification.
     */
    public void addDomainAbility(AbilitySpec domainAbility) {
        if (null == domainAbility) {
            return;
        }
        if (domainAbilities.contains(domainAbility)) {
            LOGGER.warn(String
                .format("有重复的DomainAbility [%s-%s]存在，不重复添加", domainAbility.getParentCode(), domainAbility.getCode()));
            return;
        }
        domainAbility.setParentCode(this.getCode());
        domainAbilities.add(domainAbility);
    }

    /**
     * batch register the domain abilities.
     *
     * @param abilitySpecs the domain ability specifications.
     */
    public void registerDomainAbilityDesc(AbilitySpec... abilitySpecs) {
        if (null == abilitySpecs) {
            return;
        }
        Collections.addAll(this.domainAbilities, abilitySpecs);
    }

    /**
     * All extension points set.
     *
     * @return all the extension point specification under current domain's abilities.
     */
    public Set<ExtensionPointSpec> allExtensionPoints() {
        Set<ExtensionPointSpec> extensionPointSpecList = new HashSet<>();
        for (AbilitySpec ability : domainAbilities) {
            for (AbilityInstanceSpec p : ability.getAbilityInstances()) {
                extensionPointSpecList.addAll(p.allExtensionPoints());
            }
        }
        return extensionPointSpecList;
    }

    /**
     * find the extension point under current domain with specific extension CODE.
     *
     * @param extensionCode the extension CODE to be found.
     * @return the found ExtensionPointSpec.
     */
    public ExtensionPointSpec findExtensionPointDesc(String extensionCode) {
        for (AbilitySpec ability : domainAbilities) {
            for (AbilityInstanceSpec p : ability.getAbilityInstances()) {
                for (ExtensionPointSpec ext : p.getSystemExtPoints()) {
                    if (extensionCode.equals(ext.getCode())) { return ext; }
                }
                for (ExtensionPointSpec ext : p.getBusinessExtPoints()) {
                    if (extensionCode.equals(ext.getCode())) { return ext; }
                }
            }
        }
        return null;
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
        if (!(obj instanceof DomainSpec)) {
            return false;
        }
        DomainSpec target = (DomainSpec)obj;
        return StringUtils.equals(target.getCode(), this.getCode());
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        if (null != this.getCode()) {
            return this.getCode().hashCode();
        }
        return super.hashCode();
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
     * Gets domainAbilities
     *
     * @return the domainAbilities
     */
    public Set<AbilitySpec> getDomainAbilities() {
        return domainAbilities;
    }

    /**
     * Sets domainAbilities
     *
     * @param domainAbilities the domainAbilities
     */
    public void setDomainAbilities(Set<AbilitySpec> domainAbilities) {
        this.domainAbilities = domainAbilities;
    }

    /**
     * Gets domainServices
     *
     * @return the domainServices
     */
    public List<DomainServiceSpec> getDomainServices() {
        return domainServices;
    }

    /**
     * Sets domainServices
     *
     * @param domainServices the domainServices
     */
    public void setDomainServices(List<DomainServiceSpec> domainServices) {
        this.domainServices = domainServices;
    }
}

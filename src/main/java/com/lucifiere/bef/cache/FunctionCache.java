package com.lucifiere.bef.cache;

import com.lucifiere.bef.annotation.Ability;
import com.lucifiere.bef.annotation.Domain;
import com.lucifiere.bef.annotation.Product;
import com.lucifiere.bef.model.*;
import com.lucifiere.bef.specific.impl.NotExistedExtensionPointSpec;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * The type Function cache.
 *
 * @author 沾清
 */
public class FunctionCache extends SimpleCache {

    /**
     * Do cache extension point spec extension point spec.
     *
     * @param extensionPointSpec the extension point spec
     * @return the extension point spec
     */
    public ExtensionPointSpec doCacheExtensionPointSpec(ExtensionPointSpec extensionPointSpec) {
        doCacheObjectExtensionPointSpec(extensionPointSpec.getCode(), extensionPointSpec);
        return extensionPointSpec;
    }

    /**
     * Gets all cached extension point spec.
     *
     * @return the all cached extension point spec
     */
    public Collection<ExtensionPointSpec> getAllCachedExtensionPointSpec() {
        return getAllCacheExtensionPointSpec();
    }

    /**
     * Gets all cached domains.
     *
     * @return the all cached domains
     */
    public Collection<DomainSpec> getAllCachedDomains() {
        return getAllCacheDomainSpec();
    }

    /**
     * Do create and cache domain domain spec.
     *
     * @param domain the domain
     * @return the domain spec
     */
    public DomainSpec doCreateAndCacheDomain(Domain domain) {
        DomainSpec domainSpec = getDomainSpecEntry(domain.code());
        if (null != domainSpec) {
            return domainSpec;
        }
        return doCacheObjectDomainSpec(domain.code(), DomainSpec.of(domain.parentCode(), domain.code(),
            domain.name(), domain.desc()));
    }

    /**
     * Is domain registered boolean.
     *
     * @param domainCode the domain code
     * @return the boolean
     */
    public boolean isDomainRegistered(String domainCode) {
        return isEntitiesCachedDomainSpec(domainCode);
    }

    /**
     * Do cache ability spec ability spec.
     *
     * @param ability        the ability
     * @param targetClass    the target class
     * @param parentBaseSpec the parent base spec
     * @return the ability spec
     */
    public AbilitySpec doCacheAbilitySpec(Ability ability, Class<?> targetClass, BaseSpec parentBaseSpec) {
        String abilityCode = StringUtils.isEmpty(ability.code()) ? targetClass.getName() : ability.code();
        AbilitySpec abilitySpec = getAbilitySpecEntry(abilityCode);
        if (null == abilitySpec) {
            abilitySpec = AbilitySpec.of(abilityCode, ability.name(), ability.desc());
            abilitySpec.setAbilityClass(targetClass);
            abilitySpec.setParentCode(parentBaseSpec.getCode());
            return doCacheObjectAbilitySpec(abilityCode, abilitySpec);
        }
        return abilitySpec;
    }

    /**
     * Gets cached ability spec.
     *
     * @param abilityCode the ability code
     * @return the cached ability spec
     */
    public AbilitySpec getCachedAbilitySpec(String abilityCode) {
        return getAbilitySpecEntry(abilityCode);
    }

    /**
     * Gets all cached abilities.
     *
     * @return the all cached abilities
     */
    public Collection<AbilitySpec> getAllCachedAbilities() {
        return getAllCacheAbilitySpec();
    }

    /**
     * Gets cached extension point spec.
     *
     * @param extensionCode the extension code
     * @return the cached extension point spec
     */
    public ExtensionPointSpec getCachedExtensionPointSpec(String extensionCode) {
        ExtensionPointSpec extensionPointSpec = getExtensionPointSpecEntry(extensionCode);
        if (null != extensionPointSpec) {
            if (extensionPointSpec instanceof NotExistedExtensionPointSpec) {
                return null;
            }
            return extensionPointSpec;
        }

        for (DomainSpec domainSpec : getAllCachedDomains()) {
            extensionPointSpec = domainSpec.findExtensionPointDesc(extensionCode);
            if (null != extensionPointSpec) {
                break;
            }
        }
        if (null != extensionPointSpec) {
            return doCacheObjectExtensionPointSpec(extensionCode, extensionPointSpec);
        }

        doCacheObjectExtensionPointSpec(extensionCode, new NotExistedExtensionPointSpec(null));
        return null;
    }

    /**
     * 清空cache里面的数据
     */
    public void clearAllCache() {
        doClearAllCache();
    }

    /**
     * Do cache product spec product spec.
     *
     * @param product the product
     * @return the product spec
     */
    public ProductSpec doCacheProductSpec(Product product) {
        return doCacheObjectProductSpec(product.code(), ProductSpec.of(product.code(),
            product.name(), product.desc()));
    }

    /**
     * Do cache template spec.
     *
     * @param templateSpecs the template specs
     */
    public void doCacheTemplateSpec(List<TemplateSpec> templateSpecs) {
        if (CollectionUtils.isNotEmpty(templateSpecs)) {
            for (TemplateSpec templateSpec : templateSpecs) {
                doCacheObjectTemplateSpec(templateSpec.getCode(), templateSpec);
            }
        }
    }

    /**
     * Gets cached template spec.
     *
     * @param templatSpecCode the templat spec code
     * @return the cached template spec
     */
    public TemplateSpec getCachedTemplateSpec(String templatSpecCode) {
        return getTemplateSpecEntry(templatSpecCode);
    }

    /**
     * Gets all cached template spec.
     *
     * @return the all cached template spec
     */
    public Collection<TemplateSpec> getAllCachedTemplateSpec() {
        return getAllCacheTemplateSpec();
    }

    /**
     * Is product cached boolean.
     *
     * @param productCode the product code
     * @return the boolean
     */
    public boolean isProductCached(String productCode) {
        return isEntitiesCachedProductSpec(productCode);
    }


    /**
     * Gets all cached products.
     *
     * @return the all cached products
     */
    public Collection<ProductSpec> getAllCachedProducts() {
        return getAllCacheProductSpec();
    }
}

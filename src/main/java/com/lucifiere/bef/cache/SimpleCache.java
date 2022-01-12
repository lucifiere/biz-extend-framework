package com.lucifiere.bef.cache;

import com.lucifiere.bef.model.*;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 框架缓存
 *
 * @author 沾清
 */
public class SimpleCache {

    /**
     * The Cache domain spec.
     */
    private final ConcurrentMap<String, DomainSpec> CACHE_DOMAIN_SPEC = new ConcurrentHashMap<>(120);

    /**
     * The Cache ability spec.
     */
    private final ConcurrentMap<String, AbilitySpec> CACHE_ABILITY_SPEC = new ConcurrentHashMap<>(120);

    /**
     * The Cache extension point spec.
     */
    private final ConcurrentMap<String, ExtensionPointSpec> CACHE_EXTENSION_POINT_SPEC = new ConcurrentHashMap<>(120);

    /**
     * The Cache product spec.
     */
    private final ConcurrentMap<String, ProductSpec> CACHE_PRODUCT_SPEC = new ConcurrentHashMap<>(120);

    /**
     * The Cache template spec.
     */
    private final ConcurrentMap<String, TemplateSpec> CACHE_TEMPLATE_SPEC = new ConcurrentHashMap<>(120);

    /**
     * Do cache object domain spec domain spec.
     *
     * @param key the key
     * @param obj the obj
     * @return the domain spec
     */
    protected DomainSpec doCacheObjectDomainSpec(String key, DomainSpec obj) {
        CACHE_DOMAIN_SPEC.put(key, obj);
        return obj;
    }

    /**
     * Do cache object ability spec ability spec.
     *
     * @param key the key
     * @param obj the obj
     * @return the ability spec
     */
    protected AbilitySpec doCacheObjectAbilitySpec(String key, AbilitySpec obj) {
        CACHE_ABILITY_SPEC.put(key, obj);
        return obj;
    }

    /**
     * Do cache object extension point spec extension point spec.
     *
     * @param key the key
     * @param obj the obj
     * @return the extension point spec
     */
    protected ExtensionPointSpec doCacheObjectExtensionPointSpec(String key, ExtensionPointSpec obj) {
        CACHE_EXTENSION_POINT_SPEC.put(key, obj);
        return obj;
    }

    /**
     * Gets domain spec entry.
     *
     * @param key the key
     * @return the domain spec entry
     */
    protected DomainSpec getDomainSpecEntry(String key) {
        if (key == null) {
            return null;
        }
        return CACHE_DOMAIN_SPEC.get(key);
    }

    /**
     * Gets ability spec entry.
     *
     * @param key the key
     * @return the ability spec entry
     */
    protected AbilitySpec getAbilitySpecEntry(String key) {
        if (key == null) {
            return null;
        }
        return CACHE_ABILITY_SPEC.get(key);
    }

    /**
     * Gets extension point spec entry.
     *
     * @param key the key
     * @return the extension point spec entry
     */
    protected ExtensionPointSpec getExtensionPointSpecEntry(String key) {
        if (key == null) {
            return null;
        }
        return CACHE_EXTENSION_POINT_SPEC.get(key);
    }

    /**
     * Is entities cached domain spec boolean.
     *
     * @param key the key
     * @return the boolean
     */
    protected boolean isEntitiesCachedDomainSpec(String key) {
        return CACHE_DOMAIN_SPEC.containsKey(key);
    }

    /**
     * Gets all cache domain spec.
     *
     * @return the all cache domain spec
     */
    protected Collection<DomainSpec> getAllCacheDomainSpec() {
        return CACHE_DOMAIN_SPEC.values();
    }

    /**
     * Gets cache domain spec.
     *
     * @param code the code
     * @return the cache domain spec
     */
    public DomainSpec getCacheDomainSpec(String code) {
        return CACHE_DOMAIN_SPEC.get(code);
    }


    /**
     * Gets all cache ability spec.
     *
     * @return the all cache ability spec
     */
    protected Collection<AbilitySpec> getAllCacheAbilitySpec() {
        return CACHE_ABILITY_SPEC.values();
    }

    /**
     * Gets all cache extension point spec.
     *
     * @return the all cache extension point spec
     */
    protected Collection<ExtensionPointSpec> getAllCacheExtensionPointSpec() {
        return CACHE_EXTENSION_POINT_SPEC.values();
    }

    /**
     * Do remove object domain spec.
     *
     * @param key the key
     */
    protected void doRemoveObjectDomainSpec(String key) {
        CACHE_DOMAIN_SPEC.remove(key);
    }

    /**
     * Do remove object ability spec.
     *
     * @param key the key
     */
    protected void doRemoveObjectAbilitySpec(String key) {
        CACHE_ABILITY_SPEC.remove(key);
    }

    /**
     * Do remove object extension point spec.
     *
     * @param key the key
     */
    protected void doRemoveObjectExtensionPointSpec(String key) {
        CACHE_EXTENSION_POINT_SPEC.remove(key);
    }

    /**
     * Do clear all cache.
     */
    protected void doClearAllCache() {
        CACHE_DOMAIN_SPEC.clear();
        CACHE_ABILITY_SPEC.clear();
        CACHE_EXTENSION_POINT_SPEC.clear();
        CACHE_TEMPLATE_SPEC.clear();
    }

    /**
     * Is product registered boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean isProductRegistered(String key) {
        return CACHE_PRODUCT_SPEC.containsKey(key);
    }

    /**
     * Do cache object product spec product spec.
     *
     * @param key the key
     * @param obj the obj
     * @return the product spec
     */
    protected ProductSpec doCacheObjectProductSpec(String key, ProductSpec obj) {
        CACHE_PRODUCT_SPEC.put(key, obj);
        return obj;
    }

    /**
     * Do cache object template spec template spec.
     *
     * @param key the key
     * @param obj the obj
     * @return the template spec
     */
    protected TemplateSpec doCacheObjectTemplateSpec(String key, TemplateSpec obj) {
        CACHE_TEMPLATE_SPEC.put(key, obj);
        return obj;
    }

    /**
     * Gets template spec entry.
     *
     * @param key the key
     * @return the template spec entry
     */
    protected TemplateSpec getTemplateSpecEntry(String key) {
        if (key == null) {
            return null;
        }
        return CACHE_TEMPLATE_SPEC.get(key);
    }

    /**
     * Gets all cache template spec.
     *
     * @return the all cache template spec
     */
    protected Collection<TemplateSpec> getAllCacheTemplateSpec() {
        return CACHE_TEMPLATE_SPEC.values();
    }

    /**
     * Is entities cached product spec boolean.
     *
     * @param key the key
     * @return the boolean
     */
    protected boolean isEntitiesCachedProductSpec(String key) {
        if (key == null) {
            return false;
        }
        return CACHE_PRODUCT_SPEC.containsKey(key);
    }

    /**
     * Gets all cache product spec.
     *
     * @return the all cache product spec
     */
    protected Collection<ProductSpec> getAllCacheProductSpec() {
        return CACHE_PRODUCT_SPEC.values();
    }
}

package com.lucifiere.bef;

import com.google.common.collect.Maps;
import com.lucifiere.bef.annotation.Product;
import com.lucifiere.bef.cache.FunctionCache;
import com.lucifiere.bef.model.AbilitySpec;
import com.lucifiere.bef.model.DomainSpec;
import com.lucifiere.bef.model.ExtensionPointSpec;
import com.lucifiere.bef.model.ProductSpec;
import com.lucifiere.bef.register.AbilityRegisterReq;
import com.lucifiere.bef.register.ClassPathRequest;
import com.lucifiere.bef.register.DomainAbilityRegister;
import com.lucifiere.bef.register.DomainRegister;
import com.lucifiere.bef.specific.IDomainAbilityProvider;
import com.lucifiere.bef.specific.impl.BaseDomainAbilityProvider;
import com.lucifiere.bef.template.AbstractTemplate;
import com.lucifiere.bef.template.TemplateManager;
import com.lucifiere.bef.template.TemplateRealization;
import com.lucifiere.bef.util.ClassPathScanHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 功能管理器
 *
 * @author 沾清
 */
public class FunctionManager {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionManager.class);

    /**
     * instance.
     */
    private static volatile FunctionManager instance = null;

    /**
     * concurrency control of business config data read and write.
     */
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * a bizCode maybe has different version of business config.
     */
    private Map<String, ArrayDeque<String>> bizConfigVersionMap = Maps.newHashMap();

    /**
     * The domain ability's provider
     */
    private IDomainAbilityProvider domainAbilityProvider = new BaseDomainAbilityProvider<>();

    /**
     * effect bundleService front end.
     */
    private boolean frontEffectedBundledServices;

    /**
     * The Function cache.
     */
    private FunctionCache functionCache = new FunctionCache();

    /**
     * private.
     */
    private FunctionManager() {
    }

    /**
     * Gets instance.
     *
     * @return get the instance.
     */
    public static FunctionManager getInstance() {
        if (instance == null) {
            synchronized (FunctionManager.class) {
                if (instance == null) {
                    instance = new FunctionManager();
                }
            }
        }
        return instance;
    }

    /**
     * Gets ability spec by code.
     *
     * @param abilityCode the ability code
     * @return the ability spec by code
     */
    public AbilitySpec getAbilitySpecByCode(String abilityCode) {
        if (StringUtils.isEmpty(abilityCode)) {
            return null;
        }
        return getFunctionCache().getCachedAbilitySpec(abilityCode);
    }

    /**
     * Gets domain ability provider.
     *
     * @return the domain ability provider
     */
    public IDomainAbilityProvider getDomainAbilityProvider() {
        return domainAbilityProvider;
    }

    /**
     * 根据指定的Class Package路径，扫描并添加 Domain
     *
     * @param classPackages classPackages
     * @return the list
     */
    public List<DomainSpec> registerDomains(String... classPackages) {
        return DomainRegister.getInstance().register(new ClassPathRequest(classPackages));
    }

    /**
     * Register products list.
     *
     * @param classPackages the class packages
     * @return the list
     */
    public List<ProductSpec> registerProducts(String... classPackages) {
        List<ProductSpec> productSpecs = new ArrayList<>();
        if (null == classPackages) {
            return productSpecs;
        }
        for (String classPackage : classPackages) {
            productSpecs.addAll(registerProducts(classPackage));
        }
        return productSpecs;
    }

    /**
     * Register products list.
     *
     * @param classPackage the class package
     * @return the list
     */
    private List<ProductSpec> registerProducts(String classPackage) {
        if (StringUtils.isEmpty(classPackage)) {
            return new ArrayList<>();
        }
        List<ProductSpec> productSpecs = new ArrayList<>();
        ClassPathScanHandler handler = new ClassPathScanHandler();
        Set<Class<?>> classSet = handler.getPackageAllClasses(classPackage, true);
        for (Class<?> targetClass : classSet) {
            Product product = targetClass.getDeclaredAnnotation(Product.class);
            if (null == product) {
                continue;
            }
            productSpecs.addAll(buildProductSpecs(product, targetClass, classPackage, classSet));
        }
        return productSpecs;
    }

    /**
     * Build product specs list.
     *
     * @param product       the product
     * @param targetClass   the target class
     * @param classPackages the class packages
     * @param classSet      the class set
     * @return the list
     */
    private List<ProductSpec> buildProductSpecs(Product product, Class<?> targetClass,
                                                String classPackages, Set<Class<?>> classSet) {
        List<ProductSpec> productSpecs = new ArrayList<>();
        boolean isDuplicated = getFunctionCache().isProductCached(product.code());

        if (!isDuplicated) {
            ProductSpec productSpec = getFunctionCache().doCacheProductSpec(product);

            productSpecs.add(productSpec);

            AbstractTemplate template = AbstractTemplate.createFromClass(productSpec, targetClass);

            TemplateManager.getInstance().registerTemplate(template);
            List<TemplateRealization> realizations =
                TemplateManager.getInstance().registerExtensionRealization(template, classPackages);
            TemplateManager.getInstance().registerbefTemplate(template);

            if (CollectionUtils.isNotEmpty(realizations)) {
                productSpec.getRealizations().addAll(realizations);
            }

            //把该功能域下的功能能力点都注册进来
            productSpec.getProductAbilities().addAll(
                DomainAbilityRegister.getInstance().register(new AbilityRegisterReq(productSpec, classSet)));
        }
        return productSpecs;
    }

    /**
     * Gets functionCache
     *
     * @return the functionCache
     */
    public FunctionCache getFunctionCache() {
        return functionCache;
    }

    /**
     * get the extension point specification by code.
     *
     * @param extensionCode the extension point code.
     * @return the fouind extension point
     */
    public ExtensionPointSpec getExtensionPointSpecByCode(String extensionCode) {
        return getFunctionCache().getCachedExtensionPointSpec(extensionCode);
    }
}



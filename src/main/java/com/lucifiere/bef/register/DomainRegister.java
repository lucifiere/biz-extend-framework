package com.lucifiere.bef.register;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lucifiere.bef.annotation.Domain;
import com.lucifiere.bef.annotation.DomainService;
import com.lucifiere.bef.model.AbilityInstanceSpec;
import com.lucifiere.bef.model.AbilitySpec;
import com.lucifiere.bef.model.DomainServiceSpec;
import com.lucifiere.bef.model.DomainSpec;
import com.lucifiere.bef.specific.IDomainNs;
import com.lucifiere.bef.specific.IDomainService;
import com.lucifiere.bef.util.ClassNameComparator;
import com.lucifiere.bef.util.ClassPathScanHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Domain register.
 *
 * @author 沾清
 */
public class DomainRegister extends AbstractFunctionRegister<ClassPathRequest, List<DomainSpec>> {

    /**
     * The constant instance.
     */
    private static DomainRegister instance;

    /**
     * Instantiates a new Domain register.
     */
    private DomainRegister() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DomainRegister getInstance() {
        if (null == instance) {
            instance = new DomainRegister();
        }
        return instance;
    }

    /**
     * register domain by the class package name
     *
     * @param request the request
     * @return the list
     */
    @Override
    public synchronized List<DomainSpec> register(ClassPathRequest request) {
        List<DomainSpec> domainSpecs = new ArrayList<>();
        if (null == request || null == request.getClassPackages()) {
            return domainSpecs;
        }
        ClassPathScanHandler handler = new ClassPathScanHandler();
        TreeSet<Class<?>> allClassSet = new TreeSet<>(new ClassNameComparator());
        for (String classPackage : request.getClassPackages()) {
            allClassSet.addAll(handler.getPackageAllClasses(classPackage, true));
        }
        return this.register(allClassSet);
    }

    /**
     * register domain by the classes set
     *
     * @param classSet the class set
     * @return the list
     */
    public synchronized List<DomainSpec> register(Set<Class<?>> classSet) {
        List<DomainSpec> domainSpecs = new ArrayList<>();
        TreeSet<Class<?>> domainClassSet = getClassesWithDomainAnnotation(classSet);
        if (CollectionUtils.isEmpty(domainClassSet)) {
            return Lists.newArrayList();
        }

        Map<Boolean, List<Class<?>>> domainClassGroup = domainClassSet.stream().collect(
            Collectors.groupingBy(p -> ClassUtils.isAssignable(p, IDomainNs.class)));

        registerDomainsWithNs(domainSpecs, domainClassGroup.get(true));
        return domainSpecs;
    }

    /**
     * Register domains with ns.
     *
     * @param domainSpecs the domain specs
     * @param classSet    the class set
     */
    private synchronized void registerDomainsWithNs(List<DomainSpec> domainSpecs, List<Class<?>> classSet) {
        if (CollectionUtils.isEmpty(classSet)) {
            return;
        }

        for (Class<?> targetClass : classSet) {
            Domain domain = targetClass.getAnnotation(Domain.class);
            boolean isDuplicated = getFunctionCache().isDomainRegistered(getDomainKey(domain));
            DomainSpec domainSpec = getFunctionCache().doCreateAndCacheDomain(domain);
            if (!isDuplicated) {
                domainSpecs.add(domainSpec);
            }
            ClassPathScanHandler handler = new ClassPathScanHandler();
            Set<Class<?>> domainClassSet = handler.getPackageAllClasses(targetClass.getPackage().getName(), true);
            List<Class<?>> domainServiceClasses = listDomainServiceClassesUnderNs(domainClassSet);

            domainServiceClasses.forEach(p -> registerDomainService(domainSpec, p));

            //todo 注册域能力
            mergeRegisteredDomainAbilities(domainSpec.getDomainAbilities(),
                DomainAbilityRegister.getInstance().register(new AbilityRegisterReq(domainSpec, domainClassSet)));

        }
    }

    /**
     * 把该功能域下的功能能力点都注册进来
     *
     * @param registeredAbilities the registered abilities
     * @param newRegistered       the new registered
     */
    private void mergeRegisteredDomainAbilities(Set<AbilitySpec> registeredAbilities, List<AbilitySpec> newRegistered) {
        for (AbilitySpec abilitySpec : newRegistered) {
            Optional<AbilitySpec> optional = registeredAbilities.stream()
                .filter(p -> StringUtils.equals(p.getCode(), abilitySpec.getCode()))
                .findFirst();
            if (!optional.isPresent()) {
                registeredAbilities.add(abilitySpec);
                continue;
            }
            AbilitySpec registeredAbilitySpec = optional.get();
            for (AbilityInstanceSpec abilityInstanceSpec : abilitySpec.getAbilityInstances()) {
                AbilityInstanceSpec registeredInstanceSpec = registeredAbilitySpec.getDomainAbilityInstanceSpec(
                    abilityInstanceSpec.getCode());
                if (null != registeredInstanceSpec) {
                    continue;
                }
                registeredAbilitySpec.addAbilityInstance(abilityInstanceSpec);
            }
        }
    }

    /**
     * List domain service classes under ns list.
     *
     * @param domainClassSet the domain class set
     * @return the list
     */
    private List<Class<?>> listDomainServiceClassesUnderNs(Set<Class<?>> domainClassSet) {
        if (null == domainClassSet) {
            return Lists.newArrayList();
        }
        return domainClassSet.stream()
            .filter(p -> ClassUtils.isAssignable(p, IDomainService.class))
            .collect(Collectors.toList());

    }

    /**
     * Register domain service.
     *
     * @param domainSpec         the domain spec
     * @param domainServiceClass the domain service class
     */
    private void registerDomainService(DomainSpec domainSpec, Class<?> domainServiceClass) {

        // 把该功能域下的功能域服务注册进来
        List<DomainServiceSpec> allFromScan = scanDomainServices(domainServiceClass);
        if (CollectionUtils.isEmpty(domainSpec.getDomainServices())) {
            domainSpec.getDomainServices().addAll(allFromScan);
        } else {
            // 对已经add过的service不要新增在domainSpec中
            appendDomainService(domainSpec.getDomainServices(), allFromScan);
        }
    }

    /**
     * Gets classes with domain annotation.
     *
     * @param classSet the class set
     * @return the classes with domain annotation
     */
    private TreeSet<Class<?>> getClassesWithDomainAnnotation(Set<Class<?>> classSet) {
        TreeSet<Class<?>> treeClassSet = new TreeSet<>(new ClassNameComparator());
        treeClassSet.addAll(classSet.stream().filter(p -> null != p.getAnnotation(Domain.class))
            .collect(Collectors.toList()));
        return treeClassSet;
    }

    /**
     * Append domain service.
     *
     * @param exist  the exist
     * @param append the append
     */
    private void appendDomainService(
        List<DomainServiceSpec> exist, List<DomainServiceSpec> append) {
        Set<String> existServiceCode = Sets.newHashSet();
        existServiceCode.addAll(
            exist.stream().map(
                DomainServiceSpec::getServiceCode).collect(Collectors.toList())
        );
        exist.addAll(append.stream().filter(
            appendSpec -> !existServiceCode.contains(appendSpec.getServiceCode())
        ).collect(Collectors.toList()));
    }

    /**
     * get the function domain's key for map.
     *
     * @param domain the function domain.
     * @return the key.
     */
    private String getDomainKey(Domain domain) {
        return domain.code();
    }

    /**
     * scan the domain services.
     *
     * @param targetClass the target class which is the Domain Class.
     * @return the list of the domain services.
     */
    private List<DomainServiceSpec> scanDomainServices(Class<?> targetClass) {
        List<DomainServiceSpec> domainServiceSpecs = new ArrayList<>();

        Method[] methods = targetClass.getMethods();
        for (Method m : methods) {
            DomainService domainService = AnnotationUtils.findAnnotation(m, DomainService.class);
            if (null == domainService) {
                continue;
            }
            DomainServiceSpec domainServiceSpec = DomainServiceSpec.of(domainService.code(), domainService.name());
            if (StringUtils.isNotEmpty(domainService.desc())) {
                domainServiceSpec.setDescription(domainService.desc());
            }
            domainServiceSpecs.add(domainServiceSpec);
        }
        return domainServiceSpecs;
    }
}

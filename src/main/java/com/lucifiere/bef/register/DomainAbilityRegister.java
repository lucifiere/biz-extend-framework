package com.lucifiere.bef.register;

import com.google.common.collect.Sets;
import com.lucifiere.bef.annotation.Ability;
import com.lucifiere.bef.annotation.AbilityExtension;
import com.lucifiere.bef.annotation.BusinessExtension;
import com.lucifiere.bef.annotation.Enrich;
import com.lucifiere.bef.exception.BefErrorCode;
import com.lucifiere.bef.exception.BefErrorMessage;
import com.lucifiere.bef.exception.BefSystemException;
import com.lucifiere.bef.model.AbilityInstanceSpec;
import com.lucifiere.bef.model.AbilitySpec;
import com.lucifiere.bef.model.BaseSpec;
import com.lucifiere.bef.model.ExtensionPointSpec;
import com.lucifiere.bef.specific.IAbility;
import com.lucifiere.bef.specific.IExtensionPoints;
import com.lucifiere.bef.specific.impl.ExtensionPointType;
import com.lucifiere.bef.util.BefAnnotationUtils;
import com.lucifiere.bef.util.BefBeanUtils;
import com.lucifiere.bef.util.BefClassUtils;
import com.lucifiere.bef.util.ClassPathScanHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Domain ability register.
 *
 * @author 沾清
 */
public class DomainAbilityRegister extends AbstractFunctionRegister<AbilityRegisterReq, List<AbilitySpec>> {

    /**
     * The constant instance.
     */
    private static DomainAbilityRegister instance;

    /**
     * Instantiates a new Domain ability register.
     */
    private DomainAbilityRegister() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DomainAbilityRegister getInstance() {
        if (null == instance) {
            instance = new DomainAbilityRegister();
        }
        return instance;
    }

    /**
     * scan the domain ability from the classes set.
     *
     * @param registerReq the basic ability's register request.
     * @return the domain abilities list.
     */
    @Override
    public synchronized List<AbilitySpec> register(AbilityRegisterReq registerReq) {
        BaseSpec parentBaseSpec = registerReq.getParentBaseSpec();
        Collection<Class<?>> classSet = registerReq.getClassSet();

        List<AbilitySpec> abilitySpecList = new ArrayList<>();
        for (Class<?> targetClass : classSet) {
            Ability ability = targetClass.getDeclaredAnnotation(Ability.class);
            if (null == ability) {
                continue;
            }
            if (!ability.parent().equals(parentBaseSpec.getCode())) {
                continue;
            }
            AbilitySpec abilitySpec = getFunctionCache().doCacheAbilitySpec(ability, targetClass, parentBaseSpec);
            abilitySpecList.add(abilitySpec);

            // 根据当前能力点的定义，查找对应的实现定义
            abilitySpec.addAbilityInstance(scanAbilityInstance(abilitySpec, classSet));

        }
        return abilitySpecList;
    }

    /**
     * 扫描能力的实现定义.
     *
     * @param abilitySpec the ability spec
     * @param classSet    the classes set of the specific class package.
     * @return the domain ability's instance list.
     */
    public synchronized List<AbilityInstanceSpec> scanAbilityInstance(AbilitySpec abilitySpec,
                                                                      Collection<Class<?>> classSet) {

        List<AbilityInstanceSpec> abilityInstanceSpecList = new ArrayList<>(
            registerAbilityInstances(abilitySpec, classSet));
        abilityInstanceSpecList.sort(Comparator.comparingInt(AbilityInstanceSpec::getPriority));
        return abilityInstanceSpecList;
    }

    /**
     * scan the ability's extension points of the specific ability.
     *
     * @param abilitySpec the ability.
     * @param targetClass the target class which maybe the extension point's interface.
     * @return the set of the extension points spec.
     */
    private Set<ExtensionPointSpec> scanAbilityExtensions(AbilitySpec abilitySpec, Class targetClass) {
        Set<ExtensionPointSpec> extensionPointSpecList = new HashSet<>();

        //扫描能力基类中定义的可扩展点
        Class[] interfaces = targetClass.getInterfaces();
        for (Class itfClass : interfaces) {
            extensionPointSpecList.addAll(scanAbilityExtensions(Sets.newHashSet(), itfClass, abilitySpec));
        }

        //有些能力的可扩展点不是定义在能力基类上的，而是能力的实现类上做定义的
        List<IAbility> domainAbilities = getDomainAbilityProvider().getRealizations(abilitySpec.getCode());
        for (IAbility domainAbility : domainAbilities) {
            extensionPointSpecList.addAll(scanAbilityExtensions(domainAbility, abilitySpec));
        }
        return extensionPointSpecList;
    }

    /**
     * 注册Ability Instance.
     *
     * @param packagePath the class path.
     * @return the list
     */
    public synchronized List<AbilityInstanceSpec> registerAbilityInstances(String packagePath) {
        List<AbilityInstanceSpec> instanceSpecs = new ArrayList<>();
        ClassPathScanHandler classPathScanHandler = new ClassPathScanHandler(packagePath);
        Set<Class<?>> classSet = classPathScanHandler.getPackageAllClasses(packagePath, true);
        return registerAbilityInstances(classSet);
    }

    /**
     * Register ability instances list.
     *
     * @param classSet the class set
     * @return the list
     */
    private List<AbilityInstanceSpec> registerAbilityInstances(Set<Class<?>> classSet) {
        List<AbilityInstanceSpec> instanceSpecs = new ArrayList<>();

        Collection<AbilitySpec> abilitySpecs = getAllRegisteredAbilities();
        for (AbilitySpec abilitySpec : abilitySpecs) {
            instanceSpecs.addAll(registerAbilityInstances(abilitySpec, classSet));
        }
        return instanceSpecs;
    }

    /**
     * Gets all registered abilities.
     *
     * @return the all registered abilities
     */
    public Collection<AbilitySpec> getAllRegisteredAbilities() {
        return getFunctionCache().getAllCachedAbilities();
    }

    /**
     * Register ability instances list.
     *
     * @param abilitySpec the ability spec
     * @param classSet    the class set
     * @return the list
     */
    private List<AbilityInstanceSpec> registerAbilityInstances(AbilitySpec abilitySpec, Collection<Class<?>> classSet) {
        List<AbilityInstanceSpec> instanceSpecs = new ArrayList<>();
        for (Class<?> targetClass : classSet) {
            if (Modifier.isAbstract(targetClass.getModifiers())
                || Modifier.isInterface(targetClass.getModifiers())) {
                continue;
            }
            if (!BefClassUtils.isSubClassOf(targetClass, abilitySpec.getAbilityClass())) {
                continue;//不能是自己，同时是ability的子类
            }
            AbilityInstanceBuildResult result = innerRegisterAbilityInstance(abilitySpec, targetClass);
            if (!result.isSuccess() && !result.isRegistered()) {
                BefErrorMessage errorMessage = null == result.getErrorMessage() ? BefErrorCode.toErrorMessage(
                    "LARK-THEMIS-BEF-1-002",
                    targetClass.getName() + "not clear")
                    : result.getErrorMessage();
                throw new BefSystemException(errorMessage);
            }
            AbilityInstanceSpec abilityInstanceSpec = result.getInstanceSpec();
            if (null != abilityInstanceSpec) { instanceSpecs.add(abilityInstanceSpec); }
        }

        return instanceSpecs;
    }

    /**
     * Inner register ability instance ability instance build result.
     *
     * @param abilitySpec   the ability spec
     * @param instanceClass the instance class
     * @return the ability instance build result
     */
    private AbilityInstanceBuildResult innerRegisterAbilityInstance(AbilitySpec abilitySpec, Class<?> instanceClass) {
        IAbility domainAbility;
        Object beanViaClass = BefBeanUtils.getBeanViaClass(instanceClass);
        if (beanViaClass instanceof IAbility) {
            domainAbility = (IAbility)beanViaClass;
        } else {
            LOGGER.warn(instanceClass.getName() + " is not IAbility Type");
            return AbilityInstanceBuildResult.failed(
                BefErrorCode.toErrorMessage("LARK-THEMIS-BEF-1-003", instanceClass.getName()));
        }
        if (null != getDomainAbilityProvider().getRealization(domainAbility.getInstanceCode())) {
            //已经注册了，就不重复注册
            if (isAbilityInstanceRegistered(abilitySpec, domainAbility)) {
                return AbilityInstanceBuildResult.registered();
            }
            return buildAbilityInstanceSpec(abilitySpec, domainAbility, instanceClass);
        }
        AbilityInstanceBuildResult result = buildAbilityInstanceSpec(abilitySpec, domainAbility, instanceClass);
        if (result.isSuccess()) {
            AbilityInstanceSpec abilityInstanceSpec = result.getInstanceSpec();
            if (null != abilityInstanceSpec) {
                getDomainAbilityProvider().registerRealization(domainAbility);
            }
        }
        return result;
    }

    /**
     * Is ability instance registered boolean.
     *
     * @param abilitySpec the ability spec
     * @param instance    the instance
     * @return the boolean
     */
    private boolean isAbilityInstanceRegistered(AbilitySpec abilitySpec, IAbility instance) {
        return abilitySpec.getAbilityInstances().stream()
            .anyMatch(p -> p.getCode().equals(instance.getInstanceCode()));
    }

    /**
     * Build ability instance spec ability instance build result.
     *
     * @param abilitySpec   the ability spec
     * @param instance      the instance
     * @param instanceClass the instance class
     * @return the ability instance build result
     */
    private AbilityInstanceBuildResult buildAbilityInstanceSpec(AbilitySpec abilitySpec, IAbility instance,
                                                                Class<?> instanceClass) {
        try {
            AbilityInstanceSpec instanceDesc = new AbilityInstanceSpec();
            instanceDesc.setInstanceClass(instanceClass.getName());
            instanceDesc.setCode(instance.getInstanceCode());
            instanceDesc.setName(instanceClass.getSimpleName());
            instance.setAbilityCode(abilitySpec.getCode());
            Set<ExtensionPointSpec> extensionPointSpecs = scanAbilityExtensions(instance, abilitySpec);

            Map<ExtensionPointType, List<ExtensionPointSpec>> groupBy =
                extensionPointSpecs.stream()
                    .collect(Collectors.groupingBy(ExtensionPointSpec::getType));

            List<ExtensionPointSpec> sysExtPoints = groupBy.get(ExtensionPointType.SYSTEM);
            if (CollectionUtils.isNotEmpty(sysExtPoints)) {
                instanceDesc.getSystemExtPoints().addAll(sysExtPoints);
            }

            List<ExtensionPointSpec> bizExtPoints = groupBy.get(ExtensionPointType.BUSINESS);
            if (CollectionUtils.isNotEmpty(bizExtPoints)) {
                instanceDesc.getBusinessExtPoints().addAll(bizExtPoints);
            }
            //构建全量ExtPoints缓存
            instanceDesc.buildAllExtPoints();

            Enrich enrichInfo = instanceClass.getDeclaredAnnotation(Enrich.class);
            if (null != enrichInfo) {
                if (StringUtils.isNotEmpty(enrichInfo.name())) {
                    instanceDesc.setName(enrichInfo.name());
                }
                instanceDesc.setPriority(enrichInfo.priority());
            }

            abilitySpec.addAbilityInstance(instanceDesc);

            return AbilityInstanceBuildResult.success(instanceDesc);
        } catch (Exception e) {
            return AbilityInstanceBuildResult.failed(
                BefErrorCode.toErrorMessage("LARK-THEMIS-BEF-1-002", instanceClass + e.getMessage()));
        }
    }

    /**
     * scan the extension points from the ability's instance.
     *
     * @param domainAbility the ability instance.
     * @param abilitySpec   the ability specification.
     * @return the set of the extension points spec.
     */
    private Set<ExtensionPointSpec> scanAbilityExtensions(IAbility domainAbility, AbilitySpec abilitySpec) {
        Set<ExtensionPointSpec> extensionPointSpecList = new HashSet<>();

        Class<?> returnType = findAbilityExtensionDefinition(domainAbility.getClass());
        if (null != returnType) {
            //是可扩展点的接口
            extensionPointSpecList.addAll(scanAbilityExtensions(Sets.newHashSet(), returnType, abilitySpec));
        }

        Method[] methods = domainAbility.getClass().getMethods();
        for (Method method : methods) {
            returnType = method.getReturnType();
            if (!ClassUtils.isAssignable(returnType, IExtensionPoints.class)) {
                continue;
            }

            //是可扩展点的接口
            extensionPointSpecList.addAll(
                scanAbilityExtensions(extensionPointSpecList.stream()
                        .map(BaseSpec::getCode).collect(Collectors.toSet()),
                    returnType, abilitySpec));
        }
        return extensionPointSpecList;
    }

    /**
     * Find ability extension definition class.
     *
     * @param abilityClass the ability class
     * @return the class
     */
    private Class<?> findAbilityExtensionDefinition(Class abilityClass) {
        Object genericSuperclass = abilityClass.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType)genericSuperclass;
            for (Type actualType : type.getActualTypeArguments()) {
                try {
                    Class<?> returnType = Class.forName(actualType.getTypeName());
                    if (ClassUtils.isAssignable(returnType, IExtensionPoints.class)) {
                        return returnType;
                    }
                } catch (ClassNotFoundException e) {
                    LOGGER.error(e.getMessage(), e);
                    return null;
                }
            }
        }
        if (ClassUtils.isAssignable(abilityClass.getSuperclass(), IAbility.class)) {
            return findAbilityExtensionDefinition(abilityClass.getSuperclass());
        }
        return null;
    }

    /**
     * scan the extension points from the interface class.
     *
     * @param existedSet  the existed set
     * @param itfClass    interface class.
     * @param abilitySpec the domain ability specification.
     * @return the set of the extension points spec.
     */
    private Set<ExtensionPointSpec> scanAbilityExtensions(Set<String> existedSet, Class<?> itfClass,
                                                          AbilitySpec abilitySpec) {
        Set<ExtensionPointSpec> extensionPointSpecList = new HashSet<>();
        Method[] methods = itfClass.getMethods();
        for (Method method : methods) {
            AbilityExtension extPoint = BefAnnotationUtils.findAnnotation(method, AbilityExtension.class);
            BusinessExtension bizExtPoint = BefAnnotationUtils.findAnnotation(method, BusinessExtension.class);

            if (null != bizExtPoint) {
                if (existedSet.contains(bizExtPoint.code())) { continue; }
                ExtensionPointSpec extensionPointSpec = buildExtensionPointSpec(abilitySpec, bizExtPoint, itfClass,
                    method);
                if (null != extensionPointSpec) {
                    extensionPointSpecList.add(extensionPointSpec);
                }
            }
            if (null != extPoint) {
                if (existedSet.contains(extPoint.code())) { continue; }
                ExtensionPointSpec extensionPointSpec = buildExtensionPointSpec(abilitySpec, extPoint, itfClass,
                    method);
                if (null != extensionPointSpec) {
                    extensionPointSpecList.add(extensionPointSpec);
                }
            }
        }
        return extensionPointSpecList;
    }
}

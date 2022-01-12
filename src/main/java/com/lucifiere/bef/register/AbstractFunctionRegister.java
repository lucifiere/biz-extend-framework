package com.lucifiere.bef.register;

import com.lucifiere.bef.FunctionManager;
import com.lucifiere.bef.annotation.AbilityExtension;
import com.lucifiere.bef.annotation.BusinessExtension;
import com.lucifiere.bef.cache.FunctionCache;
import com.lucifiere.bef.model.AbilitySpec;
import com.lucifiere.bef.model.ExtensionPointSpec;
import com.lucifiere.bef.specific.IDomainAbilityProvider;
import com.lucifiere.bef.specific.impl.ExtensionPointType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * The type Abstract function register.
 *
 * @param <RegisterReq>    the type parameter
 * @param <RegisterResult> the type parameter
 * @author 沾清
 */
public abstract class AbstractFunctionRegister<RegisterReq, RegisterResult> {

    /**
     * LOGGER.
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractFunctionRegister.class);

    /**
     * Gets function cache.
     *
     * @return the function cache
     */
    public FunctionCache getFunctionCache() {
        return FunctionManager.getInstance().getFunctionCache();
    }

    /**
     * Gets domain ability provider.
     *
     * @return the domain ability provider
     */
    protected IDomainAbilityProvider getDomainAbilityProvider() {
        return FunctionManager.getInstance().getDomainAbilityProvider();
    }

    /**
     * Register register result.
     *
     * @param registerReq the register req
     * @return the register result
     */
    public abstract RegisterResult register(RegisterReq registerReq);

    /**
     * Build extension point spec extension point spec.
     *
     * @param abilitySpec the ability spec
     * @param bizExtPoint the biz ext point
     * @param itfClass    the itf class
     * @param method      the method
     * @return the extension point spec
     */
    public ExtensionPointSpec buildExtensionPointSpec(AbilitySpec abilitySpec, AbilityExtension bizExtPoint,
                                                      Class<?> itfClass, Method method) {
        if (null == bizExtPoint) {
            return null;
        }

        return buildExtensionPointSpec(abilitySpec, bizExtPoint.code(), bizExtPoint.name(), bizExtPoint.desc(),
            ExtensionPointType.SYSTEM, itfClass, method);
    }

    /**
     * Build extension point spec extension point spec.
     *
     * @param abilitySpec the ability spec
     * @param bizExtPoint the biz ext point
     * @param itfClass    the itf class
     * @param method      the method
     * @return the extension point spec
     */
    public ExtensionPointSpec buildExtensionPointSpec(AbilitySpec abilitySpec, BusinessExtension bizExtPoint,
                                                      Class<?> itfClass, Method method) {
        if (null == bizExtPoint) {
            return null;
        }

        return buildExtensionPointSpec(abilitySpec, bizExtPoint.code(), bizExtPoint.name(), bizExtPoint.desc(),
            ExtensionPointType.BUSINESS, itfClass, method);
    }

    /**
     * Build extension point spec extension point spec.
     *
     * @param abilitySpec        the ability spec
     * @param extensionCode      the extension code
     * @param extensionName      the extension name
     * @param extensionDesc      the extension desc
     * @param extensionPointType the extension point type
     * @param itfClass           the itf class
     * @param method             the method
     * @return the extension point spec
     */
    private ExtensionPointSpec buildExtensionPointSpec(AbilitySpec abilitySpec, String extensionCode,
                                                       String extensionName,
                                                       String extensionDesc,
                                                       ExtensionPointType extensionPointType,
                                                       Class<?> itfClass, Method method) {
        ExtensionPointSpec extensionPointSpec =
            ExtensionPointSpec.of(method, abilitySpec.getParentCode(), abilitySpec.getCode(),
                extensionCode, extensionName, extensionDesc);
        extensionPointSpec.setType(extensionPointType);
        return extensionPointSpec;
    }

}

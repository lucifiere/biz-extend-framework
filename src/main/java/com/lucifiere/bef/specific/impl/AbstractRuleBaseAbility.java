package com.lucifiere.bef.specific.impl;

import com.lucifiere.bef.FunctionManager;
import com.lucifiere.bef.constant.BefConstants;
import com.lucifiere.bef.model.ExtensionPointSpec;
import com.lucifiere.bef.specific.*;
import com.lucifiere.bef.template.AbstractTemplate;
import com.lucifiere.bef.template.TemplateManager;
import com.lucifiere.bef.template.TemplateRealization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可基于规则进行配置的DomainAbility的抽象类，提供了一些公共的规则解析与值设置.
 *
 * @param <AbilityTarget>   the type parameter
 * @param <ExtensionPoints> the type parameter
 * @author 沾清
 */
public abstract class AbstractRuleBaseAbility<AbilityTarget, ExtensionPoints extends IExtensionPoints>
    implements IAbility<AbilityTarget, ExtensionPoints> {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRuleBaseAbility.class);

    /**
     * The Instance code.
     */
    private final String instanceCode;

    /**
     * the code of the ability.
     */
    private String abilityCode;

    /**
     * Gets ability code.
     *
     * @return the ability code
     */
    @Override
    public final String getAbilityCode() {
        return abilityCode;
    }

    /**
     * Sets ability code.
     *
     * @param abilityCode the ability code
     */
    @Override
    public final void setAbilityCode(String abilityCode) {
        // make sure that abilityCode won't change
        if (this.abilityCode != null) {
            return;
        }
        this.abilityCode = abilityCode;
    }

    /**
     * Instantiates a new Abstract rule base ability.
     */
    public AbstractRuleBaseAbility() {
        this.instanceCode = this.getClass().getName();
    }

    /**
     * Gets instance code.
     *
     * @return the ability instance code.
     */
    @Override
    public final String getInstanceCode() {
        return this.instanceCode;
    }

    /**
     * checking whether current ability support the specific target.
     *
     * @param bizCode business code.
     * @param target  the Target.
     * @return true or false.
     */
    @Override
    public boolean supportChecking(String bizCode, AbilityTarget target) {
        return true;
    }

    /**
     * whether support configure mode. if not support the Customization,
     * <p>
     *
     * @return true or false.
     */
    public boolean supportCustomization() {
        return true;
    }

    /**
     * Reduce execute with detail result execute result.
     *
     * @param <R>           the type parameter
     * @param bizInstance   the biz instance
     * @param extensionCode the extension code
     * @param callback      the callback
     * @return the execute result
     */
    public <R> ExecuteResult<R> reduceExecuteWithDetailResult(
        IBizInstance bizInstance, String extensionCode, ExtensionCallback<ExtensionPoints, R> callback) {
        R result = null;
        ExtensionPoints extensionPoints = loadExtensionRunners(bizInstance, extensionCode);
        if (extensionPoints != null) {
            result = callback.apply(extensionPoints);
        }
        return ExecuteResult.success(result);
    }

    /**
     * Reduce execute r.
     *
     * @param <R>           the type parameter
     * @param bizInstance   the biz instance
     * @param extensionCode the extension code
     * @param callback      the callback
     * @return the r
     */
    public final <R> R reduceExecute(IBizInstance bizInstance,
                                     String extensionCode,
                                     ExtensionCallback<ExtensionPoints, R> callback) {
        ExecuteResult<R> executeResult = reduceExecuteWithDetailResult(bizInstance, extensionCode, callback);
        if (null == executeResult.getResult()) {
            return null;
        }
        return executeResult.getResult();
    }

    /**
     * Load extension runners extension points.
     *
     * @param bizInstance   the biz instance
     * @param extensionCode the extension code
     * @return the extension points
     */
    private ExtensionPoints loadExtensionRunners(IBizInstance bizInstance, String extensionCode) {
        ExtensionPointSpec extensionPointSpec = getExtensionPointSpec(extensionCode);
        if (null == extensionPointSpec) {
            return null;
        }
        String bizCode = bizInstance.getBizInstanceId().getBizCode();
        AbstractTemplate template = TemplateManager.getInstance().getRegisteredTemplate(bizCode);
        if (template == null) {
            template = TemplateManager.getInstance().getRegisteredTemplate(BefConstants.BEF_PRODUCT_CODE);
        }
        if (template == null) {
            return null;
        }
        return loadExtensionRealization(bizCode, template, extensionCode);
    }

    /**
     * Gets extension point spec.
     *
     * @param extensionCode the extension code
     * @return the extension point spec
     */
    public ExtensionPointSpec getExtensionPointSpec(String extensionCode) {
        return FunctionManager.getInstance().getExtensionPointSpecByCode(extensionCode);
    }

    /**
     * Load extension realization extension points.
     *
     * @param bizCode       the biz code
     * @param template      the template
     * @param extensionCode the extension code
     * @return the extension points
     */
    private ExtensionPoints loadExtensionRealization(String bizCode, AbstractTemplate template, String extensionCode) {
        TemplateRealization[] realizations = template.getRealizationsInArray();
        IExtensionPointsFacade extFacade = findIExtensionPointsFacadeViaScenario(
            template.getCode(), extensionCode, realizations);
        if (extFacade != null) {
            return extFacade.getExtensionPointsByCode(extensionCode);
        }
        return null;
    }

    /**
     * Find i extension points facade via scenario extension points facade.
     *
     * @param templateCode  the template code
     * @param extensionCode the extension code
     * @param realizations  the realizations
     * @return the extension points facade
     */
    private IExtensionPointsFacade findIExtensionPointsFacadeViaScenario(String templateCode, String extensionCode,
                                                                         TemplateRealization[] realizations) {
        for (TemplateRealization realization : realizations) {
            IExtensionPointsFacade facade = realization.getExtensionRealization();
            if (facade != null && TemplateManager.templateCodeMatched(realization.getTemplateCode(), templateCode)
                && null != facade.getExtensionPointsByCode(extensionCode)) {
                return facade;
            }
        }
        return null;
    }

}

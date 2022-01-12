package com.lucifiere.bef.model;

import com.lucifiere.bef.specific.impl.ExtensionPointType;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * 扩展点定义
 *
 * @author 沾清
 */
public class ExtensionPointSpec extends BaseSpec {

    /**
     * The Type.
     */
    private ExtensionPointType type = ExtensionPointType.SYSTEM;

    /**
     * the parent code of the extension point.
     */
    private String parentCode;

    /**
     * The Priority.
     */
    private int priority;
    /**
     * the ability code of the extension point belong to.
     */
    private String abilityCode;

    /**
     * The Invoke method.
     */
    private Method invokeMethod;

    /**
     * Instantiates a new Extension point spec.
     *
     * @param invokeMethod the invoke method
     */
    public ExtensionPointSpec(Method invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    /**
     * create a ExtensionPointSpec.
     *
     * @param invokeMethod  the invoke method
     * @param parentCode    parent code of current extension belong to.
     * @param abilityCode   the ability code
     * @param extensionCode the extension unique code.
     * @param extensionName the extension name
     * @param extensionDesc the extension description.
     * @return the created ExtensionPointSpec.
     */
    public static ExtensionPointSpec of(Method invokeMethod, String parentCode, String abilityCode,
                                        String extensionCode, String extensionName, String extensionDesc) {
        ExtensionPointSpec extensionPointSpec = new ExtensionPointSpec(invokeMethod);
        extensionPointSpec.parentCode = parentCode;
        extensionPointSpec.abilityCode = abilityCode;
        extensionPointSpec.setCode(extensionCode);
        extensionPointSpec.setName(extensionName);
        extensionPointSpec.setDescription(extensionDesc);
        return extensionPointSpec;
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

        if (!(obj instanceof ExtensionPointSpec)) {
            return false;
        }
        ExtensionPointSpec target = (ExtensionPointSpec)obj;
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
        return "ExtensionPointSpec{" +
            "name='" + getName() + '\'' +
            ", parentCode='" + parentCode + '\'' +
            ", abilityCode='" + abilityCode + '\'' +
            ", invokeMethod=" + invokeMethod +
            '}';
    }

    /**
     * Gets type
     *
     * @return the type
     */
    public ExtensionPointType getType() {
        return type;
    }

    /**
     * Sets type
     *
     * @param type the type
     */
    public void setType(ExtensionPointType type) {
        this.type = type;
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
     * Gets invokeMethod
     *
     * @return the invokeMethod
     */
    public Method getInvokeMethod() {
        return invokeMethod;
    }

    /**
     * Sets invokeMethod
     *
     * @param invokeMethod the invokeMethod
     */
    public void setInvokeMethod(Method invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

}

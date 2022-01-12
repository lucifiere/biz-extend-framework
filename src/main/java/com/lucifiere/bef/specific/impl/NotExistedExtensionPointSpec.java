package com.lucifiere.bef.specific.impl;


import com.lucifiere.bef.model.ExtensionPointSpec;

import java.lang.reflect.Method;

/**
 * ExtensionPointSpec 空对象
 *
 * @author 沾清
 */
public class NotExistedExtensionPointSpec extends ExtensionPointSpec {
    /**
     * Instantiates a new Not existed extension point spec.
     *
     * @param invokeMethod the invoke method
     */
    public NotExistedExtensionPointSpec(Method invokeMethod) {
        super(invokeMethod);
    }
}

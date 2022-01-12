package com.lucifiere.bef.specific;

/**
 * 扩展点的Facade...,自身也可以是扁平的IExtensionPoint.
 *
 * @author 沾清
 */
public interface IExtensionPointsFacade extends IExtensionPoints {

    /**
     * get the ExtensionPoint realization by extension code.
     *
     * @param <ExtensionPoints> the type of the ExtensionPoint.
     * @param extensionCode     the extension code.
     * @return the ExtensionPoint realization.
     */
    <ExtensionPoints extends IExtensionPoints> ExtensionPoints getExtensionPointsByCode(String extensionCode);
}

package com.lucifiere.bef.specific;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The interface Business ext facade.
 *
 * @author 沾清
 */
public interface BusinessExtFacade extends BusinessExt {

    /**
     * The constant CLASS_POINT_MAP.
     */
    Map<Class<?>, Map<Key, IExtensionPoints>> CLASS_POINT_MAP = new ConcurrentHashMap<>(16);

    /**
     * Gets extension points by code.
     *
     * @param extensionCode the extension code
     * @return the extension points by code
     */
    @Override
    default IExtensionPoints getExtensionPointsByCode(String extensionCode) {

        String scenario = "None#";

        Key extensionCodeKey = new Key(scenario, extensionCode);

        Map<Key, IExtensionPoints> pointMap = BusinessExtFacade.CLASS_POINT_MAP.get(this.getClass());
        if (pointMap != null) {
            IExtensionPoints point = pointMap.get(extensionCodeKey);
            if (point != null) {
                return point;
            }
        }
        for (BusinessExt businessExt : getAllSubBusinessExt()) {
            IExtensionPoints point = businessExt.getExtensionPointsByCode(extensionCode);
            if (point != null) {
                if (pointMap == null) {
                    pointMap = new ConcurrentHashMap<>(16);
                    pointMap.put(extensionCodeKey, point);
                    CLASS_POINT_MAP.put(this.getClass(), pointMap);
                } else {
                    pointMap.put(extensionCodeKey, point);
                }
                return point;
            }
        }
        return null;
    }

    /**
     * Clear.
     */
    static void clear() {
        CLASS_POINT_MAP.clear();
    }
}
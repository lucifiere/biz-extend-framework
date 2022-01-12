package com.lucifiere.engine;

/**
 * 空对象
 *
 * @author 忠魂
 */
public class NullObject {
    /**
     * The constant INSTANCE.
     */
    private static final NullObject INSTANCE = new NullObject();

    /**
     * Instantiates a new Null object.
     */
    private NullObject() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static NullObject getInstance() {
        return INSTANCE;
    }
}

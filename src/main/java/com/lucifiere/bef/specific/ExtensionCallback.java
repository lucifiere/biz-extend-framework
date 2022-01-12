package com.lucifiere.bef.specific;

import java.util.function.Function;

/**
 * The interface Extension callback.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 * @author 沾清
 */
public interface ExtensionCallback<T extends IExtensionPoints, R> extends Function<T, R> {

}

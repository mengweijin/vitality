package com.mengweijin.mwjwork.framework.util.lambda;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@FunctionalInterface
public interface ISetterFunction<T, U> extends Serializable {
    /**
     * set
     * @param t t
     * @param u u
     */
    void set(T t, U u);
}

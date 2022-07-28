package com.github.mengweijin.quickboot.util.lambda;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@Deprecated
@FunctionalInterface
public interface ISetterFunction<T, U> extends Serializable {
    /**
     * set
     * @param t t
     * @param u u
     */
    void set(T t, U u);
}

package com.mwj.mwjwork.common.util.lambda;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@FunctionalInterface
public interface IGetterFunction<T> extends Serializable {

    /**
     * get
     * @param t t
     */
    void get(T t);
}

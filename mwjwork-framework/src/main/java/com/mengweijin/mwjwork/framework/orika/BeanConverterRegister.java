package com.mengweijin.mwjwork.framework.orika;

import ma.glasnost.orika.metadata.ClassMapBuilder;

/**
 * Subclasses only need to implement this interface to register the field maps of custom rules with Orika
 *
 * @author mengweijin
 */
public interface BeanConverterRegister<A, B> {

    /**
     * Field mapping configuration
     *
     * @param classMapBuilder classMapBuilder
     */
    void fieldMapping(ClassMapBuilder<A, B> classMapBuilder);

}

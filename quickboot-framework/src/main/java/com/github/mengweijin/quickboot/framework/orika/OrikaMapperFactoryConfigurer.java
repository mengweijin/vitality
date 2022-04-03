package com.github.mengweijin.quickboot.framework.orika;

import ma.glasnost.orika.MapperFactory;

/**
 * The interface to configure {@link MapperFactory}.
 */
public interface OrikaMapperFactoryConfigurer {

    /**
     * Configures the {@link MapperFactory}.
     *
     * @param orikaMapperFactory the {@link MapperFactory}.
     */
    void configure(MapperFactory orikaMapperFactory);

}

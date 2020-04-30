package com.mengweijin.mwjwork.framework.orika;

import cn.hutool.core.util.TypeUtil;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;

/**
 * @author mengweijin
 */
public abstract class BaseOrikaMapperFactoryConfigurator<A, B> implements OrikaMapperFactoryConfigurer {

    /**
     * Field mapping configuration
     *
     * @param classMapBuilder classMapBuilder
     */
    public abstract void fieldMapping(ClassMapBuilder<A, B> classMapBuilder);

    @Override
    public void configure(MapperFactory mapperFactory) {
        Class<A> aClass = (Class<A>) TypeUtil.getTypeArgument(this.getClass(), 0);
        Class<B> bClass = (Class<B>) TypeUtil.getTypeArgument(this.getClass(), 1);
        ClassMapBuilder<A, B> classMapBuilder = mapperFactory.classMap(aClass, bClass);
        fieldMapping(classMapBuilder);
        classMapBuilder.byDefault().register();
    }
}

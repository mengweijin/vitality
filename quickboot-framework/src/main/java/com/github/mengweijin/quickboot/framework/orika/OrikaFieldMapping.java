package com.github.mengweijin.quickboot.framework.orika;

import cn.hutool.core.util.TypeUtil;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mengweijin
 */
public abstract class OrikaFieldMapping<A, B> implements InitializingBean {

    @Autowired
    private MapperFactory mapperFactory;

    /**
     * Field mapping configuration
     *
     * @param classMapBuilder classMapBuilder
     */
    public abstract void fieldMapping(ClassMapBuilder<A, B> classMapBuilder);

    @Override
    public void afterPropertiesSet() {
        Class<A> aClass = (Class<A>) TypeUtil.getTypeArgument(this.getClass(), 0);
        Class<B> bClass = (Class<B>) TypeUtil.getTypeArgument(this.getClass(), 1);
        ClassMapBuilder<A, B> classMapBuilder = mapperFactory.classMap(aClass, bClass);
        fieldMapping(classMapBuilder);
        classMapBuilder.byDefault().register();
    }
}

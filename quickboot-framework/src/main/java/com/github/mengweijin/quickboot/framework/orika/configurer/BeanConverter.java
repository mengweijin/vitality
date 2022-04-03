package com.github.mengweijin.quickboot.framework.orika.configurer;

import cn.hutool.core.util.TypeUtil;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Bean converter initializes the processing class.
 * Subclasses only need to implement the fieldMapping method to custom the field maps of rules with Orika
 * @author mengweijin
 */
public abstract class BeanConverter<A, B> implements InitializingBean {

    @Autowired
    private MapperFactory mapperFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        // Register current bean converter to orika.
        Class<A> aClass = (Class<A>) TypeUtil.getTypeArgument(this.getClass(), 0);
        Class<B> bClass = (Class<B>) TypeUtil.getTypeArgument(this.getClass(), 1);
        ClassMapBuilder<A, B> classMapBuilder = mapperFactory.classMap(aClass, bClass);
        this.fieldMapping(classMapBuilder);
        classMapBuilder.byDefault().register();

    }

    /**
     * Field mapping configuration.
     * For Examples: classMapBuilder.field("name", "username");
     *
     * @param classMapBuilder classMapBuilder
     */
    abstract void fieldMapping(ClassMapBuilder<A, B> classMapBuilder);

}

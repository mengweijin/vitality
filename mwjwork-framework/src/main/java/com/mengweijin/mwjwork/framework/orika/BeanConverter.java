package com.mengweijin.mwjwork.framework.orika;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.TypeUtil;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Bean converter initializes the processing class.
 *
 * @author mengweijin
 */
@Component
public class BeanConverter<A, B> implements InitializingBean {

    @Autowired
    private MapperFactory mapperFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper("com.mengweijin", BeanConverterRegister.class);
        for (Class cls: classes) {
            if (!cls.isInterface() && !ClassUtil.isAbstract(cls)) {
                // Register current bean converter to orika.
                Class<A> aClass = (Class<A>) TypeUtil.getTypeArgument(cls, 0);
                Class<B> bClass = (Class<B>) TypeUtil.getTypeArgument(cls, 1);
                ClassMapBuilder classMapBuilder = mapperFactory.classMap(aClass, bClass);
                BeanConverterRegister beanConverterRegister = (BeanConverterRegister) cls.newInstance();
                beanConverterRegister.fieldMapping(classMapBuilder);
                classMapBuilder.byDefault().register();
            }
        }

    }
}

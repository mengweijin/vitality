package com.github.mengweijin.quickboot.framework.orika.configurer;

import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author mengweijin
 * @date 2022/4/3
 */
class BeanConverterTest {
    @Test
    public void test() {
        String basePackages = "com.github.mengweijin.quickboot.framework.orika";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(basePackages);

        MapperFacade mapperFacade = context.getBean(MapperFacade.class);

        BeanA beanA = new BeanA();

        BeanB beanB = mapperFacade.map(beanA, BeanB.class);

        Assertions.assertEquals(beanA.getNameA(), beanB.getNameB());
        Assertions.assertEquals(beanA.getMail(), beanB.getMail());
        Assertions.assertNotNull(beanB.getStringToDate());
        Assertions.assertNotNull(beanB.getStringToLocalDateTime());
        Assertions.assertNotNull(beanB.getLocalDate());
        Assertions.assertNotNull(beanB.getLocalDateTime());

        context.close();
    }
}
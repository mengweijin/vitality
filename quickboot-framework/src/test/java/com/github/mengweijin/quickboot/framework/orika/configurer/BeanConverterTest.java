package com.github.mengweijin.quickboot.framework.orika.configurer;

import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

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

        BeanA beanA = new BeanA().setNameA("Lisa").setMail("aa@aa.com").setCreateTime(new Date());

        BeanB beanB = mapperFacade.map(beanA, BeanB.class);

        Assertions.assertEquals(beanA.getNameA(), beanB.getNameB());
        Assertions.assertNotNull(beanB.getCreateTime());
        Assertions.assertEquals(beanA.getMail(), beanB.getMail());

        context.close();
    }
}
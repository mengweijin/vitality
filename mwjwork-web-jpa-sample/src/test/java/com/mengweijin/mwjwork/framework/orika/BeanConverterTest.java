package com.mengweijin.mwjwork.framework.orika;

import com.mengweijin.mwjwork.sample.MwjworkSampleApplication;
import ma.glasnost.orika.MapperFacade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MwjworkSampleApplication.class)
public class BeanConverterTest {

    @Autowired
    private MapperFacade mapperFacade;

    @Test
    public void afterPropertiesSet() {
        BeanA beanA = new BeanA("张三", 12, "abc@qq.com");
        BeanB beanB = mapperFacade.map(beanA, BeanB.class);
        Assert.assertEquals(beanA.getNameA(), beanB.getNameB());
        Assert.assertNull(beanB.getAgeB());
        Assert.assertEquals(beanA.getMail(), beanB.getMail());
    }

}
package com.github.mengweijin.mybatisplus.demo.FactoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class DemoFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        // 用户可以自定义这个实例的属性和对象本身如何生成
        UserFactoryEntity bean = new UserFactoryEntity();
        bean.setName("Jack");
        return bean;
    }

    @Override
    public Class<?> getObjectType() {
        return UserFactoryEntity.class;
    }

}

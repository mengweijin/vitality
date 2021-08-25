package com.github.mengweijin.mybatisplus.demo.FactoryBean;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "com.github.mengweijin.mybatisplus.demo.FactoryBean")
public class FactoryBeanTest {

    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FactoryBeanTest.class, args);
        String beanName = "demoFactoryBean";

        Object entityObject = context.getBean(beanName);
        Assertions.assertNotNull(entityObject);
        Assertions.assertTrue(entityObject instanceof UserFactoryEntity);

        Object factoryBeanObject = context.getBean(BeanFactory.FACTORY_BEAN_PREFIX + beanName);
        Assertions.assertNotNull(factoryBeanObject);
        Assertions.assertTrue(factoryBeanObject instanceof DemoFactoryBean);

        DemoFactoryBean bean = context.getBean(DemoFactoryBean.class);
        Object object = bean.getObject();
        Assertions.assertTrue(object instanceof UserFactoryEntity);
        UserFactoryEntity entity = (UserFactoryEntity) object;
        Assertions.assertEquals("Jack", entity.getName());

        context.close();
    }
}

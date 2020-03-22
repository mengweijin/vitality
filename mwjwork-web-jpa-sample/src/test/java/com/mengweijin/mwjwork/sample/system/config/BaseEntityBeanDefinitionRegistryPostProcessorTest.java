package com.mengweijin.mwjwork.sample.system.config;

import com.mengweijin.mwjwork.framework.util.SpringUtils;
import com.mengweijin.mwjwork.sample.system.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseEntityBeanDefinitionRegistryPostProcessorTest {

    @Test
    public void postProcessBeanDefinitionRegistry() {
        User user = SpringUtils.getBean(User.class);
        Assert.assertNotNull(user);
    }
}
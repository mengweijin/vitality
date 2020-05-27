package com.github.mengweijin.quickboot.framework.orika;

import cn.hutool.core.util.ClassUtil;
import com.github.mengweijin.quickboot.framework.orika.converter.LocalDateTimeToStringConverter;
import com.github.mengweijin.quickboot.framework.orika.converter.LocalDateToStringConverter;
import com.github.mengweijin.quickboot.framework.orika.converter.LocalTimeToStringConverter;
import ma.glasnost.orika.converter.BidirectionalConverter;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class OrikaBeanDefinitionRegistryPostProcessorTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void scanPackageBySuper() {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(null, BidirectionalConverter.class);
        Assert.assertTrue(classes.contains(LocalDateTimeToStringConverter.class));
        Assert.assertTrue(classes.contains(LocalDateToStringConverter.class));
        Assert.assertTrue(classes.contains(LocalTimeToStringConverter.class));
    }
}
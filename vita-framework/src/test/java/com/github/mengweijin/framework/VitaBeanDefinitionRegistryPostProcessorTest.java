package com.github.mengweijin.framework;

import com.github.mengweijin.vita.framework.VitaBeanDefinitionRegistryPostProcessor;
import com.github.mengweijin.vita.framework.constant.Const;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 */
class VitaBeanDefinitionRegistryPostProcessorTest {

    @Test
    void packagePath() {
        String pkg = ClassUtil.getPackage(VitaBeanDefinitionRegistryPostProcessor.class);
        Assertions.assertEquals("com.github.mengweijin.framework", pkg);

        String parentPkg = StrUtil.subBefore(pkg, Const.DOT, true);
        Assertions.assertEquals("com.github.mengweijin", parentPkg);
    }
}
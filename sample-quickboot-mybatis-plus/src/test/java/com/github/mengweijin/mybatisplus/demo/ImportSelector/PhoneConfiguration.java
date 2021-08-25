package com.github.mengweijin.mybatisplus.demo.ImportSelector;

import com.github.mengweijin.mybatisplus.demo.ImportSelector.entity.Phone1;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Import({
        Phone1.class,
        Phone2ImportSelector.class,
        Phone3DeferredImportSelector.class,
        Phone4ImportBeanDefinitionRegistrar.class
})
@Component
public class PhoneConfiguration {
}

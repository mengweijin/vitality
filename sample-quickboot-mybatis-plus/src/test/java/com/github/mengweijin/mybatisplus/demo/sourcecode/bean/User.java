package com.github.mengweijin.mybatisplus.demo.sourcecode.bean;

import com.github.mengweijin.mybatisplus.demo.sourcecode.importannotation.Phone1;
import com.github.mengweijin.mybatisplus.demo.sourcecode.importannotation.Phone2ImportSelector;
import com.github.mengweijin.mybatisplus.demo.sourcecode.importannotation.Phone3DeferredImportSelector;
import com.github.mengweijin.mybatisplus.demo.sourcecode.importannotation.Phone4ImportBeanDefinitionRegistrar;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = "classpath:test.properties")
@ComponentScan(value = "com.github.mengweijin.mybatisplus.demo.sourcecode.componentscan")
@Import({
        Phone1.class,
        Phone2ImportSelector.class,
        Phone3DeferredImportSelector.class,
        Phone4ImportBeanDefinitionRegistrar.class
})
public class User {

    private String name = "jack";
}

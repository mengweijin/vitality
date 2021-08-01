package com.github.mengweijin.mybatisplus.demo.test;

import com.github.mengweijin.mybatisplus.demo.importannotation.Phone1;
import com.github.mengweijin.mybatisplus.demo.importannotation.Phone2ImportSelector;
import com.github.mengweijin.mybatisplus.demo.importannotation.Phone3DeferredImportSelector;
import com.github.mengweijin.mybatisplus.demo.importannotation.Phone4ImportBeanDefinitionRegistrar;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = "classpath:test.properties")
@ComponentScan(value = "com.github.mengweijin.mybatisplus.demo.scan")
@Import({Phone1.class, Phone2ImportSelector.class, Phone3DeferredImportSelector.class, Phone4ImportBeanDefinitionRegistrar.class})
public class UserService {

    private String name = "jack";
}

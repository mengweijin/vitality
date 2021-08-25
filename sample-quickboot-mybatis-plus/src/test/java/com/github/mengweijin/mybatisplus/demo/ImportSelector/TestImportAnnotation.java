package com.github.mengweijin.mybatisplus.demo.ImportSelector;

import com.github.mengweijin.mybatisplus.demo.ImportSelector.entity.Phone1;
import com.github.mengweijin.mybatisplus.demo.ImportSelector.entity.Phone2;
import com.github.mengweijin.mybatisplus.demo.ImportSelector.entity.Phone3;
import com.github.mengweijin.mybatisplus.demo.ImportSelector.entity.Phone4;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class TestImportAnnotation {

    @Test
    public void importSelector() {
        String basePackages = "com.github.mengweijin.mybatisplus.demo.ImportSelector";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(basePackages);

        Assertions.assertNotNull(context.getBean(Phone1.class));
        Assertions.assertNotNull(context.getBean(Phone2.class));
        Assertions.assertNotNull(context.getBean(Phone3.class));
        Assertions.assertNotNull(context.getBean(Phone4.class));

        context.close();
    }
}

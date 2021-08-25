package com.github.mengweijin.mybatisplus.demo.configuration;

import com.github.mengweijin.mybatisplus.demo.configuration.entity.Student;
import com.github.mengweijin.mybatisplus.demo.configuration.entity.StudentFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class TestConfigurationAndComponentAnnotation {

    @Test
    public void testComponentAnnotation() {
        String basePackages = "com.github.mengweijin.mybatisplus.demo.configuration";
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(basePackages);

        // 看下面这两个打印的 HashCode 是否一样
        Student student = applicationContext.getBean(Student.class);
        StudentFactory studentFactory = applicationContext.getBean(StudentFactory.class);
        Student student2 = studentFactory.getStudent();

        Assertions.assertTrue(student == student2);
        Assertions.assertTrue(student.hashCode() == student2.hashCode());
    }
}

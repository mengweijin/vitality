package com.github.mengweijin.mybatisplus.demo.sourcecode;

import com.github.mengweijin.mybatisplus.demo.sourcecode.configuration.Student;
import com.github.mengweijin.mybatisplus.demo.sourcecode.configuration.StudentFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class SpringBootConfigurationDemoTest {

    @Test
    public void testComponentAnnotation() {
        String basePackages = "com.github.mengweijin.mybatisplus.demo.sourcecode.bean";
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(basePackages);

        // 看下面这两个打印的 HashCode 是否一样
        Student student = applicationContext.getBean(Student.class);
        log.debug("hashcode - {}", student.hashCode());
        StudentFactory studentFactory = applicationContext.getBean(StudentFactory.class);
        log.debug("hashcode - {}", studentFactory.getStudent().hashCode());
        // 光看 hashcode 不行
        log.debug("{}", student == studentFactory.getStudent());
    }
}

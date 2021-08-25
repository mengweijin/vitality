package com.github.mengweijin.mybatisplus.demo.configuration;

import com.github.mengweijin.mybatisplus.demo.configuration.entity.Student;
import com.github.mengweijin.mybatisplus.demo.configuration.entity.StudentFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
//@Component
@Configuration
public class StudentConfiguration {

    @Bean
    public Student student() {
        return new Student();
    }

    @Bean
    public StudentFactory studentFactory() {
        StudentFactory factory = new StudentFactory();
        factory.setStudent(student());
        return factory;
    }
}

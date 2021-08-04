package com.github.mengweijin.mybatisplus.demo.sourcecode;

import com.github.mengweijin.mybatisplus.demo.sourcecode.configuration.Student;
import com.github.mengweijin.mybatisplus.demo.sourcecode.configuration.StudentFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Configuration 和 Component 注解的异同：
 * 两个都会被 Spring 实例化，主要区别如这个类中所示：
 * 在 StudentFactory 中有 this.student(); 那么问题来了，通过
 * applicationContext.getBean(Student.class) 和 applicationContext.getBean(StudentFactory.class).getStudent() 拿到的Student是同一个对象吗（Hash值是否一样）？
 * <p>
 * 分两种情况：
 * 1. 如果这个类加的 @Configuration 注解，实例化 Student 时，会走代理生成代理对象，此时两种打印方式都是从 factoryBean.getObject() 获取的，是单例的，因此一样。
 * 2. 如果这个类加的 @Component 注解，那么 this.student() 就是一个普通方法，重新创建了一个对象，因此打印的结果就不一样，破坏了Spring的单例，这是错误的用法。
 * <p>
 * 总结：@Configuration 中所有带 @Bean 注解的方法都会被动态代理，因此调用该方法返回的都是同一个实例。
 * <p>
 * 注意：测试的时候，不要用 Lombok 加 @Data 注解，这个会重写 Student 和 StudentFactory 的 hashcode 方法，造成观测结果不正确。
 * 或者也不能手动重写 hashcode 方法。
 * 也可以使用 log.debug("{}", student == studentFactory.getStudent()); 来判断两个对象是不是同一个对象。
 */
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

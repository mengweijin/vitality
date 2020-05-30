# quickboot-framework

## 介绍
快速搭建springboot 2.0项目，整合和配置常用的模块功能，节省搭建项目工程的时间。

## 依赖包
|groupId   |artifactId  |备注  |
|----      |----        |---- |
|org.springframework.boot|spring-boot-starter||
|org.springframework.boot|spring-boot-starter-web||
|org.springframework.boot|spring-boot-starter-validation|springboot 2.3.0 以后，spring-boot-starter-web中不再包含validation starter|
|org.springframework.boot|spring-boot-starter-aop||
|org.springframework.boot|spring-boot-configuration-processor||
|org.springframework.boot|spring-boot-starter-cache||
|javax.cache|cache-api||
|org.ehcache|ehcache|ehcache3|
|org.springframework.boot|spring-boot-starter-actuator||
|ma.glasnost.orika|orika-core||
|p6spy|p6spy||
|com.github.gavlyukovskiy|p6spy-spring-boot-starter||
|com.google.guava|guava||
|org.apache.commons|commons-lang3||
|commons-beanutils|commons-beanutils||
|org.apache.commons|commons-collections4||
|commons-io|commons-io||
|commons-fileupload|commons-fileupload||
|com.alibaba|fastjson||
|org.json|json||
|cn.hutool|hutool-all||
|org.jsoup|jsoup||

## 详细介绍
### async
启用异步注解@EnableAsync，初始化了两个Executor实例：
* @Bean("simple") 单线程的线程池
* @Bean("pool") 多线程的线程池，线程池的个数根据CPU的核心数计算。
~~~~
在你的工程中如何使用？
在你的异步任务方法上加上注解：@Async("simple")或者@Async("pool")，如：

@Async("simple")
public Future<String> doTask() {
    // do something
    return new AsyncResult<>("SUCCESS");
}
~~~~

### cache
启用缓存注解@EnableCaching，实例化了一个CacheManager：
* 默认缓存名称：CacheConfig.DEFAULT_CACHE_ALIAS
* heap(1000L, EntryUnit.ENTRIES)
* withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10L)))
* withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(10L)))
~~~~
在你的工程中如何使用？
1. 使用注解的方式（自行google）
2. 使用手动方式放入/取出缓存
@Autowired
private CacheManager ehCacheManager;
Cache<Serializable, Serializable> cache = ehCacheManager.getCache(CacheConfig.DEFAULT_CACHE_ALIAS, Serializable.class, Serializable.class);
* 放入缓存：cache.put("a", "b");
* 取出缓存：cache.get("a");
~~~~

### Const类
枚举了常用的特殊String字符

### cors
跨域配置。根据规则在application.yml中配置：
~~~~yaml
quickboot:
  cors:
    # 是否启用自动配置。如果为false, 则默认采用Springboot规则（不能跨域请求）；如果为true，需要配置白名单来允许跨域请求
    enabled: true
    # url白名单， 默认 * ，即允许所有ip跨域
    urlWhiteList:
        - http://localhost:80
        - http://127.0.0.1:80
        - http://localhost:8080
        - http://127.0.0.1:8080
        - http://localhost:8010
        - http://127.0.0.1:8010
    # header 白名单， 默认 * ，即允许所有类型的header跨域
    headerWhiteList:
    # method 白名单， 默认 * ，即允许所有方式的http请求跨域
    methodWhiteList:
      - GET
      - POST
      - PATCH
      - PUT
      - DELETE
~~~~

### exception
自定义异常类：
* ClientException：客户端异常
* ServerException：服务端异常

### orika
Bean字段映射工具，更多使用参考Orika官方文档。除了默认的转换器，quickboot还添加了以下转换器：
* converterFactory.registerConverter(new LocalDateToStringConverter());
* converterFactory.registerConverter(new LocalTimeToStringConverter());
* converterFactory.registerConverter(new LocalDateTimeToStringConverter());
* converterFactory.registerConverter(DatePattern.NORM_DATETIME_MS_PATTERN, new DateToStringConverter(DatePattern.NORM_DATETIME_MS_PATTERN));
* converterFactory.registerConverter(DatePattern.NORM_DATETIME_PATTERN, new DateToStringConverter(DatePattern.NORM_DATETIME_PATTERN));
* converterFactory.registerConverter(DatePattern.NORM_DATETIME_MINUTE_PATTERN, new DateToStringConverter(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
* converterFactory.registerConverter(DatePattern.NORM_DATE_PATTERN, new DateToStringConverter(DatePattern.NORM_DATE_PATTERN));
* converterFactory.registerConverter(DatePattern.CHINESE_DATE_PATTERN, new DateToStringConverter(DatePattern.CHINESE_DATE_PATTERN));
~~~~
在你的工程中如何使用？

@Autowired
private MapperFacade mapperFacade;
在方法中使用其方法，把Bean A转为Bean B:
B b = mapperFacade.map(A, B);
~~~~
Bean字段映射规则配置：自定义一个类继承类OrikaFieldMapping<A, B>，添加@Component注解，重写fieldMapping方法：
~~~~java
package com.github.mengweijin.mwjwork.quickboot.orika;

import cn.hutool.core.date.DatePattern;
import com.github.mengweijin.quickboot.framework.orika.OrikaFieldMapping;
import ma.glasnost.orika.metadata.ClassMapBuilder;

@Component
public class BeanAToBeanBFieldMapping extends OrikaFieldMapping<BeanA, BeanB> {

    @Override
    public void fieldMapping(ClassMapBuilder<BeanA, BeanB> classMapBuilder) {
        classMapBuilder
                .field("nameA", "nameB")
                .fieldMap("normDate", "normDate").converter(DatePattern.NORM_DATE_PATTERN).add()
                .fieldMap("normDateTimeMinute", "normDateTimeMinute").converter(DatePattern.NORM_DATETIME_MINUTE_PATTERN).add()
                .fieldMap("normDateTimeMs", "normDateTimeMs").converter(DatePattern.NORM_DATETIME_MS_PATTERN).add()
                .fieldMap("normDateTime", "normDateTime").converter(DatePattern.NORM_DATETIME_PATTERN).add()
                .fieldMap("chineseDate", "chineseDate").converter(DatePattern.CHINESE_DATE_PATTERN).add()
        ;
    }
}
~~~~

### scheduler
启用Spring调度器，启用注解@EnableScheduling
~~~~
在你的工程中如何使用？

/**
 * 每天晚上4点执行一次
 */
@Scheduled(cron = "0 0 4 * * ?")
public void doTask() {
    // do something
}
~~~~

### util
常用工具类

### web
Spring MVC 工程常用配置
* 注入Bean RestTemplate
* Default ExceptionHandler，统一异常处理
* BaseController
* 其他

### xss
XSS 过滤配置。根据规则在application.yml中配置：
~~~~yaml
quickboot:
  xss:
    # 是否启用xss过滤，默认true
    enabled: true
    # 不需要xss校验的链接（配置示例：/system/*,/tool/*）
    excludes: /druid/*,/actuator/*
~~~~


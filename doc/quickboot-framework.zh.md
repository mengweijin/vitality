# quickboot-framework

## 介绍
快速搭建springboot 2.0项目，整合和配置常用的模块功能，节省搭建项目工程的时间。

## 依赖包
|groupId   |artifactId  |备注  |
|----      |----        |---- |
|org.springframework.boot|spring-boot-starter-web||
|org.springframework.boot|spring-boot-starter-validation|springboot 2.3.0 以后，spring-boot-starter-web中不再包含validation starter|
|org.springframework.boot|spring-boot-starter-aop||
|org.springframework.boot|spring-boot-configuration-processor||
|org.springframework.boot|spring-boot-starter-cache||
|javax.cache|cache-api||
|org.ehcache|ehcache|ehcache3|
|org.springframework.boot|spring-boot-starter-actuator||
|com.github.gavlyukovskiy|p6spy-spring-boot-starter||
|cn.hutool|hutool-all||
|org.jsoup|jsoup||

## 详细介绍

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

### async and scheduler
启用Spring调度器，注解@EnableScheduling
启用异步任务，注解@EnableAsync

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


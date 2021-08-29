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
|org.springframework.boot|spring-boot-starter-actuator||
|com.github.gavlyukovskiy|p6spy-spring-boot-starter||
|cn.hutool|hutool-all||
|org.jsoup|jsoup||

## 详细介绍

### SafetyEncryptEnvironmentPostProcessor 安全加密处理器

可以实现配置文件敏感信息的加密配置。比如：数据库密码等信息。 使用方式：

```
1. 生成 16 位随机 AES 密钥，在启动 jar 时把下面生成的 key 通过命令行参数 --cipher.key 传递到应用程序中
SecretKey key = SecureUtil.generateKey("AES");

2. 密钥加密：配置在 application.yaml 中的加密值
String result = SecureUtil.aes(key).encrypt("password");

3. YML 配置：加密配置 quickboot: 开头紧接加密内容（ 非数据库配置专用 YML 中其它配置也是可以使用的 ）
spring:
    datasource:
        url: {cipher}qRhvCwF4GOqjessEB3G+a5okP+uXXr96wcucn2Pev6Bf1oEMZ1gVpPPhdDmjQqoM
        password: {cipher}Hzy5iliJbwDHhjLs1L0j6w==
        username: {cipher}Xb+EgsyuYRXw7U7sBJjBpA==
```

### Cache 以及缓存过期 CacheExpire

启用缓存注解@EnableCaching，SpringBoot 默认会根据引入的依赖实例化了一个 CacheManager：

#### @CacheExpire

QuickBoot 扩展了缓存功能，提供 @CacheExpire 注解配合 @Cacheable 等 SpringBoot 提供的注解一起工作。

@CacheExpire 参数说明：

- expire：缓存过期时间，默认值为 0，即缓存永不过期。如果同时配置了 cron 和 expire > 0, 优先使用 expire 规则。
- chronoUnit：ChronoUnit 类，默认值为 ChronoUnit.MINUTES。即默认单位为分钟。和 expire 搭配使用。
- cron：cron 表达式来控制缓存过期时间。

```java
/**
 * 此示例中，配置了 user 缓存 20秒 自动过期，被清除。
 */
@CacheExpire(expire = 20, chronoUnit = ChronoUnit.SECONDS)
@Cacheable(cacheNames = "user")
@GetMapping("/get")
public List<User> getUser(){
    return userService.list();
}
```

#### CacheManager 的使用

* 放入缓存：cache.put(Object key, @Nullable Object value);
* 取出缓存：cache.get(Object key);

```java
@Autowired
private CacheManager cacheManager;
        Cache cache=cacheManager.getCache("cacheName");
```

### Const类

枚举了常用的特殊String字符

### cors 跨域配置

跨域配置。根据规则在application.yml中配置：
~~~~yaml
quickboot:
  cors:
    # 是否启用自动配置，默认 false。如果为 false, 则默认采用 SpringBoot 规则（不能跨域请求）；
    enabled: true
~~~~

### exception

自定义异常类：

* ClientException：客户端异常
* ServerException：服务端异常

### AOP debug 级别请求、响应参数详情等日志记录

- RequestLogAop
- AopLogger

### p6spy 数据库查询日志记录

自动记录每一条真实查询的 SQL 记录到 debug 日志中。

~~~yaml
decorator:
  datasource:
    enabled: true
    p6spy:
      # Register P6LogFactory to log JDBC events
      enable-logging: true
      # Use com.p6spy.engine.spy.appender.MultiLineFormat instead of com.p6spy.engine.spy.appender.SingleLineFormat
      multiline: true
      # Use logging for default listeners [slf4j, sysout, file]
      logging: slf4j
      # Log file to use (only with logging=file)
      #log-file: spy.log
      # Custom log format, if specified com.p6spy.engine.spy.appender.CustomLineFormat will be used with this log format
      #log-format:
      tracing:
        include-parameter-values: true
~~~

### knife4j 自动文档接口（Swagger）

启动服务后访问 url: http://localhost:8080/doc.html

~~~yaml
# http://localhost:8080/doc.html
knife4j:
  enable: true
  documents:
    - group: 1.2.x
      name: 测试自定义标题分组
      locations: classpath:md/*
  setting:
    enableSwaggerModels: true
    enableDocumentManage: true
    enableHost: false
    enableHostText: http://localhost:999
    enableRequestCache: true
    enableFilterMultipartApis: false
    enableFilterMultipartApiMethodType: POST
    language: zh-CN
  cors: false
  production: false
  basic:
    enable: false
    username: test
    password: 123
~~~

### async and scheduler

启用Spring调度器，注解@EnableScheduling 启用异步任务，注解@EnableAsync

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


# quickboot

Language: [English](README.md)

<p align="center">	
	<a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.mengweijin%22%20AND%20a:%22quickboot-parent%22">
		<img src="https://img.shields.io/maven-central/v/com.github.mengweijin/quickboot-parent" />
	</a>
	<a target="_blank" href="https://github.com/mengweijin/quickboot/blob/master/LICENSE">
		<img src="https://img.shields.io/badge/license-Apache2.0-blue.svg" />
	</a>
	<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
		<img src="https://img.shields.io/badge/JDK-8+-green.svg" />
	</a>
	<a target="_blank" href="https://gitee.com/mengweijin/quickboot/stargazers">
		<img src="https://gitee.com/mengweijin/quickboot/badge/star.svg?theme=dark" alt='gitee star'/>
	</a>
	<a target="_blank" href='https://github.com/mengweijin/quickboot'>
		<img src="https://img.shields.io/github/stars/mengweijin/quickboot.svg?style=social" alt="github star"/>
	</a>
</p>

## 介绍
快速搭建 SpringBoot 项目，整合和配置常用的模块功能，节省搭建项目工程的时间。

## cache-expired-spring-boot-starter
启用缓存注解 @EnableCaching. SpringBoot 默认会根据引入的依赖实例化了一个 CacheManager：

SpringBoot 原生的 @Cacheable/@CachePut 没有缓存过期的处理。

这里额外提供一个 @CacheExpired 注解，配合 @Cacheable/@CachePut 使用，提供缓存过期注解。
```xml
<project>
  <parent>
    <groupId>com.github.mengweijin</groupId>
    <artifactId>quickboot-parent</artifactId>
    <version>Latest Version</version>
  </parent>
    
    <dependencies>
      <dependency>
        <groupId>com.github.mengweijin</groupId>
        <artifactId>cache-expired-spring-boot-starter</artifactId>
      </dependency>
    </dependencies>
</project>
```
```java
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * 10 秒后，缓存自动过期。
     */
    @CacheExpired(expire = 10, chronoUnit = ChronoUnit.SECONDS)
    @Cacheable(cacheNames = "user")
    @GetMapping("/cache")
    public String hello(){
        log.info("Entered hello method.");
        return "Hello";
    }
}
```
#### 问题说明
- 当使用存储在应用内存中的缓存管理器时，@CacheExpire 注解配合 @Cacheable 或 @CachePut 非常完美。
- 当使用存储到磁盘的缓存管理器时，应用重启后，存在已经缓存的数据无法使其缓存过期的问题。如何解决？
  - 此时建议当使用存储到磁盘的缓存管理器（如：Redis）时，在 Redis 中设置好缓存过期时间，达到双重清理缓存的目的。这样就能同时兼容使用保存在内存或者磁盘的缓存管理器。

@CacheExpire 参数说明：
- expire：缓存过期时间，默认值为 0，即缓存永不过期。如果同时配置了 cron 和 expire > 0, 优先使用 expire 规则。
- chronoUnit：ChronoUnit 类，默认值为 ChronoUnit.MINUTES。即默认单位为分钟。和 expire 搭配使用。
- cron：cron 表达式来控制缓存过期时间。

#### CacheManager 的使用
* 放入缓存：cache.put(Object key, @Nullable Object value);
* 取出缓存：cache.get(Object key);
```java
  @Autowired
  private CacheManager cacheManager;
  Cache cache=cacheManager.getCache("cacheName");
```

## xss-spring-boot-starter
XSS 过滤配置。根据规则在application.yml中配置：
~~~~yaml
xss:
  # 是否启用xss过滤，默认true
  enabled: true
  # 不需要xss校验的链接（配置示例：/system/*,/tool/*）
  excludes: /druid/*,/actuator/*
~~~~
```java
@RestController
@RequestMapping("/user")
public class UserController {
  /**
   * 访问 http://localhost:8080/user/xss?html=<script>aaaa</script>bbbb
   * @return 返回 bbbb，说明过滤了 <script>aaaa</script>
   */
  @GetMapping("/xss")
  public String xss(String html){
    log.info("Entered xss method.");
    return html;
  }
}
```

## quickboot-framework-spring-boot-starter
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

### cors 跨域配置
跨域配置。根据规则在application.yml中配置：
~~~~yaml
quickboot:
  cors:
    # 是否启用自动配置，默认 false。如果为 false, 则默认采用 SpringBoot 规则（不能跨域请求）；
    enabled: true
~~~~

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

### Const类
枚举了常用的特殊String字符

### exception
自定义异常类：
* QuickBootClientException：客户端异常
* QuickBootException：服务端异常

### AOP debug 级别，请求、响应参数详情等日志记录
仅当 spring.profiles.active 中配置了 dev 才启用。如：spring.profiles.active=quickboot,dev
- RequestLogAop
- AopLogger

### async and scheduler

启用Spring调度器，注解@EnableScheduling 启用异步任务，注解@EnableAsync

### util

常用工具类

### web

Spring MVC 工程常用配置
* 注入Bean RestTemplate
* Default ExceptionHandler，统一异常处理
* BaseController

## quickboot-mybatis-plus-spring-boot-starter
集成 MyBatis-Plus.

配置：
~~~yaml
# mybatis-plus
mybatis-plus:
  # MyBatis Mapper 所对应的 XML 文件位置，如果您在 Mapper 中有自定义方法（XML 中有自定义实现），需要进行该配置，告诉 Mapper 所对应的 XML 文件位置。
  mapper-locations: classpath*:/mapper/**/*.xml
  # 配置扫描通用枚举，支持统配符 * 或者 ; 分割，如果配置了该属性，会将路径下的枚举类进行注入，让实体类字段能够简单快捷的使用枚举属性
  #type-enums-package: com.mwj.cms.common.enums
  global-config:
    # 打印mybatis-plus的logo
    banner: false
    db-config:
      # 是否开启 LIKE 查询，即根据 entity 自动生成的 where 条件中 String 类型字段 是否使用 LIKE，默认不开启。
      #column-like: false
      # 全局默认主键生成策略类型。默认值ASSIGN_ID
      id-type: ASSIGN_ID
      # 表名、是否使用下划线命名，默认数据库表使用下划线命名
      table-underline: true
      # 是否开启大写命名，默认不开启
      capital-mode: false
      #全局逻辑删除字段值 3.3.0开始支持。
      logic-delete-field: deleted
      # 逻辑已删除值,(逻辑删除下有效)
      logic-delete-value: 1
      # 逻辑未删除值,(逻辑删除下有效)
      logic-not-delete-value: 0
  configuration:
    # MyBatis 在使用 resultMap 来映射查询结果中的列，如果查询结果中包含空值的列，则 MyBatis 在映射的时候，不会映射这个字段，
    # 这就导致在调用到该字段的时候由于没有映射，取不到而报空指针异常。
    call-setters-on-nulls: true
    # 对JavaBean中属性开启自动驼峰命名规则（camel case）映射，默认对返回类型为Map的对象的key不起作用，所以需要使用自定义MybatisMapWrapperFactory类来处理
    map-underscore-to-camel-case: true
~~~
~~~~xml
<project>
	<parent>
		<artifactId>quickboot-parent</artifactId>
		<groupId>com.github.mengweijin</groupId>
		<version>Latest version</version>
	</parent>
    
    <dependencies>
      <dependency>
        <groupId>com.github.mengweijin</groupId>
        <artifactId>quickboot-mybatis-plus-spring-boot-starter</artifactId>
      </dependency>
    </dependencies>
</project>
~~~~

### flyway
~~~yaml
spring:
  # flyway在spring boot中默认配置位置为：classpath:db/migration
  # flyway命名规则为：V<VERSION>__<NAME>.sql (with <VERSION> an underscore-separated version, such as ‘1’ or ‘2_1’)
  flyway:
    # 默认不启用
    enabled: true
    baseline-on-migrate: true
    locations:
      - classpath:db/h2
      # - classpath:db/mysql
      # - classpath:db/oracle
~~~

### 分页 Pager
提供默认的分页对象的入参和返回对象统一处理。
- MyBatisPlusPageArgumentResolver
- PageResponseBodyAdvice

使用如下：
~~~java
@GetMapping("/page")
public IPage<User> getPage(Page<User> page){
    return userService.page(page);
}
~~~

### createBy, createTime, updateBy, updateTime 字段的自动填充
参考类：
- DefaultMetaObjectHandler 默认填充为 SYSTEM， 可以修改。
- BaseEntity 示例如下：
~~~java
@TableField(value = "create_time", fill = FieldFill.INSERT)
protected LocalDateTime createTime;

@TableField(value = "create_by", fill = FieldFill.INSERT)
protected String createBy;

@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
protected LocalDateTime updateTime;

@TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
protected String updateBy;
~~~

## quickboot-jpa-spring-boot-starter
集成 Sping Data JPA. 提供如下功能类：
- BaseJpaRepository.java
- BaseServiceImpl.java
- EntityMapCamelCaseResultTransformer.java 返回 Map 时下划线转驼峰。

配置 basePackages（不配置默认为 basePackages = com）。并且配置的包名要和你项目中的包名保持一直，否则无法使用 BaseJpaRepository, BaseServiceImpl.
```yaml
quickboot:
  # 如果不配置下面这个，默认 basePackages = com
  jpa:
    basePackages:
      - com
```

~~~~xml
<project>
	<parent>
		<artifactId>quickboot-parent</artifactId>
		<groupId>com.github.mengweijin</groupId>
		<version>Latest version</version>
	</parent>
    
    <dependencies>
      <dependency>
        <groupId>com.github.mengweijin</groupId>
        <artifactId>quickboot-jpa-spring-boot-starter</artifactId>
      </dependency>
    </dependencies>
</project>
~~~~

### BaseJpaRepository 和 BaseService
- 兼容所有原生实现 JpaRepository 的功能；
- 支持数据库表字段的部分值更新的 update 方法；
- 支持自定义 SQL 的 findByNativeSQL 和 updateByNativeSQL 方法，返回集合并且字段名自动下划线转为驼峰（EntityMapCamelCaseResultTransformer）；
- 支持 QueryDSL 查询；
- Service 层继承 BaseServiceImpl 即可不用注入（@Autowired） Repository 而直接使用其方法。

使用方式如下：

- 只需要把原来实现 JpaRepository 接口改为 BaseJpaRepository。
- Service 层继承 BaseServiceImpl 即可不用注入（@Autowired） Repository 而直接使用其方法。

~~~java
@Repository
public interface UserRepository extends BaseJpaRepository<User, Long> {
}

@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {
}
~~~
### ID Generator

在单机应用下，提供了两个 ID 生成器

- SnowflakeIdGenerator
- TimestampIdGenerator

使用方式：在 BaseEntity 中有参考示例，部分如下：

~~~java
@Id
@GenericGenerator(name = ID_GENERATOR_NAME, strategy = STRATEGY_TIMESTAMP)
@GeneratedValue(generator = ID_GENERATOR_NAME)
@JsonSerialize(using = ToStringSerializer.class)
@Column(name = "ID")
protected Long id;
~~~

### 分页 Pager

提供默认的分页对象的入参和返回对象统一处理。

- JpaPageArgumentResolver
- PageResponseBodyAdvice

使用如下：

~~~java
@GetMapping("/page")
public Page findPage(Pager pager){
    Pageable pageable=PageRequest.of(pager.getCurrent(),pager.getSize());
    return userService.findAll(pageable);
}
~~~

### createBy, createTime, updateBy, updateTime 字段的自动填充

参考类：

- AuditorAwareImpl 默认填充为 SYSTEM， 可以修改。
- BaseEntity 示例如下：

```java
  @CreatedDate
  //@DateTimeFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
  //@JsonFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
  @Column(name = "CREATE_TIME")
  protected LocalDateTime createTime;
  
  @LastModifiedDate
  //@DateTimeFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
  //@JsonFormat(pattern = DateFormatUtil.YYYY_MM_DD_HH_MM_SS)
  @Column(name = "UPDATE_TIME")
  protected LocalDateTime updateTime;
  
  @CreatedBy
  @Column(name = "CREATE_BY")
  protected String createBy;
  
  @LastModifiedBy
  @Column(name = "UPDATE_BY")
  protected String updateBy;
```

## quickboot-web
springboot 2.0+, thymeleaf，公共 include.html, index.html 页面，以及常用前端组件。
~~~xml
<project>
    <parent>
        <artifactId>quickboot-parent</artifactId>
        <groupId>com.github.mengweijin</groupId>
        <version>Latest version</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>com.github.mengweijin</groupId>
            <artifactId>quickboot-web</artifactId>
        </dependency>
    </dependencies>
</project>
~~~

引用的包如下：
~~~xml
<webjars-locator.version>0.38</webjars-locator.version>
<vue.version>2.6.11</vue.version>
<element-ui.version>2.14.0</element-ui.version>
<axios.version>0.21.0</axios.version>
<dayjs.version>1.9.6</dayjs.version>
<respond.version>1.4.2</respond.version>
<html5shiv.version>3.7.3</html5shiv.version>
~~~


## 依赖包最新版本查询
|依赖|文档|代码|最新版本|
|---:|:---|:---|:---|
|Spring-Boot|[Spring-Boot](https://spring.io/projects/spring-boot)|[Github](https://github.com/spring-projects/spring-boot)|<a target="_blank" href="https://search.maven.org/search?q=g:%22org.springframework.boot%22%20AND%20a:%22spring-boot-dependencies%22"><img src="https://img.shields.io/maven-central/v/org.springframework.boot/spring-boot-dependencies"/></a>|
|mybatis-plus|[mybatis-plus](https://baomidou.com/)|[Gitee](https://gitee.com/baomidou/mybatis-plus) <br> [Github](https://github.com/baomidou/mybatis-plus)|<a target="_blank" href="https://search.maven.org/search?q=g:%22com.baomidou%22%20AND%20a:%22mybatis-plus-boot-starter%22"><img src="https://img.shields.io/maven-central/v/com.baomidou/mybatis-plus-boot-starter"/></a>|					
|Hutool|[Hutool](https://hutool.cn/)|[Gitee](https://gitee.com/dromara/hutool/) <br> [Github](https://github.com/dromara/hutool/)|<a target="_blank" href="https://search.maven.org/search?q=g:%22cn.hutool%22%20AND%20a:%22hutool-all%22"><img src="https://img.shields.io/maven-central/v/cn.hutool/hutool-all"/></a>|
|p6spy|[p6spy](https://p6spy.readthedocs.io/en/latest/integration.html#spring-boot-autoconfiguration)|[Github](https://github.com/p6spy/p6spy)|<a target="_blank" href="https://search.maven.org/search?q=g:%22p6spy%22%20AND%20a:%22p6spy%22"><img src="https://img.shields.io/maven-central/v/p6spy/p6spy"/></a>|
|p6spy-spring-boot-starter| |[Github](https://github.com/gavlyukovskiy/spring-boot-data-source-decorator)|<a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.gavlyukovskiy%22%20AND%20a:%22p6spy-spring-boot-starter%22"><img src="https://img.shields.io/maven-central/v/com.github.gavlyukovskiy/p6spy-spring-boot-starter"/></a>|
|knife4j-spring-boot-starter|[knife4j](https://doc.xiaominfo.com/knife4j/documentation/)|[Gitee](https://gitee.com/xiaoym/knife4j)|<a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.xiaoymin%22%20AND%20a:%22knife4j-spring-boot-starter%22"><img src="https://img.shields.io/maven-central/v/com.github.xiaoymin/knife4j-spring-boot-starter"/></a>|
|jsoup|[jsoup](https://jsoup.org/)|[Github](https://github.com/jhy/jsoup)|<a target="_blank" href="https://search.maven.org/search?q=g:%22org.jsoup%22%20AND%20a:%22jsoup%22"><img src="https://img.shields.io/maven-central/v/org.jsoup/jsoup"/></a>|
|alibaba - testable-mock|[testable-mock](https://alibaba.github.io/testable-mock/#/)|[Github](https://github.com/alibaba/testable-mock)|<a target="_blank" href="https://search.maven.org/search?q=g:%22com.alibaba.testable%22%20AND%20a:%22testable-allp%22"><img src="https://img.shields.io/maven-central/v/com.alibaba.testable/testable-all"/></a>|




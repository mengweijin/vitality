# QuickBoot
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

## QuickBoot 版本
| 模块                      | 最新版本                                                                                                                                                                                                                                    |
|:------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| quickboot-parent        | <a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.mengweijin%22%20AND%20a:%22quickboot-parent%22"><img src="https://img.shields.io/maven-central/v/com.github.mengweijin/quickboot-parent"/></a>               |
| quickboot-framework     | <a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.mengweijin%22%20AND%20a:%22quickboot-framework%22"><img src="https://img.shields.io/maven-central/v/com.github.mengweijin/quickboot-framework"/></a>         |
| quickboot-mybatis-plus  | <a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.mengweijin%22%20AND%20a:%22quickboot-mybatis-plus%22"><img src="https://img.shields.io/maven-central/v/com.github.mengweijin/quickboot-mybatis-plus"/></a>   |
| quickboot-jpa           | <a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.mengweijin%22%20AND%20a:%22quickboot-jpa%22"><img src="https://img.shields.io/maven-central/v/com.github.mengweijin/quickboot-jpa"/></a>                     |
| quickboot-cache-expired | <a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.mengweijin%22%20AND%20a:%22quickboot-cache-expired%22"><img src="https://img.shields.io/maven-central/v/com.github.mengweijin/quickboot-cache-expired"/></a> |
| quickboot-layui         | <a target="_blank" href="https://search.maven.org/search?q=g:%22com.github.mengweijin%22%20AND%20a:%22quickboot-layui%22"><img src="https://img.shields.io/maven-central/v/com.github.mengweijin/quickboot-layui"/></a>                 |

## 介绍
快速搭建 SpringBoot 项目，整合和配置常用的模块功能，节省搭建项目工程的时间。

### 使用 quickboot-mybatis-plus
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
      <artifactId>quickboot-mybatis-plus</artifactId>
    </dependency>
  </dependencies>
</project>
```

#### 配置加密
SafetyEncryptEnvironmentPostProcessor 可以实现配置文件敏感信息的加密配置。比如：数据库密码等信息。 使用方式：

- 生成 16 位随机 AES 密钥。
  - 在启动 jar 时把下面生成的 key 通过命令行参数 --cipher.key={randomKey} 传递到应用程序中的 Jar 启动参数。
  - idea 设置 Program arguments , 服务器可以设置为启动环境变量
  - 示例：--cipher.key==d1104d7c3b616f0b
  - String randomKey = AESUtils.generateRandomKey();
- 密钥加密。
  - 配置在 application.yaml 中的加密值
  - String encrypt = AESUtils.encryptByKey(randomKey, password);
- YML 配置。
  - 加密配置 {cipher} 开头紧接加密内容（ 非数据库配置专用 YML 中其它配置也是可以使用的 ）
  - spring.datasource.username='{cipher}Xb+EgsyuYRXw7U7sBJjBpA=='
  - spring.datasource.password='{cipher}Hzy5iliJbwDHhjLs1L0j6w=='
  
#### cors 跨域
跨域配置。根据规则在application.yml中配置：
~~~~yaml
quickboot:
  cors:
    # 是否启用自动配置，默认 false。如果为 false, 则默认采用 SpringBoot 规则（不能跨域请求）；
    enabled: true
~~~~

#### xss
XSS 过滤配置。根据规则在application.yml中配置：依赖 Jsoup。
~~~~yaml
quickboot:
  xss:
    # 是否启用 xss 过滤，默认 false
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

#### p6spy 数据库查询日志记录
自动记录每一条真实查询的 SQL 记录到 debug 日志中。

#### SpringDoc OpenAPI（Swagger）
启动服务后访问 url: http://localhost:8080/swagger-ui/index.html

#### AOP debug 级别，请求、响应参数详情等日志记录
仅当 spring.profiles.active 中配置了 !prod 才启用。如：spring.profiles.active=quickboot,dev 不包含 prod, 就会启用

#### flyway
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

#### 分页 IPage & Pager
~~~java
@GetMapping("/page")
public IPage<User> getPage(Page<User> page){
    return userService.page(page);
}

@GetMapping("/pager")
public Pager<User> getPager(Pager<User> pager) {
final IPage<User> page = userService.page(pager.toPage());
    return pager.toPager(page);
}
~~~


### quickboot-jpa
集成 Sping Data JPA. 提供如下功能类：
- BaseJpaRepository.java
- BaseServiceImpl.java

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
        <artifactId>quickboot-jpa</artifactId>
      </dependency>
    </dependencies>
</project>
~~~~

#### BaseJpaRepository 和 BaseService
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
#### ID Generator
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


### quickboot-cache-expired
启用缓存注解 @EnableCaching. SpringBoot 默认会根据引入的依赖实例化了一个 CacheManager：

SpringBoot 原生的 @Cacheable/@CachePut 没有缓存过期的处理。

这里额外提供一个 @CacheExpired 注解，配合 @Cacheable/@CachePut 使用，提供缓存过期注解。
```xml
<project>
    <dependencies>
      <dependency>
        <groupId>com.github.mengweijin</groupId>
        <artifactId>quickboot-cache-expired</artifactId>
        <version>Latest Version</version>
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

### quickboot-layui
- 集成 Thymeleaf，Layui，扩展了一个 Layui 的 quickboot 模块。
- 全局 jquery 配置。
- 添加 Ajax 的 put，delete 方法到 jquery。

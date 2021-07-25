# quickboot-jpa

## 介绍

springboot 2.0+, JPA

## 使用

### 配置

如果不配置下面这个，默认 basePackages = com。因此，必须保证配置的包路径和你项目中使用的包路径一直。 如果不一致，可能导致无法实例化项目中的 *Repository.java 的实例。

~~~yaml
quickboot:
  jpa:
    basePackages:
      - com.github.mengweijin
~~~

### Maven 引入:

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
            <artifactId>quickboot-mybatis-jpa</artifactId>
        </dependency>
    </dependencies>
</project>
~~~

## 功能

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

~~~java
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
~~~



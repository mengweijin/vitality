# quickboot-mybatis-plus

## 介绍

springboot 2.0+, Mybatis-plus

## 使用

### 配置

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

### Maven
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
            <artifactId>quickboot-mybatis-mybatis-plus</artifactId>
        </dependency>
    </dependencies>
</project>
~~~

## 功能
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



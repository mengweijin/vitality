package com.github.mengweijin.vitality.framework.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.github.mengweijin.vitality.framework.mybatis.data.permission.DefaultDataPermissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Meng Wei Jin
 **/
@Configuration
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter({MybatisPlusAutoConfiguration.class})
public class MybatisPlusConfig {

    @Autowired
    private DataSourceProperties dataSourceProperties;
    /**
     * map-underscore-to-camel-case: true。
     * 对JavaBean中属性开启自动驼峰命名规则（camel case）映射，
     * 默认对返回类型为Map的对象的key不起作用，所以需要自定义 MybatisMapWrapperFactory 类来处理
     *
     */
    @Bean
    @ConditionalOnMissingBean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
        };
    }

    /**
     * 注解：@InterceptorIgnore {@link com.baomidou.mybatisplus.annotation.InterceptorIgnore}
     * 用于 Mapper.java 的类或方法上。哪个配置为 true, 哪个拦截器就不会生效。常用于对某个方法或类忽略指定的拦截器。
     *
     * 目前已有的功能:
     *
     * 自动分页: PaginationInnerInterceptor
     * 多租户: TenantLineInnerInterceptor
     * 动态表名: DynamicTableNameInnerInterceptor
     * 乐观锁: OptimisticLockerInnerInterceptor
     * sql性能规范: IllegalSQLInnerInterceptor
     * 防止全表更新与删除: BlockAttackInnerInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 注意：插件有一定顺序，不能乱放。
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据权限插件
        interceptor.addInnerInterceptor(dataPermissionInterceptor());
        // Left join SYS_USER 查询用户昵称插件（自定义）
        // interceptor.addInnerInterceptor(new SysUserNameInnerInterceptor());
        // 分页插件, 一缓和二缓遵循mybatis的规则
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件。注解实体字段 @Version
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 如果子工程不使用 DataSourceProperties 中配置的数据库（如使用自定义配置的多数据源的情况下），
     * 只需在子工程中实例化一个 PaginationInnerInterceptor Bean，指定具体的 DbType 即可.
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInnerInterceptor paginationInnerInterceptor(){
        DbType dbType = JdbcUtils.getDbType(dataSourceProperties.getUrl());
        return new PaginationInnerInterceptor(dbType);
    }

    @Bean
    @ConditionalOnMissingBean
    public DataPermissionInterceptor dataPermissionInterceptor(){
        return new DataPermissionInterceptor(dataPermissionHandler());
    }

    /**
     * 如果需要数据权限拦截器，只需要子工程重新按照自己的业务要求来实现 BaseDataPermissionHandler.java。
     * 这里的默认实现只是给个参考。
     */
    @Bean
    @ConditionalOnMissingBean
    public DataPermissionHandler dataPermissionHandler(){
        return new DefaultDataPermissionHandler();
    }

    /**
     * 自动填充 BaseEntityMetaObjectHandler
     */
    @Bean
    @ConditionalOnMissingBean
    public MetaObjectHandler metaObjectHandler() {
        return new BaseEntityMetaObjectHandler();
    }

}

package com.github.mengweijin.quickboot.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.github.mengweijin.quickboot.mybatis.page.PageArgumentResolver;
import com.github.mengweijin.quickboot.mybatis.page.PageResponseBodyAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

/**
 * @author Meng Wei Jin
 **/
@Configuration
@AutoConfigureAfter({MybatisPlusAutoConfiguration.class})
public class QuickBootMybatisPlusAutoConfiguration implements WebMvcConfigurer {

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
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
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

    /**
     * 自动填充
     * BaseEntityMetaObjectHandler
     * 可以使用 BaseEntityMetaObjectHandler 来填充创建时间，修改时间。
     * 或者使用 BaseCreatorEntityMetaObjectHandler 来填充创建时间，修改时间，创建人、修改人。
     *
     * return new BaseCreatorEntityMetaObjectHandler(ServletUtils.SESSION_USER);
     *
     */
    @Bean
    @ConditionalOnMissingBean
    public MetaObjectHandler metaObjectHandler() {
        return new BaseEntityMetaObjectHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public PageResponseBodyAdvice pageResponseBodyAdvice(){
        return new PageResponseBodyAdvice();
    }

    /**
     * 注册参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageArgumentResolver());
    }
}
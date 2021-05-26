package com.github.mengweijin.quickboot.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.github.mengweijin.quickboot.framework.web.PageArgumentResolver;
import com.github.mengweijin.quickboot.mybatis.page.MyBatisPlusPageArgumentResolver;
import com.github.mengweijin.quickboot.mybatis.page.PageResponseBodyAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Meng Wei Jin
 * @description
 **/
@EnableTransactionManagement
@Configuration
public class QuickBootMybatisPlusAutoConfiguration {

    /**
     * map-underscore-to-camel-case: true。
     * 对JavaBean中属性开启自动驼峰命名规则（camel case）映射，
     * 默认对返回类型为Map的对象的key不起作用，所以需要自定义 MybatisMapWrapperFactory 类来处理
     *
     * @return
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
     * 如果子工程不使用 H2 数据库，只需在子工程中实例化一个PaginationInnerInterceptor Bean.
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInnerInterceptor paginationInnerInterceptor(){
        return new PaginationInnerInterceptor(DbType.H2);
    }

    /**
     * 自动填充
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MetaObjectHandler metaObjectHandler() {
        return new DefaultMetaObjectHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public PageArgumentResolver pagerArgumentResolver(){
        return new MyBatisPlusPageArgumentResolver();
    }

    @Bean
    @ConditionalOnMissingBean
    public PageResponseBodyAdvice pageResponseBodyAdvice(){
        return new PageResponseBodyAdvice();
    }
}

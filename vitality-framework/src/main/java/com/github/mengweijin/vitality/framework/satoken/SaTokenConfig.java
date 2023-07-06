package com.github.mengweijin.vitality.framework.satoken;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mengweijin
 * @date 2023/7/2
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 指定一条 match 规则
            SaRouter.match("/**")               // 拦截的 path 列表，可以写多个 */
                    .notMatch("/vitality/**", "/")
                    .notMatch("/login")         // 排除掉的 path 列表，可以写多个
                    .check(r -> StpUtil.checkLogin());    // 要执行的校验动作，可以写完整的 lambda 表达式

            // 根据路由划分模块，不同模块不同鉴权
            //SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
            //SaRouter.match("/admin/**", r -> StpUtil.checkPermission("admin"));
        })).addPathPatterns("/**");
    }

    @Bean
    public StpInterface stpInterface() {
        return new StpInterfaceImpl();
    }

    @Bean
    public SaTokenListener saTokenListener() {
        return new SaTokenListenerImpl();
    }
}

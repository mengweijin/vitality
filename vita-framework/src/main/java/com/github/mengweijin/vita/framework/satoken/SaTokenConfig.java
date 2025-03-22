package com.github.mengweijin.vita.framework.satoken;

import cn.dev33.satoken.filter.SaPathCheckFilterForJakartaServlet;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaFirewallStrategy;
import com.github.mengweijin.vita.framework.exception.ServerException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.IOException;

/**
 * 注册拦截器
 *
 * @author mengweijin
 * @since 2023/7/2
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer, InitializingBean {

    /**
     * 注册sa-token的拦截器，打开注解式鉴权功能
     * // 指定一条 match 规则。拦截的 path 列表，可以写多个
     * SaRouter.match("/**")
     * // 要执行的校验动作，可以写完整的 lambda 表达式
     * .check(r -> StpUtil.checkLogin());
     * <p>
     * knife4j:
     *      "/doc.html", "/webjars/**", "/v3/api-docs/**"
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                    SaRouter.match("/**").check(r -> StpUtil.checkLogin());
                    // 根据路由划分模块，不同模块不同鉴权
                    //SaRouter.match("/doc.html", r -> StpUtil.checkPermission("tool:api:view"));
                }))
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/vita/**", "/doc.html", "/webjars/**", "/v3/api-docs/**");
    }

    /**
     * 自定义当请求 path 校验不通过时 sa-token 处理方案。
     * Version <= 1.39.0：SaStrategy.instance.requestPathInvalidHandle
     * Version >= 1.40.0：SaFirewallStrategy.instance.requestPathInvalidHandle
     *
     * 比如访问：localhost:8080/.xxx
     * 上面这个 url 包含了 .xxx 这样包含小数点的非常规 url，很多漏洞扫描工具（比如：AppScan）会模拟类似的 url 对应用进行扫描。
     * sa-token 默认的处理方式会返回 http 状态为 200 和 “非法请求” 的文字提示，而漏洞扫描工具要求返回 400、500 这样的错误状态码。
     * 因此通过自定义修改 SaStrategy.instance.requestPathInvalidHandle 的处理默认处理方式来规避。
     * 这里直接 throw new RuntimeException(e);
     * {@link SaPathCheckFilterForJakartaServlet } 在处理的时候就会抛运行时异常，客户端收到的就是 500 的异常。
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        SaFirewallStrategy.instance.requestPathInvalidHandle = (e, requestObject, responseObject) -> {
            if(responseObject instanceof HttpServletResponse response) {
                try {
                    response.setContentType("text/plain; charset=utf-8");
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.getWriter().print(e.getMessage());
                    response.getWriter().flush();
                } catch (IOException ex) {
                    throw new ServerException(ex);
                }
            }
        };
    }
}

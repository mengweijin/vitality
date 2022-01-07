package com.github.mengweijin.quickboot.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author mengweijin
 * @date 2022/1/2
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailServiceImpl;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    /**
     * 认证输入的用户名和密码匹配
     * @param auth AuthenticationManagerBuilder
     * @throws Exception Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService (userDetailServiceImpl).passwordEncoder(passwordEncoder());
    }

    /**
     * Spring Security能够防止主体对同一应用程序进行超过指定次数的并发身份验证。
     * 而网络管理员喜欢这个特性，因为它有助于防止人们共享登录名。
     * 例如，您可以阻止用户“Batman”从两个不同的会话登录到web应用程序。
     * 您可以终止他们以前的登录，也可以在他们试图再次登录时报告错误，从而阻止第二次登录。
     * 注意，如果使用第二种方法，没有显式注销的用户(例如，刚刚关闭浏览器的用户)将无法再次登录，直到其原始会话过期。
     * 所以，一般情况下，使用第一种方式即可。
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> { request
            .antMatchers("/h2/**").hasRole("DBA")
            .antMatchers("/actuator/**").hasRole("actuator")
            .antMatchers("/swagger-ui/**").hasRole("swagger")
            .antMatchers("/login").permitAll()
            // 其他任何请求,登录后才可以访问
            //.anyRequest().authenticated();
            .anyRequest().permitAll();
        });

        // 前后端分离项目，禁用 csrf。采用 jwt token 验证，不存在 csrf 的问题了。
        http.csrf().disable();

        http.headers(header -> { header
            // iframe嵌入网页(如：使用layer弹层时)，security默认使用deny策略，浏览器报错x-frame-options deny
            // 修改为SAMEORIGIN，页面只能显示在与页面本身相同的原点的框架中
            .frameOptions().sameOrigin()
            // 开启xss过滤
            .xssProtection().xssProtectionEnabled(true);
        });

        http.formLogin().permitAll();

        http.logout(logout -> { logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            // 登出成功后处理，记录登出日志
            .logoutSuccessHandler(logoutSuccessHandler);
        });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 解决静态资源被拦截的问题 /**表示可以跨文件夹
        web.ignoring().antMatchers("/static/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

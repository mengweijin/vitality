package com.github.mengweijin.vitality;

import com.github.mengweijin.vitality.filter.xss.XssProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.List;

/**
 * 加@Validated可对配置属性进行数据格式校验
 * havingValue: 可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
 * matchIfMissing: 缺少该property时是否可以加载。如果为true，没有该property也会正常加载；反之报错
 *
 * ConfigurationProperties 注解主要用来把properties配置文件转化为bean来使用。
 * VitalityAutoConfiguration 中的 EnableConfigurationProperties 注解的作用是使@ConfigurationProperties注解生效。
 * 如果只配置@ConfigurationProperties注解，在IOC容器中是获取不到properties配置文件转化的bean的。
 * @author Meng Wei Jin
 **/
@ConfigurationProperties(prefix = "vitality")
@Data
@Validated
public class VitalityProperties {

    private XssProperties xss = new XssProperties();

    private boolean allowedCORS = false;

    private boolean debug = false;

    private List<String> bodyAdviceExcludePathPrefix = new ArrayList<>();

    /** session/shiro/spring security 等认证框架上下文中存储的当前登录用户变量名名称 */
    private String loginUserIdKeyName = "LOGIN_USER_ID";
}

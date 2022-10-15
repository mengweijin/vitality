package com.github.mengweijin.quickboot;

import com.github.mengweijin.quickboot.filter.xss.XssProperties;
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
 * QuickBootFrameworkAutoConfiguration 中的 EnableConfigurationProperties 注解的作用是使@ConfigurationProperties注解生效。
 * 如果只配置@ConfigurationProperties注解，在IOC容器中是获取不到properties配置文件转化的bean的。
 * @author Meng Wei Jin
 **/
@ConfigurationProperties(prefix = "quickboot")
@Data
@Validated
public class QuickBootProperties {

    private XssProperties xss = new XssProperties();

    private boolean cors = false;

    private boolean debug = false;

    private List<String> bodyAdviceExcludePathPrefix = new ArrayList<>();
}

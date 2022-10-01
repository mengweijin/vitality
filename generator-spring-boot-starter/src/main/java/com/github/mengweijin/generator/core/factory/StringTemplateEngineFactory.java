package com.github.mengweijin.generator.core.factory;

import cn.hutool.core.io.FileUtil;
import org.thymeleaf.context.Context;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/9/3
 */
public class StringTemplateEngineFactory {

    private static SpringTemplateEngine textTemplateEngine;

    public static SpringTemplateEngine getTextTemplateEngine() {
        if (textTemplateEngine != null) {
            return textTemplateEngine;
        }
        //字符串模板引擎对象
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        //内置方言
        IDialect springStandardDialect = new SpringStandardDialect();
        springTemplateEngine.setDialect(springStandardDialect);
        //字符串解析器
        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        //使用缓存
        stringTemplateResolver.setCacheable(true);
        stringTemplateResolver.setTemplateMode(TemplateMode.TEXT);
        springTemplateEngine.setTemplateResolver(stringTemplateResolver);
        return springTemplateEngine;
    }

    public static String process(String templateContent, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        SpringTemplateEngine engine = getTextTemplateEngine();
        return engine.process(templateContent, context);
    }

    public static String process(File templateFile, Map<String, Object> variables) {
        String templateContent = FileUtil.readString(templateFile, StandardCharsets.UTF_8);
        return process(templateContent, variables);
    }

}

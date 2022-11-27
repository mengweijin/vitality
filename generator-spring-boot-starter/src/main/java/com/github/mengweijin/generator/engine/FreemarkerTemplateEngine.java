package com.github.mengweijin.generator.engine;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;

/**
 * @author mengweijin
 * @date 2022/11/6
 */
public class FreemarkerTemplateEngine {

    /**
     * 配置 freemarker configuration
     *
     * @return
     */
    private static Configuration configuration() {
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(ConstVal.UTF8);
        return configuration;
    }

    /**
     *
     * @param templateName "entity.java.ftl"
     * @param templateContent "content"
     * @param args args
     */
    public static String process(String templateName, String templateContent, Object args) {
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate(templateName, templateContent);

        Configuration configuration = configuration();
        configuration.setTemplateLoader(templateLoader);

        try(StringWriter writer = new StringWriter()) {
            Template template = configuration.getTemplate(templateName);
            template.process(args, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

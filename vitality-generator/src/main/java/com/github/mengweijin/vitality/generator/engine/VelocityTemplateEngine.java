package com.github.mengweijin.vitality.generator.engine;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.vitality.generator.dto.GeneratorArgs;
import com.github.mengweijin.vitality.generator.util.GeneratorUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.date.DatePattern;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.lang.tuple.Pair;
import org.dromara.hutool.core.text.StrUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 */
@Slf4j
@Getter
public abstract class VelocityTemplateEngine {

    private final VelocityEngine velocityEngine;

    private GeneratorArgs generatorArgs;

    private TableInfo tableInfo;

    protected abstract VelocityEngine getVelocityEngine(String templateDir);

    protected abstract List<String> getTemplates();

    public VelocityTemplateEngine() {
        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.init();
    }

    public Pair<String, String> writeString(String fileName, String templateContent, GeneratorArgs args, TableInfo tableInfo) {
        return evaluate(fileName, templateContent, getObjectMap(args, tableInfo));
    }

    protected Pair<String, String> evaluate(String fileName, String templateContent, Map<String, Object> args) {
        try (StringWriter writer = new StringWriter()) {
            VelocityContext context = new VelocityContext(args);
            velocityEngine.evaluate(context, writer, fileName, templateContent);
            return Pair.of(fileName, writer.toString());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> getObjectMap(GeneratorArgs args, TableInfo tableInfo) {
        List<String> baseEntityColumns = GeneratorUtils.resolveBaseEntityColumns(args);
        String entityName = GeneratorUtils.resolveEntityName(tableInfo.getName(), args);
        List<TableField> entityFields = GeneratorUtils.resolveEntityFields(tableInfo, baseEntityColumns);
        List<TableField> commonFields = GeneratorUtils.resolveCommonFields(tableInfo, baseEntityColumns);

        List<String> entityColumns = GeneratorUtils.resolveEntityColumns(entityFields);
        List<String> commonColumns = GeneratorUtils.resolveCommonColumns(commonFields);

        String requestMapping = "/" + StrUtil.toSymbolCase(entityName, '-');
        if (StrUtil.isNotBlank(args.getModuleName())) {
            requestMapping = StrUtil.addPrefixIfNot(args.getModuleName(), "/") + requestMapping;
        }

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("module", args.getModuleName());
        objectMap.put("package", args.getPackages());
        objectMap.put("author", args.getAuthor());
        objectMap.put("date", DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATE_PATTERN));
        objectMap.put("baseEntity", args.getBaseEntity());
        objectMap.put("baseEntityPackage", StrUtil.subBefore(args.getBaseEntity(), ".", true));
        objectMap.put("baseEntityName", StrUtil.subAfter(args.getBaseEntity(), ".", true));
        objectMap.put("baseEntityColumns", baseEntityColumns);
        objectMap.put("table", tableInfo);
        objectMap.put("idField", GeneratorUtils.getIdField(tableInfo));
        objectMap.put("entityName", entityName);
        objectMap.put("entityPropertyName", StrUtil.lowerFirst(entityName));
        objectMap.put("entityFields", entityFields);
        objectMap.put("commonFields", commonFields);
        objectMap.put("allFields", CollUtil.addAll(new ArrayList<>(entityFields), new ArrayList<>(commonFields)));
        objectMap.put("entityColumns", entityColumns);
        objectMap.put("commonColumns", commonColumns);
        objectMap.put("allColumns", CollUtil.addAll(new ArrayList<>(entityColumns), new ArrayList<>(commonColumns)));
        objectMap.put("requestMapping", requestMapping);

        objectMap.put("hutoolStrUtil", StrUtil.class);
        return objectMap;
    }

}

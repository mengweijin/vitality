package com.github.mengweijin.vita.generator.util;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.mengweijin.vita.generator.domain.dto.GeneratorArgs;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.util.PropertyPlaceholderHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 */
@Slf4j
public class GeneratorUtils {

    public static final PropertyPlaceholderHelper PLACEHOLDER_HELPER = new PropertyPlaceholderHelper("${", "}");

    /**
     * If the user configured superEntityColumns, the configuration will prevail;
     * if not, the default configuration of superEntityColumns will be generated according to the superEntityClass.
     *
     * @return String
     */
    public static List<String> resolveBaseEntityColumns(GeneratorArgs generatorArgs) {
        String baseEntity = generatorArgs.getBaseEntity();
        if (StrUtil.isBlank(baseEntity)) {
            return new ArrayList<>();
        }
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> cls = Class.forName(baseEntity, true, classLoader);
            Field[] declaredFields = FieldUtil.getFieldsDirectly(cls, true);
            return Arrays.stream(declaredFields).map(field -> StrUtil.toUnderlineCase(field.getName()).toUpperCase()).collect(Collectors.toList());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static String[] trimItems(String[] items) {
        if (items == null) {
            return new String[]{};
        }
        return Arrays.stream(items).map(String::trim).toArray(String[]::new);
    }

    public static String[] parseTablePrefix(String str) {
        if (StrUtil.isBlank(str)) {
            return new String[]{};
        }
        return trimItems(str.split("[,，;； ]"));
    }

    public static String resolveEntityName(String tableName, String[] tablePrefix) {
        String val = tableName;
        if (tablePrefix != null) {
            for (String prefix : tablePrefix) {
                if (tableName.toLowerCase().startsWith(prefix.toLowerCase())) {
                    val = tableName.substring(prefix.length());
                    break;
                }
            }
        }
        return StrUtil.upperFirst(StrUtil.toCamelCase(val.toLowerCase()));
    }

    public static List<String> resolveCommonColumns(List<TableField> commonFields) {
        return commonFields.stream().map(tableField -> tableField.getColumnName().toUpperCase()).collect(Collectors.toList());
    }

    public static List<String> resolveEntityColumns(List<TableField> entityFields) {
        return entityFields.stream().map(tableField -> tableField.getColumnName().toUpperCase()).collect(Collectors.toList());
    }

    public static List<TableField> resolveCommonFields(TableInfo tableInfo, List<String> baseEntityColumns) {
        return tableInfo.getFields().stream().filter(tableField -> baseEntityColumns.contains(tableField.getColumnName().toUpperCase())).collect(Collectors.toList());
    }

    public static List<TableField> resolveEntityFields(TableInfo tableInfo, List<String> baseEntityColumns) {
        return tableInfo.getFields().stream().filter(tableField -> !baseEntityColumns.contains(tableField.getColumnName().toUpperCase())).collect(Collectors.toList());
    }

    public static TableField getIdField(TableInfo tableInfo) {
        return tableInfo.getFields().stream().filter(TableField::isKeyFlag).findFirst().orElse(null);
    }

    public static String getPackages(String packages, String moduleName) {
        if (StrUtil.isBlank(moduleName)) {
            return packages;
        }
        return String.join(".", packages, moduleName);
    }

    public static String replaceTemplateString(String templateString, Map<String, Object> objectMap) {
        Properties props = new Properties();
        objectMap.forEach((k, v) -> props.setProperty(k, StrUtil.toString(v)));
        return PLACEHOLDER_HELPER.replacePlaceholders(templateString, props);
    }

}

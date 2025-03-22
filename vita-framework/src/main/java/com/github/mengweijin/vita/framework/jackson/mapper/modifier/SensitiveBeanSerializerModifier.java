package com.github.mengweijin.vita.framework.jackson.mapper.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.type.MapType;
import com.github.mengweijin.vita.framework.jackson.mapper.SensitiveObjectMapper;
import com.github.mengweijin.vita.framework.jackson.mapper.serializer.SensitiveBeanFieldSerializer;
import com.github.mengweijin.vita.framework.jackson.mapper.serializer.SensitiveMapSerializer;
import org.dromara.hutool.core.text.StrUtil;

import java.util.List;

/**
 * Bean 序列化修改器
 *
 * @author mengweijin
 */
public class SensitiveBeanSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter writer : beanProperties) {
            if (writer.getType().isTypeOrSubTypeOf(String.class) && StrUtil.containsAnyIgnoreCase(writer.getName(), SensitiveObjectMapper.SENSITIVE_KEY)) {
                // 如果是字符串，并且属性名称属于敏感字段名，则使用自定义处理
                writer.assignSerializer(new SensitiveBeanFieldSerializer());
            }
        }
        return beanProperties;
    }

    @Override
    public JsonSerializer<?> modifyMapSerializer(SerializationConfig config,
                                                 MapType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        return new SensitiveMapSerializer((MapSerializer) serializer, null, false);
    }

}

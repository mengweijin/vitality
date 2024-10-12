package com.github.mengweijin.vitality.framework.jackson.util;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.dromara.hutool.core.text.StrUtil;

import java.util.List;

/**
 * Bean 序列化修改器
 *
 * @author mengweijin
 */
public class SensitiveFieldBeanSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter writer : beanProperties) {
            if (writer.getType().isTypeOrSubTypeOf(String.class) && StrUtil.containsAnyIgnoreCase(writer.getName(), JacksonBeanSensitiveUtils.SENSITIVE_KEY)) {
                // 如果是字符串，并且属性名称属于敏感字段名，则使用自定义处理
                writer.assignSerializer(new SensitiveBeanFieldSerializer());
            }
        }
        return beanProperties;
    }

}

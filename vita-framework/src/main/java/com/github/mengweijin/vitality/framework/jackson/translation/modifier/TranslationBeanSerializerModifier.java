package com.github.mengweijin.vitality.framework.jackson.translation.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.github.mengweijin.vitality.framework.jackson.translation.Translation;
import com.github.mengweijin.vitality.framework.jackson.translation.serializer.TranslationSerializer;

import java.util.List;

/**
 * Bean 序列化修改器
 * <p>
 * Jackson 中 属性值为 null 值时优先由 {@link NullSerializer}，这时候不走自定义的 {@link TranslationSerializer}。
 * 当属性为其他字段映射过来的字段时（即 {@link Translation} 中指定了 field 字段时），就达不到翻译数据的目的。
 * 因此，需要将 null 值也交给自定义的 {@link TranslationSerializer} 来处理才行。
 *
 * @author mengweijin
 */
public class TranslationBeanSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties) {
        for (BeanPropertyWriter writer : beanProperties) {
            if (writer.getSerializer() instanceof TranslationSerializer serializer) {
                writer.assignNullSerializer(serializer);
            }
        }
        return beanProperties;
    }

}

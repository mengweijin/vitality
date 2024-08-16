package com.github.mengweijin.framework.jackson.translation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.github.mengweijin.framework.jackson.translation.strategy.ITranslationStrategy;
import com.github.mengweijin.framework.util.ReflectUtils;
import org.dromara.hutool.core.text.StrUtil;
import java.io.IOException;
import java.util.Objects;

/**
 * @author mengweijin
 */
public class TranslationSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private Translation translation;

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ITranslationStrategy<?> translationStrategy = TranslationFactory.getTranslation(translation.translateType());
        if(translationStrategy == null) {
            gen.writeObject(value);
        } else {
            // 优先取配置的映射字段属性值
            if (StrUtil.isNotBlank(translation.field())) {
                value = ReflectUtils.invokeGetter(gen.currentValue(), translation.field());
            }
            // 如果 value 为 null 就不用翻译了
            if (value == null) {
                gen.writeNull();
                return;
            }
            Object result = translationStrategy.translation(value, translation);
            gen.writeObject(result);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Translation translation = property.getAnnotation(Translation.class);
        if (Objects.nonNull(translation)) {
            this.translation = translation;
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}

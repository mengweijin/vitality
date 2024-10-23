package com.github.mengweijin.vitality.framework.jackson.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.mengweijin.vitality.framework.jackson.util.SensitiveObjectMapper;
import org.dromara.hutool.core.text.StrUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author mengweijin
 */
public class SensitiveMapSerializer extends MapSerializer {

    private final static JavaType KEY_TYPE = TypeFactory.defaultInstance().constructSimpleType(String.class, null);
    private final static JavaType VALUE_TYPE = KEY_TYPE;

    private final static TypeSerializer VTS = null;

    private final static JsonSerializer<?> KEY_SERIALIZER = new StringSerializer();
    private final static JsonSerializer<?> VALUE_SERIALIZER = new StringSerializer();

    public SensitiveMapSerializer() {
        super(null, null, KEY_TYPE, VALUE_TYPE, false, VTS, KEY_SERIALIZER, VALUE_SERIALIZER);
    }

    public SensitiveMapSerializer(MapSerializer src, Object filterId, boolean sortKeys) {
        super(src, filterId, sortKeys);
    }

    public SensitiveMapSerializer(SensitiveMapSerializer sensitiveMapSerializer, BeanProperty property, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer, Set<String> ignored, Set<String> included) {
        super(sensitiveMapSerializer, property, keySerializer, valueSerializer, ignored, included);
    }

    @Override
    public void serialize(Map<?, ?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        super.serialize(this.processSensitive(value), gen, provider);
    }

    @Override
    public MapSerializer withResolved(BeanProperty property,
                                      JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer,
                                      Set<String> ignored, Set<String> included, boolean sortKeys) {
        // _ensureOverride("withResolved");
        SensitiveMapSerializer ser = new SensitiveMapSerializer(this, property, keySerializer, valueSerializer, ignored, included);
        if (sortKeys != ser._sortKeys) {
            ser = new SensitiveMapSerializer(ser, _filterId, sortKeys);
        }
        return ser;
    }

    private Map<?, ?> processSensitive(Map<?, ?> map) {
        Map<Object, Object> result = new HashMap<>();
        map.forEach((k, v) -> {
            if (v instanceof CharSequence && StrUtil.containsAnyIgnoreCase(StrUtil.toString(k), SensitiveObjectMapper.SENSITIVE_KEY)) {
                result.put(k, SensitiveObjectMapper.HIDE_VALUE);
            } else {
                result.put(k, v);
            }
        });
        return result;
    }
}

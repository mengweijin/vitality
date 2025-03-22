package com.github.mengweijin.vita.framework.jackson.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.jackson.JacksonConfig;
import com.github.mengweijin.vita.framework.jackson.mapper.modifier.SensitiveBeanSerializerModifier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

/**
 * @author mengweijin
 * @since 2022/5/17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SensitiveObjectMapper {

    public static final String[] SENSITIVE_KEY = new String[]{"password", "pwd", "token"};

    public static final String HIDE_VALUE = "********";

    private volatile static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            synchronized (SensitiveObjectMapper.class) {
                if (objectMapper == null) {
                    ObjectMapper mapper = new ObjectMapper();
                    init(mapper);
                    objectMapper = mapper;
                }
            }
        }
        return objectMapper;
    }

    private static void init(ObjectMapper objectMapper) {
        //只序列化对象值不为 null 的属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.registerModule(JacksonConfig.javaTimeModule());

        // 重新设置 Jackson 的 Bean 序列化修改器
        SerializerFactory serializerFactory = objectMapper.getSerializerFactory().withSerializerModifier(new SensitiveBeanSerializerModifier());
        objectMapper.setSerializerFactory(serializerFactory);
    }

    public static String writeValueAsString(Object value) {
        try {
            return getObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new ServerException(e);
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return getObjectMapper().readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new ServerException(e);
        }
    }
}

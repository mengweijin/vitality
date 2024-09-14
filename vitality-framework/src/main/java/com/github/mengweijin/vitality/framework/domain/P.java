package com.github.mengweijin.vitality.framework.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.mengweijin.vitality.framework.jackson.BigNumberSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.extra.spring.SpringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author mengweijin
 * @since 2022/5/17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class P {

    public static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        //只序列化对象值不为 null 的属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.registerModule(javaTimeModule());
    }

    public static ObjectMapper objectMapper() {
        return SpringUtil.getBean(ObjectMapper.class);
    }

    public static String writeValueAsStringWithoutNull(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String writeValueAsString(Object value) {
        try {
            return objectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static JavaTimeModule javaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(Long.class, BigNumberSerializer.INSTANCE);
        javaTimeModule.addSerializer(Long.TYPE, BigNumberSerializer.INSTANCE);
        javaTimeModule.addSerializer(BigInteger.class, BigNumberSerializer.INSTANCE);
        javaTimeModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        return javaTimeModule;
    }
}

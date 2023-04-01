package com.github.mengweijin.vitality.framework.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.mengweijin.vitality.framework.redis.inteceptor.SameUrlDataInterceptor;
import com.github.mengweijin.vitality.framework.redis.limiter.RateLimiterAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mengweijin
 * @date 2022/1/1
 */
@Slf4j
@SuppressWarnings(value = {"rawtypes"})
@EnableCaching
@Configuration
@ConditionalOnClass({RedisConnectionFactory.class, org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class})
public class RedisConfig implements CachingConfigurer, WebMvcConfigurer {

    @Bean
    public RedisService redisService(RedisTemplate redisTemplate) {
        return new RedisService(redisTemplate);
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        log.info("Creating bean redisTemplate......");
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        Jackson2JsonRedisSerializer<?> serializer = new Jackson2JsonRedisSerializer<>(mapper, Object.class);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public DefaultRedisScript<Long> limitScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(limitScriptText());
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    /**
     * 限流脚本
     */
    private String limitScriptText() {
        return """
                    local key = KEYS[1]
                    local count = tonumber(ARGV[1])
                    local time = tonumber(ARGV[2])
                    local current = redis.call('get', key);
                    if current and tonumber(current) > count then
                        return tonumber(current);
                    end
                    current = redis.call('incr', key)
                    if tonumber(current) == 1 then
                        redis.call('expire', key, time)
                    end
                    return tonumber(current);
                """;


    }

    @Bean
    public RateLimiterAspect rateLimiterAspect() {
        return new RateLimiterAspect();
    }

    @Bean
    public SameUrlDataInterceptor sameUrlDataInterceptor(){
        return new SameUrlDataInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sameUrlDataInterceptor()).addPathPatterns("/**");
    }

}

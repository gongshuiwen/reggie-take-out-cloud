package org.example.reggie.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;

@Configuration
public class RedisConfig {

    /**
     * 配置 RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // Set key serializer
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);

        // Set value serializer
        RedisSerializer<Object> valueSerializer = redisValueSerializer();
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);

        return redisTemplate;
    }

    /**
     * 配置 RedisSerializer
     */
    @Bean
    public RedisSerializer<Object> redisValueSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapperForRedisValueSerializer());
    }

    /**
     * 配置 RedisSerializer 使用的 ObjectMapper 实例
     */
    private ObjectMapper objectMapperForRedisValueSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Visibility
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // Register modules from SecurityJackson2Modules
        // (already include com.fasterxml.jackson.datatype.jsr310.JavaTimeModule)
        objectMapper.registerModules(SecurityJackson2Modules.getModules(RedisConfig.class.getClassLoader()));
        return objectMapper;
    }
}

package org.example.reggie.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.example.reggie.common.security.MsgCodeAuthenticationToken;
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

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory,
            RedisSerializer<Object> valueSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // Set key serializer
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);

        // Set value serializer
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);

        return redisTemplate;
    }

    @Bean
    public RedisSerializer<Object> redisValueSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapperForRedisValueSerializer());
    }

    private ObjectMapper objectMapperForRedisValueSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Visibility
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Active Default Typing
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);

        // Register modules from SecurityJackson2Modules
        // (already include com.fasterxml.jackson.datatype.jsr310.JavaTimeModule)
        objectMapper.registerModules(SecurityJackson2Modules.getModules(RedisConfig.class.getClassLoader()));

        // Add mix-in annotations for MsgCodeAuthenticationToken
        Class<?> mixInClazz;
        try {
            mixInClazz = ClassLoader.getSystemClassLoader()
                    .loadClass("org.springframework.security.jackson2.UsernamePasswordAuthenticationTokenMixin");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        objectMapper.addMixIn(MsgCodeAuthenticationToken.class, mixInClazz);
        return objectMapper;
    }
}

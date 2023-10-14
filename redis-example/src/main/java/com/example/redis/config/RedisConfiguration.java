package com.example.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis 配置
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfiguration {

    /**
     * redis缓存管理器配置
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //设置不同cacheName的过期时间
        Map<String, RedisCacheConfiguration> configurations = new HashMap<>(16);
        // 序列化方式
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = getJsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> serializationPair = RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer);
        // 默认的缓存时间
        Duration defaultTtl = Duration.ofSeconds(20L);
        // 用户模块的缓存时间
        Duration userTtl = Duration.ofSeconds(50L);
        // 默认的缓存配置
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //.entryTtl(defaultTtl)
                .serializeValuesWith(serializationPair);
        // 自定义用户模块的缓存配置 自定义的配置可以覆盖默认配置（当前的模块）
        configurations.put("user_cache", RedisCacheConfiguration.defaultCacheConfig()
                //.entryTtl(userTtl)
                .serializeValuesWith(serializationPair));

        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(configurations)
                // 事物支持
                .transactionAware().build();
    }

    /**
     * 设置jackson的序列化方式
     *
     * @return
     */

    private Jackson2JsonRedisSerializer<Object> getJsonRedisSerializer() {
        //配置对象映射 解决查询缓存转换异常的问题
        Jackson2JsonRedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        redisSerializer.setObjectMapper(om);
        return redisSerializer;
    }


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = getJsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jsonRedisSerializer);
        // 支持事物
        //template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }


}
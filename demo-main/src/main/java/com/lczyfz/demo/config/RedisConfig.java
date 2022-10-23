package com.lczyfz.demo.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by maple on 2018-08-22.
 */
@Configuration
//@EnableCaching
public class RedisConfig {
    @Bean
    public CacheManager cacheManagerByRedis(RedisConnectionFactory redisConnectionFactory) {
        CacheManager cacheManager = RedisCacheManager.create(redisConnectionFactory);
        return cacheManager;
    }

    /**
     * 采用原生JdkSerializationRedisSerializer的方法
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(factory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
//        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);

//        redisTemplate.setDefaultSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.afterPropertiesSet();\

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }

//    @Bean(name = "redisUtils")
//    public RedisUtils redisUtils(RedisTemplate<String, Object> redisTemplate) {
//        return new RedisUtils(redisTemplate);
//    }
}

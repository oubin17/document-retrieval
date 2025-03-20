package com.odk.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * RedisConfig
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2025/3/20
 */
@Configuration
public class CustomerRedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(6380);
        redisStandaloneConfiguration.setPassword("123456");
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }
}



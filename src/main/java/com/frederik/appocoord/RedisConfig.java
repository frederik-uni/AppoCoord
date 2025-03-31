package com.frederik.appocoord;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;

@Configuration
public class RedisConfig {
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        String host = isRunningInDocker() ? "redis" : "localhost";
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, 6379));
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    private boolean isRunningInDocker() {
        return new File("/.dockerenv").exists() || "true".equalsIgnoreCase(System.getenv("RUNNING_IN_DOCKER"));
    }
}
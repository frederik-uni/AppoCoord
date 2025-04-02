package com.frederik.appocoord;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.UUID;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public String saveData(Serializable data) {
        String id = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(id, toJson(data));

        return id;
    }

    public <T> T getData(String id, Class<T> clazz) {
        String json = (String) redisTemplate.opsForValue().get(id);
        return fromJson(json, clazz);
    }

    public String createIf(String id, Serializable data) {
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(id))) {
            redisTemplate.opsForValue().set(id, toJson(data));
        }
        return id;
    }

    private String toJson(Serializable obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON to object", e);
        }
    }

    public String createOrUpdate(String id, Serializable data) {
        redisTemplate.opsForValue().set(id, toJson(data));
        return id;
    }
}

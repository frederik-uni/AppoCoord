package com.frederik.appocoord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String saveData(Object data) {
        String id = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(id, data);

        return id;
    }

    public Object getData(String id) {
        return redisTemplate.opsForValue().get(id);
    }

    public String createIf(String id, Object data) {
        if (!Boolean.TRUE.equals(redisTemplate.hasKey(id))) {
            redisTemplate.opsForValue().set(id, data);
        }
        return id;
    }

    public String createOrUpdate(String id, Object data) {
        redisTemplate.opsForValue().set(id, data);
        return id;
    }
}

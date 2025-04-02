package com.frederik.appocoord;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.frederik.appocoord.models.User;
import com.frederik.appocoord.models.UserInternal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@Service
public class RedisService {

    private final ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisService() {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(User.class, new UserSerializer());
        module.addDeserializer(User.class, new UserDeserializer());

        objectMapper.registerModule(module);
    }

    public String saveData(Serializable data) {
        String id = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(id, toJson(data));

        return id;
    }

    public <T> T getData(String id, Class<T> clazz) {
        String json = (String) redisTemplate.opsForValue().get(id);
        return fromJson(json, clazz);
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

    public static class UserSerializer extends JsonSerializer<User> {
        @Override
        public void serialize(User user, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            String id = SpringContext.getBean(RedisService.class).createOrUpdate(user.getFingerprint(), new UserInternal(user.getFingerprint(), user.getName(), user.getEmail()));
            gen.writeString(id);
        }
    }

    public static class UserDeserializer extends JsonDeserializer<User> {
        @Override
        public User deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
            String id = p.getText();
            UserInternal data = SpringContext.getBean(RedisService.class).getData(id, UserInternal.class);
            return new User(data.getFingerprint(), data.getName(), data.getEmail());
        }
    }
}

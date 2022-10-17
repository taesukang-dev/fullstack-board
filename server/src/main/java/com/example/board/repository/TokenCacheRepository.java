package com.example.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TokenCacheRepository {

    private final RedisTemplate<String, String> redisTokenTemplate;

    public void setToken(String key, String data) {
        ValueOperations<String, String> values = redisTokenTemplate.opsForValue();
        values.set(getKey(key), data);
    }

    public void setToken(String key, String date, Duration duration) {
        ValueOperations<String, String> values = redisTokenTemplate.opsForValue();
        values.set(getKey(key), date, duration);
    }

    public Optional<String> getToken(String key) {
        ValueOperations<String, String> values = redisTokenTemplate.opsForValue();
        return Optional.of(values.get(getKey(key)));
    }

    public void deleteToken(String key) {
        redisTokenTemplate.delete(getKey(key));
    }

    private String getKey(String key) {
        return "RTK:" + key;
    }
}

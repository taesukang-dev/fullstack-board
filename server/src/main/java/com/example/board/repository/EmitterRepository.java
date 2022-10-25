package com.example.board.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class EmitterRepository {
    private Map<String, SseEmitter> emitterMap = new HashMap<>();
    private Map<String, ChannelTopic> topics = new HashMap<>();

    public SseEmitter save(Long userId, SseEmitter sseEmitter) {
        String key = getKey(userId);
        emitterMap.put(key, sseEmitter);
        return sseEmitter;
    }

    public Optional<SseEmitter> get(Long userId) {
        String key = getKey(userId);
        return Optional.ofNullable(emitterMap.get(key));
    }

    public void delete(Long userId) {
        emitterMap.remove(getKey(userId));
    }

    private String getKey(Long userId) {
        return "Emitter:UID" + userId;
    }

    public ChannelTopic getTopic(Long userId) {
        return topics.get(getKey(userId));
    }

    public void putTopic(Long userId, ChannelTopic topic) {
        topics.put(getKey(userId), topic);
    }
}

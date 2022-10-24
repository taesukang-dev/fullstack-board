package com.example.board.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class EmitterRepository {
    private Map<String ,SseEmitter> emitterMap = new HashMap<>();

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
}
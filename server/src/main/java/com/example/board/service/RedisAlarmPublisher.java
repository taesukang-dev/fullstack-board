package com.example.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisAlarmPublisher {
    private final RedisTemplate<String, String> redisTokenTemplate;

    public void publish(ChannelTopic topic, Long userId) {
        redisTokenTemplate.convertAndSend(topic.getTopic(), userId.toString());
    }
}

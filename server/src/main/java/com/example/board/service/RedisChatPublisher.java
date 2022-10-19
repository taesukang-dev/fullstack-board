package com.example.board.service;

import com.example.board.dto.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisChatPublisher {
    private final RedisTemplate<String, Object> redisChatTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        redisChatTemplate.convertAndSend(topic.getTopic(), message);
    }
}
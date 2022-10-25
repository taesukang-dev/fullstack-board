package com.example.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisAlarmSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTokenTemplate;
    private final AlarmService alarmService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = redisTokenTemplate.getStringSerializer().deserialize(message.getBody());
            Long userId = objectMapper.readValue(publishMessage, long.class);
            alarmService.send(0L, userId);
            log.info("onMessage userId {}", userId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

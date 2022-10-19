package com.example.board.controller;

import com.example.board.dto.chat.ChatMessage;
import com.example.board.dto.chat.MessageType;
import com.example.board.repository.RedisChatRoomRepository;
import com.example.board.service.RedisChatPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisChatPublisher redisPublisher;
    private final RedisChatRoomRepository redisChatRoomRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (MessageType.ENTER.equals(message.getType())) {
            String username = message.getSender();
            message.setSender("New");
            redisChatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(username + "님이 입장하셨습니다.");
        }
        redisPublisher.publish(redisChatRoomRepository.getTopic(message.getRoomId()), message);
    }
}
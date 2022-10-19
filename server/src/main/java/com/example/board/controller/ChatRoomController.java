package com.example.board.controller;

import com.example.board.dto.chat.ChatRoom;
import com.example.board.dto.response.Response;
import com.example.board.repository.RedisChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/chat")
@RestController
public class ChatRoomController {
    private final RedisChatRoomRepository redisChatRoomRepository;

    @GetMapping("/rooms")
    public Response<List<ChatRoom>> room() {
        return Response.success(redisChatRoomRepository.findAllRoom());
    }

    @PostMapping("/room")
    public Response<ChatRoom> createRoom(@RequestParam String name) {
        return Response.success(redisChatRoomRepository.createChatRoom(name));
    }

    @GetMapping("/room/{roomId}")
    public Response<ChatRoom> roomInfo(@PathVariable String roomId) {
        return Response.success(redisChatRoomRepository.findRoomById(roomId));
    }
}

package com.example.board.controller;

import com.example.board.dto.chat.ChatRoom;
import com.example.board.dto.response.Response;
import com.example.board.repository.ChatRoomCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/chat")
@RestController
public class ChatRoomController {
    private final ChatRoomCacheRepository chatRoomCacheRepository;

    @GetMapping("/rooms")
    public Response<List<ChatRoom>> room() {
        return Response.success(chatRoomCacheRepository.findAllRoom());
    }

    @PostMapping("/room")
    public Response<ChatRoom> createRoom(@RequestParam String name) {
        return Response.success(chatRoomCacheRepository.createChatRoom(name));
    }

    @GetMapping("/room/{roomId}")
    public Response<ChatRoom> roomInfo(@PathVariable String roomId) {
        return Response.success(chatRoomCacheRepository.findRoomById(roomId));
    }
}

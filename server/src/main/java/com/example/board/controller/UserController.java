package com.example.board.controller;

import com.example.board.dto.request.UserJoinRequest;
import com.example.board.dto.request.UserLoginRequest;
import com.example.board.dto.response.Response;
import com.example.board.dto.response.UserJoinResponse;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody @Valid UserJoinRequest request) {
        return Response.success(UserJoinResponse.fromUserDto(userService.join(request.getUsername(), request.getPassword())));
    }

    @PostMapping("/login")
    public Response<String> login(@RequestBody @Valid UserLoginRequest request) {
        return Response.success(userService.login(request.getUsername(), request.getPassword()));
    }
}
package com.example.board.controller;

import com.example.board.dto.request.UserJoinRequest;
import com.example.board.dto.request.UserLoginRequest;
import com.example.board.dto.response.Response;
import com.example.board.dto.response.TokenResponse;
import com.example.board.dto.response.UserJoinResponse;
import com.example.board.dto.security.UserPrincipal;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping
    public Response<UserJoinResponse> userInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(UserJoinResponse.fromUserPrincipal(userPrincipal));
    }

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody @Valid UserJoinRequest request) {
        return Response.success(UserJoinResponse.fromUserDto(userService.join(request.getUsername(), request.getPassword())));
    }

    @PostMapping("/login")
    public Response<TokenResponse> login(@RequestBody @Valid UserLoginRequest request) {
        return Response.success(userService.login(request.getUsername(), request.getPassword()));
    }

    @GetMapping("/reissue")
    public Response<String> reissue(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(userService.regenerateToken(userPrincipal.getUsername()));
    }
}

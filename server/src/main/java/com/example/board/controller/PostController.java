package com.example.board.controller;

import com.example.board.dto.request.PostWriteRequest;
import com.example.board.dto.response.PostReadResponse;
import com.example.board.dto.response.PostUpdateResponse;
import com.example.board.dto.response.PostWriteResponse;
import com.example.board.dto.response.Response;
import com.example.board.dto.security.UserPrincipal;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public Response<List<PostReadResponse>> list(@RequestParam int page) {
        return Response.success(postService.list(page)
                .stream().map(PostReadResponse::fromPostDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/post/{postId}")
    public Response<PostReadResponse> getPost(@PathVariable Long postId) {
        return Response.success(PostReadResponse.fromPostDto(postService.getPost(postId)));
    }

    @PostMapping("/write")
    public Response<PostWriteResponse> writePost(
            @RequestBody PostWriteRequest postWriteRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ) {
        return Response.success(PostWriteResponse.of(postService.create(postWriteRequest.getTitle(), postWriteRequest.getContent(), userPrincipal.getUsername())));
    }

    @PutMapping("/{postId}/update")
    public Response<PostUpdateResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostWriteRequest postWriteRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return Response.success(PostUpdateResponse.fromDto(postService.update(postId, postWriteRequest.getTitle(), postWriteRequest.getContent(), userPrincipal.getUsername())));
    }

    @DeleteMapping("/{postId}/delete")
    public Response<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postService.delete(postId, userPrincipal.getUsername());
        return Response.success();
    }
}

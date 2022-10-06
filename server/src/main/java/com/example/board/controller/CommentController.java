package com.example.board.controller;

import com.example.board.dto.CommentDto;
import com.example.board.dto.response.CommentReadResponse;
import com.example.board.dto.response.Response;
import com.example.board.dto.security.UserPrincipal;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public Response<List<CommentReadResponse>> list(@PathVariable Long postId) {
        return Response.success(commentService.commentList(postId).stream().map(CommentReadResponse::fromCommentDto)
                .collect(Collectors.toList()));
    }

//    @PostMapping("/{postId}")
//    public void create(
//            @PathVariable Long postId,
//            @AuthenticationPrincipal UserPrincipal userPrincipal,
//            CommentWriteRequest commentWriteRequest // it has a parent id? or not
//    ) {
//
//    }
//
//    @DeleteMapping("/{postId}")
//    public void delete(
//            @PathVariable Long postId,
//            CommentDeleteRequest commentDeleteRequest
//    ) {
//
//    }


}

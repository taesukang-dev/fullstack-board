package com.example.board.controller;

import com.example.board.dto.request.CommentWriteRequest;
import com.example.board.dto.response.CommentReadResponse;
import com.example.board.dto.response.CommentWriteResponse;
import com.example.board.dto.response.Response;
import com.example.board.dto.security.UserPrincipal;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    /**
     * @param postId
     * @param userPrincipal
     * @param commentWriteRequest doesn't have a parent
     */
    @PostMapping("/{postId}")
    public Response<CommentWriteResponse> create(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid CommentWriteRequest commentWriteRequest
    ) {
        return Response.success(CommentWriteResponse.fromCommentDto(commentService.create(postId, userPrincipal.getUsername(), commentWriteRequest.getContent())));
    }

    /**
     * @param postId
     * @param userPrincipal
     * @param commentWriteRequest have a parent
     */
    @PostMapping("/{postId}/{parentId}")
    public Response<CommentWriteResponse> create(
            @PathVariable Long postId,
            @PathVariable Long parentId,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid CommentWriteRequest commentWriteRequest
    ) {
        return Response.success(CommentWriteResponse.fromCommentDto(commentService.createdByParent(postId, userPrincipal.getUsername(), parentId, commentWriteRequest.getContent())));
    }

    @DeleteMapping("/{postId}/{commentId}")
    public Response<Void> delete(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
       commentService.delete(postId, userPrincipal.getUsername(), commentId);
        return Response.success();
    }


}

package com.example.board.service;

import com.example.board.domain.Comment;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.CommentDto;
import com.example.board.exception.BoardApplicationException;
import com.example.board.exception.ErrorCode;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public List<CommentDto> commentList(Long postId) {
        return commentRepository.findByPostId(postRepository.findById(postId)
                        .orElseThrow(() -> new BoardApplicationException(ErrorCode.POST_NOT_FOUND))
                        .getId())
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.POST_NOT_FOUND))
                .stream().map(CommentDto::fromComment)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto create(Long postId, String username, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.POST_NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND));
        return CommentDto.fromComment(commentRepository.save(Comment.of(content, post, user)));
    }

    @Transactional
    public CommentDto createdByParent(Long postId, String username, Long parentId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.POST_NOT_FOUND));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND));
        Comment parent = commentRepository.findById(parentId)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.COMMENT_NOT_FOUND));
        Comment comment = Comment.of(content, post, user, parent);
        parent.addChild(comment);
        return CommentDto.fromComment(parent);
    }

    @Transactional
    public void delete(String username, Long commentId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.COMMENT_NOT_FOUND));
        if (comment.getUser() != user) {
            throw new BoardApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        if (comment.getChild().isEmpty()) {
            commentRepository.delete(comment);
        } else {
            comment.getChild().forEach(commentRepository::delete);
            commentRepository.delete(comment);
        }
    }
}

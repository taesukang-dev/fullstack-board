package com.example.board.service;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.PostDto;
import com.example.board.exception.BoardApplicationException;
import com.example.board.exception.ErrorCode;
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
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostDto> list(int page) {
        return postRepository.findAllWithPaging(page)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.POST_NOT_FOUND))
                .stream().map(PostDto::fromPost)
                .collect(Collectors.toList());
    }

    public PostDto getPost(Long postId) {
        return PostDto.fromPost(postRepository.findById(postId)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.POST_NOT_FOUND)));
    }

    @Transactional
    public Long create(String title, String content, String username) {
        User user = getUserOrThrow(username);
        return postRepository.save(Post.of(title, content, user));
    }

    @Transactional
    public PostDto update(Long postId, String title, String content, String username) {
        Post post = getPostOrThrow(postId);
        User user = getUserOrThrow(username);
        if (post.getUser() != user) {
            throw new BoardApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        post.updatePost(title, content);
        return PostDto.fromPost(post);
    }

    @Transactional
    public void delete(Long postId, String username) {
        User user = getUserOrThrow(username);
        Post post = getPostOrThrow(postId);
        if (post.getUser() != user) {
            throw new BoardApplicationException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        postRepository.remove(post);
    }

    private User getUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    private Post getPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BoardApplicationException(ErrorCode.POST_NOT_FOUND));
    }

}

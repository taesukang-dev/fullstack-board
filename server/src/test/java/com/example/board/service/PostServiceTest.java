package com.example.board.service;

import com.example.board.dto.PostDto;
import com.example.board.exception.BoardApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Transactional
@SpringBootTest
class PostServiceTest {
    @Autowired private PostService postService;
    @Autowired private UserService userService;

    @BeforeEach
    void beforeEach() {
        userService.join("test", "test");
        for (int i = 1; i < 20; i++) {
            postService.create("title" + i, "content" + i, "test");
        }
    }

    @Test
    void 모든_게시물_조회() {

        // given
//        userService.join("test", "test");
        // when
        List<PostDto> list = postService.list(0);
        // then
        assertThat(list.size()).isEqualTo(10);
    }

    @Test
    void 한_게시물_조회() {
        // given
        // when
        PostDto post = postService.getPost(1L);
        assertThat(post.getContent()).isEqualTo("content1");
        // then
    }

    @Test
    void 한_게시물_조회_실패() {
        // given
        // when
        // then
        assertThatThrownBy(() -> postService.getPost(25L))
                .isInstanceOf(BoardApplicationException.class);
    }

    @Test
    void 게시물_생성에_실패할_경우() {
        // given
        // when
        // then
        assertThatThrownBy(() -> postService.create("title", "content", "test"))
                .isInstanceOf(BoardApplicationException.class);
    }

    @Test
    void 게시물_업데이트() {
        // given
        // when
        postService.update(1L, "update", "update", "test");
        List<PostDto> list = postService.list(0);
        PostDto postDto = list.get(0);
        // then
        assertThat(postDto.getTitle()).isEqualTo("update");
    }

    @Test
    void 게시물_업데이트_실패할_경우() {
        // given
        // when
        // then
        assertThatThrownBy(() -> postService.update(1L, "update", "update", "test2"))
                .isInstanceOf(BoardApplicationException.class);
    }

    @Test
    void 게시물_삭제() {
        // given
        // when
        postService.delete(1L, "test");
        List<PostDto> list = postService.list(0);
        PostDto postDto = list.get(0);
        // then
    }

    @Test
    void 게시물_삭제_실패할_경우() {
        // given
        // when
        // then
        assertThatThrownBy(() -> postService.delete(1L, "test1"))
                .isInstanceOf(BoardApplicationException.class);
    }
}
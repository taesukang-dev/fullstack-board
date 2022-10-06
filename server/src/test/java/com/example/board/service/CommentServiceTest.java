package com.example.board.service;

import com.example.board.domain.Comment;
import com.example.board.dto.CommentDto;
import com.example.board.dto.UserDto;
import com.example.board.exception.BoardApplicationException;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@SpringBootTest
class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;
    @Autowired PostService postService;
    @Autowired UserService userService;
    @Autowired EntityManager em;


    @Test
    void 댓글_조회_정상() throws Exception {
        // given
        UserDto user = userService.join("test", "test");
        Long postId = postService.create("title", "content", "test");
        commentService.create(postId, user.getUsername(), "content");
        // when
        List<CommentDto> commentDtos = commentService.commentList(postId);
        // then
        assertThat(commentDtos.size()).isEqualTo(1);
    }

    @Test
    void 댓글_조회_게시글이_없는_경우() throws Exception {
        // given
        // when
        // then
        assertThatThrownBy(() -> commentService.commentList(null))
                .isInstanceOf(BoardApplicationException.class);

    }

    @Test
    void 댓글_생성_게시글이_없는_경우() throws Exception {
        // given
        UserDto user = userService.join("test", "test");
        // when
        // then
        assertThatThrownBy(() -> commentService.create(null, user.getUsername(), "content"))
                .isInstanceOf(BoardApplicationException.class);
    }

    @Test
    void 댓글_생성_사용자가_없는_경우() throws Exception {
        // given
        UserDto user = userService.join("test", "test");
        Long postId = postService.create("title", "content", user.getUsername());
        // when
        // then
        assertThatThrownBy(() -> commentService.create(postId, null, "content"))
                .isInstanceOf(BoardApplicationException.class);
    }

    @Test
    void 대댓글_생성_정상() throws Exception {
        // given
        UserDto user = userService.join("test", "test");
        Long postId = postService.create("title", "content", "test");
        CommentDto parent = commentService.create(postId, user.getUsername(), "parent");
        // when
        CommentDto child = commentService.createdByParent(postId, user.getUsername(), parent.getId(), "child");
        CommentDto child2 = commentService.createdByParent(postId, user.getUsername(), parent.getId(), "child2");
        // then
        List<CommentDto> commentDtos = commentService.commentList(postId);
        assertThat(commentDtos.get(0).getChild().size()).isEqualTo(2);
    }

    @Test
    void 대댓글_생성_상위_댓글이_없는_경우() throws Exception {
        // given
        UserDto user = userService.join("test", "test");
        Long postId = postService.create("title", "content", "test");
        // when
        // then
        assertThatThrownBy(() -> commentService.createdByParent(postId, user.getUsername(), null, "content22"))
                .isInstanceOf(BoardApplicationException.class);
    }

    @Test
    void 댓글_삭제() throws Exception {
        UserDto user = userService.join("test", "test");
        Long postId = postService.create("title", "content", "test");
        CommentDto comment = commentService.create(postId, user.getUsername(), "content");
        // when
        commentService.delete(user.getUsername(), comment.getId());
        // then
        assertThat(commentService.commentList(postId).size()).isEqualTo(0);
    }

    @Test
    void 댓글_삭제_대댓글이_있는_경우() throws Exception {
        UserDto user = userService.join("test", "test");
        Long postId = postService.create("title", "content", "test");
        CommentDto parent = commentService.create(postId, user.getUsername(), "content");
        // when
        CommentDto child = commentService.createdByParent(postId, user.getUsername(), parent.getId(), "content22");
        // then
        commentService.delete(user.getUsername(), parent.getId());

        assertThat(commentRepository.findById(child.getId())).isNotPresent();
    }


    @Test
    void 댓글_삭제_유저가_없는_경우() throws Exception {
        UserDto user = userService.join("test", "test");
        Long postId = postService.create("title", "content", "test");
        CommentDto parent = commentService.create(postId, user.getUsername(), "content");
        // when
        // then
        assertThatThrownBy(() -> commentService.delete(null, parent.getId()))
                .isInstanceOf(BoardApplicationException.class);
    }

    @Test
    void 댓글_삭제_댓글이_없는_경우() throws Exception {
        UserDto user = userService.join("test", "test");
        Long postId = postService.create("title", "content", "test");
        CommentDto comment = commentService.create(postId, user.getUsername(), "content");
        // when
        // then
        assertThatThrownBy(() -> commentService.delete(user.getUsername(), null))
                .isInstanceOf(BoardApplicationException.class);
    }


    @Test
    void 댓글_삭제_유저가_다를_경우() throws Exception {
        UserDto user = userService.join("test", "test");
        Long postId = postService.create("title", "content", "test");
        CommentDto comment = commentService.create(postId, user.getUsername(), "content");
        // when
        // then
        assertThatThrownBy(() -> commentService.delete("notTest", comment.getId()))
                .isInstanceOf(BoardApplicationException.class);
    }
}
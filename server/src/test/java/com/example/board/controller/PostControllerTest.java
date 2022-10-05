package com.example.board.controller;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.PostDto;
import com.example.board.dto.response.PostWriteResponse;
import com.example.board.exception.BoardApplicationException;
import com.example.board.exception.ErrorCode;
import com.example.board.service.PostService;
import com.example.board.service.fixture.PostFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.With;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean PostService postService;

    @Test
    void 게시물_조회_정상() throws Exception {
        // given
        when(postService.list(anyInt())).thenReturn(mock(List.class));
        // when
        // then
        mockMvc.perform(get("/api/posts/list?page=0")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 한_게시물_조회_정상() throws Exception {
        // given
        // when
        when(postService.getPost(eq(1L)))
                .thenReturn(PostDto.fromPost(PostFixture.get("test", 1L, 1L)));
        // then
        mockMvc.perform(get("/api/posts/post/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 한_게시물_없는_경우() throws Exception {
        // given
        // when
        doThrow(new BoardApplicationException(ErrorCode.POST_NOT_FOUND))
                .when(postService).getPost(eq(1L));
        // then
        mockMvc.perform(get("/api/posts/post/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void 게시물_쓰기_정상() throws Exception {
        // given
        // when
//        when(postService.create(eq("title"), eq("content"), eq("username")))
//                .thenReturn(eq(1L));
        // then
        mockMvc.perform(post("/api/posts/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new PostWriteResponse(1L)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 게시물_쓰기_유저_오류() {
        // given
        // when
        // then
    }

    @Test
    void 게시물_수정_정상() {
        // given
        // when
        // then
    }

    @Test
    void 게시물_수정_유저_오류() {
        // given
        // when
        // then
    }

    @Test
    void 게시물_수정_게시물_오류() {
        // given
        // when
        // then
    }

    @Test
    void 게시물_삭제_정상() {
        // given
        // when
        // then
    }

    @Test
    void 게시물_삭제_유저_오류() {
        // given
        // when
        // then
    }

    @Test
    void 게시물_삭제_게시물_오류() {
        // given
        // when
        // then
    }

}
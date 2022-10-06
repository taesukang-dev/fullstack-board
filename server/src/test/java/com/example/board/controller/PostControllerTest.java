package com.example.board.controller;

import com.example.board.dto.PostDto;
import com.example.board.dto.request.PostWriteRequest;
import com.example.board.dto.response.PostUpdateResponse;
import com.example.board.dto.response.PostWriteResponse;
import com.example.board.exception.BoardApplicationException;
import com.example.board.exception.ErrorCode;
import com.example.board.service.PostService;
import com.example.board.fixture.PostFixture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        String title = "title";
        String content = "content";
        // when

        // then
        mockMvc.perform(post("/api/posts/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(
                                new PostWriteRequest(title, content)
                        ))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void 게시물_쓰기_유저_오류() throws Exception{
        // given
        String title = "title";
        String content = "content";
        // when
        // then
        mockMvc.perform(post("/api/posts/write")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(
                                        new PostWriteRequest(title, content)
                                ))
                        ).andDo(print())
                        .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void 게시물_수정_정상() throws Exception{
        String title = "title";
        String content = "content";
        // given
        when(postService.update(anyLong(), eq(title), eq(content), any()))
                .thenReturn(PostDto.fromPost(PostFixture.get("username", 1L, 1L)));
        // when
        // then
        mockMvc.perform(put("/api/posts/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(
                                new PostWriteRequest(title, content)
                        ))
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithAnonymousUser
    void 게시물_수정_유저_오류() throws Exception {
        String title = "title";
        String content = "content";
        // given
        when(postService.update(anyLong(), eq(title), eq(content), any()))
                .thenReturn(PostDto.fromPost(PostFixture.get("username", 1L, 1L)));
        // when
        // then
        mockMvc.perform(put("/api/posts/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(
                                new PostWriteRequest(title, content)
                        ))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void 게시물_수정_게시물_오류() throws Exception{
        String title = "title";
        String content = "content";
        // given
        doThrow(new BoardApplicationException(ErrorCode.POST_NOT_FOUND))
                .when(postService).update(anyLong(), eq(title), eq(content), any());

        // when
        // then
        mockMvc.perform(put("/api/posts/999/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(
                                new PostWriteRequest(title, content)
                        ))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithUserDetails(value = "test", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void 게시물_삭제_정상() throws Exception{
        String title = "title";
        String content = "content";
        // given
        // when
        // then
        mockMvc.perform(delete("/api/posts/1/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    void 게시물_삭제_유저_오류() throws Exception{
        String title = "title";
        String content = "content";
        // given
        // when
        // then
        mockMvc.perform(delete("/api/posts/1/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void 게시물_삭제_게시물_오류() throws Exception{
        // given
        // when
        doThrow(new BoardApplicationException(ErrorCode.POST_NOT_FOUND))
                .when(postService).delete(anyLong(), any());
        // then
        mockMvc.perform(delete("/api/posts/1/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
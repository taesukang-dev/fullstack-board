package com.example.board.controller;

import com.example.board.dto.UserDto;
import com.example.board.dto.request.UserJoinRequest;
import com.example.board.dto.request.UserLoginRequest;
import com.example.board.exception.BoardApplicationException;
import com.example.board.exception.ErrorCode;
import com.example.board.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean UserService userService;

    @Test
    void 가입_정상() throws Exception {
        // given
        String username = "username";
        String password = "password";
        // when
        when(userService.join(username, password)).thenReturn(mock(UserDto.class));
        // then
        mockMvc.perform(post("/api/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 가입_중복일_경우() throws Exception {
        // given
        String username = "username";
        String password = "password";
        // when
        when(userService.join(username, password)).thenThrow(new BoardApplicationException(ErrorCode.DUPLICATED_USER));
        // then
        mockMvc.perform(post("/api/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @Disabled
    void 로그인_정상() throws Exception {
        // given
        String username = "username";
        String password = "password";
        // when
//        when(userService.login(username, password)).thenReturn("some_token");
        // then
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 로그인_비정상일_경우() throws Exception{
        // given
        String username = "username";
        String password = "password";
        // when
        when(userService.login(username, password)).thenThrow(new BoardApplicationException(ErrorCode.INVALID_PASSWORD));
        // then
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(username, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
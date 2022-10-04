package com.example.board.service;

import com.example.board.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;

    @Test
    void 회원가입_정상() {
        // given
        UserDto join = userService.join("test1", "test1");
        // when
        // then
        log.info("join id : {}, username : {}", join.getId(), join.getUsername());
    }

    @Test
    void 회원가입시_중복username_존재할_경우() {
        // given
        UserDto join1 = userService.join("test1", "test1");
        // when
        // then
        Assertions.assertThatThrownBy(() -> userService.join("test1", "test1")).isInstanceOf(RuntimeException.class);
    }

}
package com.example.board.service;

import com.example.board.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;

    @BeforeEach
    void beforeEach() {
        userService.join("test1", "test1");
        userService.join("test2", "test2");
    }

    @Test
    void 회원가입_정상() {
        // given
        UserDto join = userService.join("test3", "test1");
        // when
        // then
        log.info("join id : {}, username : {}", join.getId(), join.getUsername());
    }

    @Test
    void 회원가입시_중복username_존재할_경우() {
        // given
        // when
        // then
        assertThatThrownBy(() -> userService.join("test1", "test1")).isInstanceOf(RuntimeException.class);
    }

    @Disabled
    @Test
    void 로그인_정상() {
//        String login = userService.login("test1", "test1");
//        assertThat(login).isInstanceOf(String.class);
    }

    @Test
    void 로그인시_유저가_없는_경우() {
        assertThatThrownBy(() -> userService.login("st1", ""))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 로그인시_비밀번호가_틀릴_경우() {
        assertThatThrownBy(() -> userService.login("test1", ""))
                .isInstanceOf(RuntimeException.class);
    }
}
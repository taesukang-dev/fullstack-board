package com.example.board.service;

import com.example.board.domain.Comment;
import com.example.board.domain.User;
import com.example.board.dto.UserDto;
import com.example.board.dto.response.UserJoinResponse;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto join(String username, String password) {
        userRepository.findByUsername(username)
                .ifPresent(it -> { throw new RuntimeException("No!!!!!!!!!!!!"); });
        return userRepository.save(User.of(username, password));
    }

    public String login(String username, String password) {
        UserDto user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("None of user"));
        if (user.getPassword() != password) {
            throw new RuntimeException("Invalid password");
        }
        return "login success";
    }

}

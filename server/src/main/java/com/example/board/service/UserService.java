package com.example.board.service;

import com.example.board.domain.Comment;
import com.example.board.domain.User;
import com.example.board.dto.UserDto;
import com.example.board.dto.response.UserJoinResponse;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}

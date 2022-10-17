package com.example.board.service;

import com.example.board.domain.User;
import com.example.board.dto.UserDto;
import com.example.board.exception.BoardApplicationException;
import com.example.board.exception.ErrorCode;
import com.example.board.jwt.JwtTokenProvider;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserDto join(String username, String password) {
        log.info("the username {}", username);
        userRepository.findByUsername(username)
                .ifPresent(it -> { throw new BoardApplicationException(ErrorCode.DUPLICATED_USER); });
        return UserDto.fromUser(userRepository.save(User.of(username, passwordEncoder.encode(password))));
    }

    public UserDto userInfo(String username) {
        return UserDto.fromUser(userRepository.findByUsername(username).orElseThrow(() -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND)));
    }


    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BoardApplicationException(ErrorCode.INVALID_PASSWORD);
        }
        ArrayList<String> roles = new ArrayList<>();
        roles.add(user.getRole().getName());

        return jwtTokenProvider.createToken(user.getUsername(), roles);
    }

}

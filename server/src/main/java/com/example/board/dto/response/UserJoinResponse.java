package com.example.board.dto.response;

import com.example.board.domain.User;
import com.example.board.domain.UserRole;
import com.example.board.dto.UserDto;
import com.example.board.dto.security.UserPrincipal;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserJoinResponse {
    private Long id;
    private String username;
    private UserRole role;

    public static UserJoinResponse fromUserDto(UserDto userDto) {
        return new UserJoinResponse(userDto.getId(), userDto.getUsername(), userDto.getRole());
    }

    public static UserJoinResponse fromUserPrincipal(UserPrincipal userPrincipal) {
        return new UserJoinResponse(userPrincipal.getId(), userPrincipal.getUsername(), UserRole.USER);
    }
}

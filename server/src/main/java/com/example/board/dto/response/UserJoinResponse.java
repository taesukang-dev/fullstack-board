package com.example.board.dto.response;

import com.example.board.domain.UserRole;
import com.example.board.dto.UserDto;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
}

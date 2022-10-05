package com.example.board.dto;

import com.example.board.domain.User;
import com.example.board.domain.UserRole;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private UserRole role;

    public static UserDto fromUser(User user) {
        return new UserDto(user.getId(), user.getPassword(), user.getUsername(), user.getRole());
    }
}

package com.example.board.dto.security;

import com.example.board.domain.UserRole;
import com.example.board.dto.UserDto;
import org.apache.catalina.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {

    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal of(String username, String password, UserRole userRole) {
        Set<UserRole> roles = Set.of(userRole);

        return new UserPrincipal(
                username,
                password,
                roles.stream().map(UserRole::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toUnmodifiableSet())
        );
    }

    public static UserPrincipal from(UserDto userDto) {
        return UserPrincipal.of(userDto.getUsername(), userDto.getPassword(), userDto.getRole());
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return this.authorities; }
    @Override public String getPassword() { return this.password; }
    @Override public String getUsername() { return this.username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

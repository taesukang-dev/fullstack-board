package com.example.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),;

    @Getter private String name;

    UserRole(String name) {
        this.name = name;
    }
}

package com.example.board.domain;

import lombok.Getter;

public enum AlarmType {
    COMMENT("New Comment!"),
    LIKE("New Like!"),;

    @Getter
    private String name;
    AlarmType(String name) {
        this.name = name;
    }
}

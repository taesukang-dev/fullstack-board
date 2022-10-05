package com.example.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not found"),
    DUPLICATED_USER(HttpStatus.CONFLICT, "Already used username"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "Unauthorized access"),
    ;

    private HttpStatus status;
    private String message;

}

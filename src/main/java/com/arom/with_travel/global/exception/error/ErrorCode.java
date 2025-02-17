package com.arom.with_travel.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN("INVALID_TOKEN", "Invalid refresh token", ErrorDisplayType.HIDE),
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", "Token not found", ErrorDisplayType.HIDE),
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found", ErrorDisplayType.TOAST),
    ;

    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

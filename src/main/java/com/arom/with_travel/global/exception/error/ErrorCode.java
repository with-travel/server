package com.arom.with_travel.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    REFRESH_TOKEN_NOT_FOUND("RT001", "Refresh Token not found", ErrorDisplayType.POPUP),
    INVALID_TOKEN("AUTH001", "Invalid refresh token", ErrorDisplayType.POPUP)
    ;


    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

package com.arom.with_travel.global.jwt.error;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements BaseCode {

    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"TKN-0000", "refresh token이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"TKN-0001", "유효하지 않은 token입니다.", ErrorDisplayType.POPUP);

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

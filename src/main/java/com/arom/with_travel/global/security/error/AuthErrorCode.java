package com.arom.with_travel.global.security.error;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN-0001", "유효하지 않는 토큰입니다.", ErrorDisplayType.POPUP),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN-0000", "토큰 오류", ErrorDisplayType.POPUP),
    EMPTY_TOKEN_PROVIDED(HttpStatus.UNAUTHORIZED, "TOKEN-0002", "토큰 텅텅", ErrorDisplayType.POPUP),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TKN-0000", "refresh token이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    AUTH_UNSUPPORTED_PRINCIPAL(HttpStatus.UNAUTHORIZED, "AUTH-0001", "지원하지 않는 인증 주체입니다.", ErrorDisplayType.POPUP),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "LOGIN-0001", "중복된 이메일이 존재합니다.", ErrorDisplayType.POPUP),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "LOGIN-0002", "비밀번호가 올바르지 않습니다.", ErrorDisplayType.POPUP),
    LOGIN_FAIL(HttpStatus.UNAUTHORIZED, "LOGIN-0003", "로그인 과정이 정상적으로 이루어지지 않았습니다.", ErrorDisplayType.POPUP),
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

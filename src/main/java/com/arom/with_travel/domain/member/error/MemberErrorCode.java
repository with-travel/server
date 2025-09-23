package com.arom.with_travel.domain.member.error;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseCode {

    INVALID_EMAIL_OR_PASSWORD(HttpStatus.UNAUTHORIZED, "MEMBER-0001", "유효하지 않는 이메일, 비번", ErrorDisplayType.POPUP),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEM-0000", "해당 회원이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    MEMBER_ALREADY_REGISTERED(HttpStatus.CONFLICT, "MEM-0001", "이미 회원가입되어 있습니다.", ErrorDisplayType.POPUP),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

package com.arom.with_travel.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseCode{

    TMP_ERROR(HttpStatus.BAD_REQUEST, "S3-0000", "파일 형식이 올바르지 않습니다.", ErrorDisplayType.MODAL),

    //client error : 4xx

    //member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"MEM-0000", "해당 회원이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    MEMBER_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST,"MEM-0001", "이미 회원가입되어 있습니다.", ErrorDisplayType.POPUP),

    //token
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"TKN-0000", "refresh token이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"TKN-0001", "유효하지 않은 token입니다.", ErrorDisplayType.POPUP),

    //accompany
    ACCOMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST,"ACC-0000", "해당 동행이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    ACCOMPANY_ALREADY_APPLIED(HttpStatus.BAD_REQUEST,"ACC-0001", "이미 신청한 동행입니다.", ErrorDisplayType.POPUP),
    ACCOMPANY_POST_ERROR(HttpStatus.BAD_REQUEST,"ACC-0002", "동행 입력이 올바르지 않습니다.", ErrorDisplayType.POPUP),
    // ACCOMPANY_ALREADY_CONFIRMED("ACC-0002", "참가 확정된 동행입니다.", ErrorDisplayType.POPUP)

    //survey
    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND,"SVY-0000", "해당 설문이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

package com.arom.with_travel.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TMP_ERROR("S3-0000", "파일 형식이 올바르지 않습니다.", ErrorDisplayType.MODAL),

    //client error : 4xx

    //member
    MEMBER_NOT_FOUND("MEM-0000", "해당 회원이 존재하지 않습니다.", ErrorDisplayType.POPUP),

    //token
    TOKEN_NOT_FOUND("TKN-0000", "refresh token이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    INVALID_TOKEN("TKN-0001", "유효하지 않은 token입니다.", ErrorDisplayType.POPUP),

    //accompany
    ACCOMPANY_NOT_FOUND("ACC-0000", "해당 동행이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    ACCOMPANY_ALREADY_APPLIED("ACC-0001", "이미 신청한 동행입니다.", ErrorDisplayType.POPUP),
    // ACCOMPANY_ALREADY_CONFIRMED("ACC-0002", "참가 확정된 동행입니다.", ErrorDisplayType.POPUP)

    //survey
    SURVEY_NOT_FOUND("SVY-0000", "해당 설문이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    ;


    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

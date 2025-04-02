package com.arom.with_travel.domain.accompanies.error;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccompanyErrorCode implements BaseCode {

    ACCOMPANY_NOT_FOUND(HttpStatus.BAD_REQUEST,"ACC-0000", "해당 동행이 존재하지 않습니다.", ErrorDisplayType.POPUP),
    ACCOMPANY_ALREADY_APPLIED(HttpStatus.BAD_REQUEST,"ACC-0001", "이미 신청한 동행입니다.", ErrorDisplayType.POPUP),
    ACCOMPANY_POST_ERROR(HttpStatus.BAD_REQUEST,"ACC-0002", "동행 입력이 올바르지 않습니다.", ErrorDisplayType.POPUP)
    // ACCOMPANY_ALREADY_CONFIRMED("ACC-0002", "참가 확정된 동행입니다.", ErrorDisplayType.POPUP)
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

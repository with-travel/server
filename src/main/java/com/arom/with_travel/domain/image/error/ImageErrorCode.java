package com.arom.with_travel.domain.image.error;

import com.arom.with_travel.global.exception.error.BaseCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImageErrorCode implements BaseCode {
    // Image
    INVALID_IMG_TYPE(HttpStatus.BAD_REQUEST, "IMG-0000", "지원하지 않는 이미지 형식입니다.", ErrorDisplayType.POPUP),
    IMG_URL_MUST_FILLED(HttpStatus.BAD_REQUEST, "IMG-0001", "이미지 url이 존재해야합니다.", ErrorDisplayType.POPUP),
    IMG_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "IMG-0002", "이미지 저장에 실패했습니다.", ErrorDisplayType.POPUP),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

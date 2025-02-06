package com.arom.with_travel.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TMP_ERROR("S3-0000", "파일 형식이 올바르지 않습니다.", ErrorDisplayType.MODAL);

    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}

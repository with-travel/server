package com.arom.with_travel.global.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "Server Error", ErrorDisplayType.POPUP),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "Invalid Input Value", ErrorDisplayType.POPUP),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C003", "Invalid HTTP Method", ErrorDisplayType.POPUP),
    CONFLICT(HttpStatus.CONFLICT, "C005", "Conflict Occurred", ErrorDisplayType.POPUP),
    UNACCEPTABLE_EXTENSION(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "C007", "Unacceptable Extension", ErrorDisplayType.POPUP),
    INVALID_JSON_FORMAT(HttpStatus.BAD_REQUEST, "C008", "Invalid JSON Format", ErrorDisplayType.POPUP),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "C009", "Missing Parameter", ErrorDisplayType.POPUP),
    INVALID_PARAMETER_TYPE(HttpStatus.BAD_REQUEST, "C010", "Invalid Parameter Type", ErrorDisplayType.POPUP),
    MISSING_PATH_VARIABLE(HttpStatus.BAD_REQUEST, "C011", "Missing Path Variable", ErrorDisplayType.POPUP),
    FORBIDDEN(HttpStatus.FORBIDDEN, "C012", "Forbidden", ErrorDisplayType.POPUP),
    ERR_DATA_INTEGRITY_VIOLATION(HttpStatus.CONFLICT, "E001", "Data integrity violation", ErrorDisplayType.POPUP),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "C013", "Validation Failed", ErrorDisplayType.POPUP),
    REQ_BODY_ERROR(HttpStatus.BAD_REQUEST, "C014", "", ErrorDisplayType.POPUP),
    REQ_PARAMS_ERROR(HttpStatus.BAD_REQUEST, "C015", "", ErrorDisplayType.POPUP),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
    private final ErrorDisplayType displayType;
}
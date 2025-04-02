package com.arom.with_travel.global.exception.error;

import org.springframework.http.HttpStatus;

public interface BaseCode {
    HttpStatus getStatus();
    String getCode();
    String getMessage();
    ErrorDisplayType getDisplayType();
}

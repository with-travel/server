package com.arom.with_travel.global.exception.handler;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.exception.error.ErrorDisplayType;
import com.arom.with_travel.global.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler {


    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> onThrowException(BaseException baseException) {
        ErrorResponse response = ErrorResponse.generateErrorResponse(baseException);
        return ResponseEntity.status(baseException.getCode().getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> onThrowException(MethodArgumentNotValidException exception){
        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.TMP_ERROR.getCode())
                .message(exception.getBindingResult().getFieldError().getDefaultMessage())
                .displayType(ErrorDisplayType.POPUP)
                .build();
        return ResponseEntity.status(exception.getStatusCode()).body(response);
    }
}

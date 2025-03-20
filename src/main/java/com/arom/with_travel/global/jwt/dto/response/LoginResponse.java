package com.arom.with_travel.global.jwt.dto.response;

import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    public static <T> LoginResponse<T> success(T data) {
        return new LoginResponse<>(true, "SUCCESS", "요청이 성공적으로 처리되었습니다.", data);
    }

    public static LoginResponse<?> error(ErrorCode errorCode) {
        return new LoginResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null);
    }
}

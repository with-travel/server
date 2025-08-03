package com.arom.with_travel.global.jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AuthTokenResponse {
    private String accessToken;
    private String refreshToken;
}

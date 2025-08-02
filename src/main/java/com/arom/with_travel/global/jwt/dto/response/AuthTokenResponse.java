package com.arom.with_travel.global.jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthTokenResponse {
    private String accessToken;
}

package com.arom.with_travel.domain.member.dto;

import com.arom.with_travel.global.jwt.dto.response.AuthTokenResponse;
import com.nimbusds.oauth2.sdk.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberSignupTokenResponse {
    private MemberSignupResponseDto memberSignupDto;
    private AuthTokenResponse tokenDto;
}

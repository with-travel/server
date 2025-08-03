package com.arom.with_travel.domain.member.dto;

import com.arom.with_travel.global.jwt.dto.response.AuthTokenResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignupTokenResponse {
    private MemberSignupResponseDto memberSignupDto;
    private AuthTokenResponse tokenDto;
}

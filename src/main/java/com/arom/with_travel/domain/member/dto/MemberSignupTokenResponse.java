package com.arom.with_travel.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberSignupTokenResponse {
    private MemberSignupResponseDto memberSignupDto;
    private String accessToken;
}

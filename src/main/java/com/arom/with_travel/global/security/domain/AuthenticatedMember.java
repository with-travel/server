package com.arom.with_travel.global.security.domain;

import com.arom.with_travel.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticatedMember {
    private Long memberId;
    private String email;
    private final String role;

    public static AuthenticatedMember from(Member member){
        return new AuthenticatedMember(
                member.getId(),
                member.getEmail(),
                member.getRole().name()
        );
    }
}

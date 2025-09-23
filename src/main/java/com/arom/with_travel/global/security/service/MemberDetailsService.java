package com.arom.with_travel.global.security.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.error.MemberException;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.security.domain.AuthenticatedMember;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.arom.with_travel.domain.member.error.MemberErrorCode.MEMBER_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public PrincipalDetails loadUserByUsername(String email){
        Member findMember = memberRepository
                .findByEmail(email)
                .orElseThrow(() -> MemberException.from(MEMBER_NOT_FOUND));
        AuthenticatedMember authenticatedMember = AuthenticatedMember.from(findMember);
        return PrincipalDetails.from(authenticatedMember);
    }
}

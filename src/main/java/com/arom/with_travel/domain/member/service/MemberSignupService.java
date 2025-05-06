package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberSignupService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberSignupResponseDto registerMember(String email, String oauthId, MemberSignupRequestDto requestDto) {

        Member newMember = Member.builder()
                .oauthId(oauthId)
                .email(email)
                .birth(requestDto.getBirthdate())
                .gender(requestDto.getGender())
                .loginType(Member.LoginType.KAKAO)
                .nickname(requestDto.getNickname())
                .role(Member.Role.USER)
                .build();

        Member savedMember = memberRepository.save(newMember);
        log.info("신규 가입 성공: 이메일={}, oauthId={}", email, oauthId);

        return MemberSignupResponseDto.from(savedMember);
    }

    public Member getMemberByIdOrElseThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member getMemberByEmailOrElseThrow(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public Member getByEmail(String email) {
        return getMemberByEmailOrElseThrow(email);
    }

}

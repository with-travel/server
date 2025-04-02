package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberSignupService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member registerMember(String email, MemberSignupRequestDto requestDto) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));

        if (member.getRole() == Member.Role.USER) {
            throw BaseException.from(ErrorCode.MEMBER_ALREADY_REGISTERED);
        }

        member.setNickname(requestDto.getNickname());
        member.setBirth(requestDto.getBirthdate());
        member.setGender(requestDto.getGender());
        member.setRole(Member.Role.USER);

        return memberRepository.save(member);
    }

    public Member getMemberByIdOrElseThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member getMemberByEmailOrElseThrow(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }
}

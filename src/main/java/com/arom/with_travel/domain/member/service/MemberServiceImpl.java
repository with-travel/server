package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    // userId로 유저 조회, 실패 시 에러 발생
    public Member getUserByUserIdOrElseThrow(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() ->  BaseException.from(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    // loginEmail로 유저 조회, 실패 시 에러 발생
    public Member getUserByLoginEmailOrElseThrow(String loginEmail) {
        return memberRepository.findByLoginEmail(loginEmail)
                .orElseThrow(() -> BaseException.from(ErrorCode.USER_NOT_FOUND));
    }
}

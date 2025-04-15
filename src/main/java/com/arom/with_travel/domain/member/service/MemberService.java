package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.accompanies.service.AccompanyService;
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
public class MemberService {

    private final MemberRepository memberRepository;
    private final AccompanyService accompanyService;

    // userId로 유저 조회, 실패 시 에러 발생
    public Member getUserByUserIdOrElseThrow(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() ->  BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    // loginEmail로 유저 조회, 실패 시 에러 발생
    public Member getUserByLoginEmailOrElseThrow(String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    //사용자 본인인지 확인
    public boolean isPrinciple(Member mem1, Member mem2){
        //비교하는거
        if(!mem1.getId().equals(mem2.getId())) return false;
        return true;
    }


}

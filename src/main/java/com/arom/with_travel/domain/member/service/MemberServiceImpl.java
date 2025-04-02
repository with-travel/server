package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.service.AccompanyService;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberProfileRequestDto;
import com.arom.with_travel.domain.member.dto.MemberProfileResponseDto;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AccompanyService accompanyService;

    @Override
    // userId로 유저 조회, 실패 시 에러 발생
    public Member getUserByUserIdOrElseThrow(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() ->  BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
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

    //멤버별로 동행 가져오기


    public MemberProfileResponseDto getMemberProfile(String email, MemberProfileRequestDto requestDto){
        Member member = memberRepository.findByEmail(email).get();
        Member request = memberRepository.findByEmail(requestDto.memberEmail()).get();


        return MemberProfileResponseDto.toDto(
                member.getId() == request.getId()
                ,request,accompanyService.getAccompanyByMember(member));
    }

}

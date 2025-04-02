package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberProfileRequestDto;
import com.arom.with_travel.domain.member.dto.MemberProfileResponseDto;

public interface MemberService {

    Member getUserByUserIdOrElseThrow(Long memberId);
    Member getUserByLoginEmailOrElseThrow(String loginEmail);
    MemberProfileResponseDto getMemberProfile(String email, MemberProfileRequestDto requestDto);
}

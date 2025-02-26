package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.member.Member;

public interface MemberService {

    Member getUserByUserIdOrElseThrow(Long memberId);
    Member getUserByLoginEmailOrElseThrow(String loginEmail);
}

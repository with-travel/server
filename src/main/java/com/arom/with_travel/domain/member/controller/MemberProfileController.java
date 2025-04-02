package com.arom.with_travel.domain.member.controller;

import com.arom.with_travel.domain.member.dto.MemberProfileRequestDto;
import com.arom.with_travel.domain.member.dto.MemberProfileResponseDto;
import com.arom.with_travel.domain.member.service.MemberService;
import com.arom.with_travel.global.jwt.service.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @GetMapping("/member/profile")
    public MemberProfileResponseDto getProfile(HttpServletRequest request, @RequestBody MemberProfileRequestDto requestDto){
        return memberService.getMemberProfile(tokenProvider.getMemberLoginEmail(request),requestDto);
    }
}

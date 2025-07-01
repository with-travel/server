package com.arom.with_travel.domain.member.controller;

import com.arom.with_travel.domain.member.dto.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.service.MemberService;
import com.arom.with_travel.domain.member.service.MemberSignupService;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입", description = "카카오 신규 회원 추가 정보 입력 API")
public class MemberSignupController {

    private final MemberService memberService;
    private final MemberSignupService memberSignupService;

    // 추가 정보 등록
    @PostMapping("/signup/register")
    public ResponseEntity<MemberSignupResponseDto> fillExtraInfo(
            @AuthenticationPrincipal CustomOAuth2User user,
            @RequestBody @Valid MemberSignupRequestDto req) {

        MemberSignupResponseDto dto = memberSignupService
                .fillExtraInfo(user.getEmail(), req);
        return ResponseEntity.ok(dto);
    }

    // 현재 로그인 사용자 정보 조회
    @GetMapping("/signup/register")
    public ResponseEntity<MemberSignupResponseDto> getMyInfo(
            @AuthenticationPrincipal CustomOAuth2User user) {

        MemberSignupResponseDto dto = memberSignupService
                .getSignupInfo(user.getEmail());
        return ResponseEntity.ok(dto);
    }
}

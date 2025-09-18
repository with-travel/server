package com.arom.with_travel.domain.member.controller;

import com.arom.with_travel.domain.member.dto.request.LocalLoginRequest;
import com.arom.with_travel.domain.member.dto.request.SignupWithSurveyRequestDto;
import com.arom.with_travel.domain.member.dto.response.LoginResponse;
import com.arom.with_travel.domain.member.dto.response.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.dto.response.MemberInfoResponse;
import com.arom.with_travel.domain.member.service.MemberService;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "회원가입", description = "신규 회원가입 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LocalLoginRequest req) {
        return memberService.login(req);
    }

    @PostMapping("/signup")
    public void signup(
            @RequestBody @Valid SignupWithSurveyRequestDto req) {
        memberService.signup(req);
    }

    @GetMapping("/user-info")
    public MemberInfoResponse getMyInfo(@AuthenticationPrincipal PrincipalDetails principal) {
        return memberService.getSignupInfo(principal.getUsername());
    }

    @GetMapping("/signup/check-nickname")
    public boolean checkNicknameDuplicate(@RequestParam String nickname) {
        return memberService.isNicknameDuplicated(nickname);
    }

    @GetMapping("/signup/check-email")
    public boolean checkEmailDuplicate(@RequestParam String email) {
        return memberService.isEmailAvailable(email);
    }
}

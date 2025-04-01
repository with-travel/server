package com.arom.with_travel.domain.member.controller;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.service.MemberSignupService;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
@Tag(name = "회원가입", description = "사용자 회원가입 API")
public class MemberSignupController {

    private final MemberSignupService memberSignupService;

    @GetMapping("/check")
    public ResponseEntity<?> checkUserRole(@AuthenticationPrincipal CustomOAuth2User customUser) {

        if (customUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        if (customUser.getRole() == Member.Role.GUEST) {
            return ResponseEntity.ok("GUEST; 추가 회원 가입이 필요합니다.");
        } else {
            return ResponseEntity.ok("USER; 이미 가입된 사용자입니다.");
        }
    }

    @PostMapping("/register")
    @Operation(summary = "회원 정보 등록", description = "신규 회원의 추가 정보를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 등록 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음")
    })
    public MemberSignupResponseDto registerUser(
            @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
            @RequestBody @Valid MemberSignupRequestDto requestDto
    ) {
        if (customOAuth2User == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        String email = customOAuth2User.getOAuth2Response().getEmail();

        Member member = memberSignupService.registerMember(email, requestDto);

        CustomOAuth2User newPrincipal = new CustomOAuth2User(customOAuth2User.getOAuth2Response(), Member.Role.USER);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(newPrincipal, null, newPrincipal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return MemberSignupResponseDto.from(member);
    }
}

package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.request.LocalLoginRequest;
import com.arom.with_travel.domain.member.dto.request.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.request.SignupWithSurveyRequestDto;
import com.arom.with_travel.domain.member.dto.response.LoginResponse;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.domain.survey.Survey;
import com.arom.with_travel.domain.survey.dto.request.SurveyRequestDto;
import com.arom.with_travel.domain.survey.repository.SurveyRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.jwt.dto.response.AuthTokenResponse;
import com.arom.with_travel.global.security.token.provider.JwtProvider;
import com.arom.with_travel.global.security.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LocalAuthService {

    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        boolean duplicated = memberRepository.existsByEmail(email);
        return !duplicated;
    }

    // 신규 회원 추가 정보 + 설문 통합 등록
    public LoginResponse registerWithSurvey(SignupWithSurveyRequestDto req) {

        String email = req.getExtraInfo().getEmail();
        if(!isEmailAvailable(email)) {
            throw BaseException.from(ErrorCode.DUPLICATED_EMAIL);
        }

        MemberSignupRequestDto extra = req.getExtraInfo();
        String encodedPassword = passwordEncoder.encode(extra.getPassword());

        Member member = Member.builder()
                .email(extra.getEmail())
                .password(encodedPassword)
                .name(extra.getName())
                .phone(extra.getPhone())
                .birth(extra.getBirthdate())
                .gender(extra.getGender())
                .nickname(extra.getNickname())
                .introduction(extra.getIntroduction())
                .role(Member.Role.USER)
                .additionalDataChecked(false)
                .build();

        member = memberRepository.save(member);

        SurveyRequestDto s = req.getSurvey();
        Survey survey = Survey.create(member, s);
        surveyRepository.save(survey);
        member.setSurvey(survey);

        member.markAdditionalDataChecked();

        AuthTokenResponse tokenPair = tokenService.issueTokenPair(member.getEmail());

        return new LoginResponse(
                tokenPair.getAccessToken(),
                tokenPair.getRefreshToken(),
                member.getAdditionalDataChecked()
        );
    }

    private Member getUserByLoginEmailOrElseThrow(String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    // 로그인: raw, hashed 비번 비교 → 토큰 발급
    @Transactional(readOnly = true)
    public LoginResponse login(LocalLoginRequest req) {
        Member m = memberRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> BaseException.from(ErrorCode.LOGIN_FAIL));

        if (m.getPassword() == null || !passwordEncoder.matches(req.getPassword(), m.getPassword())) {
            throw BaseException.from(ErrorCode.LOGIN_FAIL);
        }

        String access  = jwtProvider.generateAccessToken(m);
        String refresh = jwtProvider.generateRefreshToken(m);
        return new LoginResponse(access, refresh, Boolean.TRUE.equals(m.getAdditionalDataChecked()));
    }
}
package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.request.LocalLoginRequest;
import com.arom.with_travel.domain.member.dto.request.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.request.SignupWithSurveyRequestDto;
import com.arom.with_travel.domain.member.dto.response.LoginResponse;
import com.arom.with_travel.domain.member.dto.response.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.dto.response.MemberInfoResponse;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.domain.survey.Survey;
import com.arom.with_travel.domain.survey.dto.request.SurveyRequestDto;
import com.arom.with_travel.domain.survey.repository.SurveyRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.jwt.dto.response.AuthTokenResponse;
import com.arom.with_travel.global.security.token.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public LoginResponse login(LocalLoginRequest req) {
        Member member = loadMemberOrThrow(req.getEmail());
        validMemberLoginRequest(req, member);
        String access  = jwtProvider.generateAccessToken(member);
        String refresh = jwtProvider.generateRefreshToken(member);
        return new LoginResponse(access, refresh, Boolean.TRUE.equals(member.getAdditionalDataChecked()));
    }

    // userId로 유저 조회, 실패 시 에러 발생
    public Member getUserByUserIdOrElseThrow(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() ->  BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional
    public MemberSignupResponseDto signup(SignupWithSurveyRequestDto req) {
        MemberSignupRequestDto extra = req.getExtraInfo();
        SurveyRequestDto s = req.getSurvey();
        Member member = Member.create(
                extra.getNickname(),
                extra.getBirthdate(),
                extra.getGender(),
                extra.getIntroduction(),
                extra.getEmail(),
                passwordEncoder.encode(extra.getPassword()),
                extra.getName(),
                passwordEncoder.encode(extra.getPhone())
        );
        Survey survey = surveyRepository.findByMemberIdAndIsDeletedFalse(member.getId())
                .map(existing -> {
                    existing.update(s);
                    return existing;
                })
                .orElseGet(() -> Survey.create(member, s));
        member.markAdditionalDataChecked();
        memberRepository.save(member);
        surveyRepository.save(survey);
        return MemberSignupResponseDto.from(member);
    }

    @Transactional
    public MemberSignupResponseDto fillExtraInfo(String email,
                                                 MemberSignupRequestDto req) {
        Member member = loadMemberOrThrow(email);
        member.updateExtraInfo(req.getNickname(),
                req.getBirthdate(),
                req.getGender(),
                req.getIntroduction(),
                req.getEmail(),
                passwordEncoder.encode(req.getPassword()),
                req.getName(),
                passwordEncoder.encode(req.getPhone())
        );
        return MemberSignupResponseDto.from(member);
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getSignupInfo(String email) {
        Member member = loadMemberOrThrow(email);
        return MemberInfoResponse.from(member);
    }

    @Transactional(readOnly = true)
    public boolean isNicknameDuplicated(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        boolean duplicated = memberRepository.existsByEmail(email);
        return !duplicated;
    }

    private void validMemberLoginRequest(LocalLoginRequest req, Member member) {
        if (member.getPassword() == null || !passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw BaseException.from(ErrorCode.LOGIN_FAIL);
        }
    }

    private Member loadMemberOrThrow(String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

//    @Transactional
//    public SocialMemberVerificationResponse verifyMember(SocialMemberVerificationRequest req){
//        Member member = memberRepository.findByOauthId(req.getOauthId())
//                .orElseGet(() -> {
//                    Member newMember = Member.create(req.getName(), req.getEmail(), req.getOauthId());
//                    return memberRepository.save(newMember);
//                });
//        boolean isChecked = member.getAdditionalDataChecked();
//        String accessToken = jwtProvider.generateAccessToken(member);
//        String refreshToken = jwtProvider.generateRefreshToken(member);
//        log.info("[member id] : {}", member.getId());
//        log.info("[access token] : {}", accessToken);
//        log.info("[refresh token] : {}", refreshToken);
//        return new SocialMemberVerificationResponse(isChecked, accessToken, refreshToken);
//    }

//    public LoginResponse registerWithSurvey(SignupWithSurveyRequestDto req) {
//
//        String email = req.getExtraInfo().getEmail();
//        if(!isEmailAvailable(email)) {
//            throw BaseException.from(ErrorCode.DUPLICATED_EMAIL);
//        }
//
//        MemberSignupRequestDto extra = req.getExtraInfo();
//        String encodedPassword = passwordEncoder.encode(extra.getPassword());
//
//        Member member = Member.builder()
//                .email(extra.getEmail())
//                .password(encodedPassword)
//                .name(extra.getName())
//                .phone(extra.getPhone())
//                .birth(extra.getBirthdate())
//                .gender(extra.getGender())
//                .nickname(extra.getNickname())
//                .introduction(extra.getIntroduction())
//                .role(Member.Role.USER)
//                .additionalDataChecked(false)
//                .build();
//
//        member = memberRepository.save(member);
//
//        SurveyRequestDto s = req.getSurvey();
//        Survey survey = Survey.create(member, s);
//        surveyRepository.save(survey);
//        member.setSurvey(survey);
//
//        member.markAdditionalDataChecked();
//
//        AuthTokenResponse tokenPair = tokenService.issueTokenPair(member.getEmail());
//
//        return new LoginResponse(
//                tokenPair.getAccessToken(),
//                tokenPair.getRefreshToken(),
//                member.getAdditionalDataChecked()
//        );
//    }

    // 로그인: raw, hashed 비번 비교 → 토큰 발급
//    @Transactional(readOnly = true)
//    public LoginResponse login(LocalLoginRequest req) {
//        Member m = memberRepository.findByEmail(req.getEmail())
//                .orElseThrow(() -> BaseException.from(ErrorCode.LOGIN_FAIL));
//
//        if (m.getPassword() == null || !passwordEncoder.matches(req.getPassword(), m.getPassword())) {
//            throw BaseException.from(ErrorCode.LOGIN_FAIL);
//        }
//
//        String access  = jwtProvider.generateAccessToken(m);
//        String refresh = jwtProvider.generateRefreshToken(m);
//        return new LoginResponse(access, refresh, Boolean.TRUE.equals(m.getAdditionalDataChecked()));
//    }
}

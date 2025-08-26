package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.request.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.request.SignupWithSurveyRequestDto;
import com.arom.with_travel.domain.member.dto.response.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.dto.response.MemberSignupTokenResponse;
import com.arom.with_travel.domain.member.dto.response.SocialMemberVerificationResponse;
import com.arom.with_travel.domain.member.dto.request.SocialMemberVerificationRequest;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.domain.survey.Survey;
import com.arom.with_travel.domain.survey.dto.request.SurveyRequestDto;
import com.arom.with_travel.domain.survey.repository.SurveyRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import com.arom.with_travel.global.security.token.provider.JwtProvider;
import com.arom.with_travel.global.security.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberSignupService {

    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public SocialMemberVerificationResponse verifyMember(SocialMemberVerificationRequest req){
        Member member = memberRepository.findByOauthId(req.getOauthId())
                .orElseGet(() -> {
                    Member newMember = Member.create(req.getName(), req.getEmail(), req.getOauthId());
                    return memberRepository.save(newMember);
                });
        boolean isChecked = member.getAdditionalDataChecked();
        String accessToken = jwtProvider.generateAccessToken(member);
        String refreshToken = jwtProvider.generateRefreshToken(member);
        log.info("[member id] : {}", member.getId());
        log.info("[access token] : {}", accessToken);
        log.info("[refresh token] : {}", refreshToken);
        return new SocialMemberVerificationResponse(isChecked, accessToken, refreshToken);
    }

    // 신규 회원 추가 정보 + 설문 통합 등록
    public MemberSignupResponseDto registerWithSurvey(String email,
                                                      SignupWithSurveyRequestDto req) {
        Member member = getUserByLoginEmailOrElseThrow(email);

        MemberSignupRequestDto extra = req.getExtraInfo();
        member.updateExtraInfo(extra.getNickname(), extra.getBirthdate(), extra.getGender(), extra.getIntroduction());

//        req.getSurveys().forEach(sdto -> {
//            Survey survey = Survey.create(member, sdto.getAnswers());
//            surveyRepository.save(survey);
//        });

        SurveyRequestDto s = req.getSurvey(); // 단일 설문
        Survey survey = surveyRepository.findByMemberIdAndIsDeletedFalse(member.getId())
                .map(existing -> {
                    existing.update(
                            s.getEnergyLevel()
                            // 이후 섹션 늘리면 여기에 추가
                    );
                    return existing;
                })
                .orElseGet(() -> Survey.create(
                        member,
                        s.getEnergyLevel()
                        // 이후 섹션 늘리면 여기에 추가
                ));
        surveyRepository.save(survey);

        member.markAdditionalDataChecked();

        return MemberSignupResponseDto.from(member);
    }

    // 신규 회원 등록
    public Member createIfNotExists(CustomOAuth2User user) {
        return memberRepository
                .findByEmail(user.getEmail())
                .orElseGet(() -> {
                    Member inserted = Member.signUp(user.getEmail(), user.getOauthId());
                    return memberRepository.save(inserted);
                });
    }
  
    private Member getUserByLoginEmailOrElseThrow(String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    // 신규 회원 추가 정보 등록
    public MemberSignupResponseDto fillExtraInfo(String email,
                                                 MemberSignupRequestDto dto) {
        Member member = getUserByLoginEmailOrElseThrow(email);
        member.updateExtraInfo(dto.getNickname(),
                dto.getBirthdate(),
                dto.getGender(),
                dto.getIntroduction());

        return MemberSignupResponseDto.from(member);
    }

    @Transactional(readOnly = true)
    public MemberSignupTokenResponse getSignupInfo(String email) {
        Member member = getUserByLoginEmailOrElseThrow(email);
        return MemberSignupTokenResponse.from(member);
    }

    @Transactional(readOnly = true)
    public boolean isNicknameDuplicated(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}

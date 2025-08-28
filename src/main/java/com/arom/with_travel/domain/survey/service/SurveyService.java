// SurveyService.java
package com.arom.with_travel.domain.survey.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.domain.survey.Survey;
import com.arom.with_travel.domain.survey.dto.request.SurveyRequestDto;
import com.arom.with_travel.domain.survey.dto.response.SurveyResponseDto;
import com.arom.with_travel.domain.survey.repository.SurveyRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveSurvey(String email, SurveyRequestDto dto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));

        Survey survey = surveyRepository.findByMemberIdAndIsDeletedFalse(member.getId())
                .map(existing -> { existing.update(dto); return existing; })
                .orElseGet(() -> Survey.create(member, dto));

        // 기존 엔티티는 dirty checking으로 flush, 신규는 persist
        surveyRepository.save(survey);
    }

    @Transactional(readOnly = true)
    public SurveyResponseDto getSurvey(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));

        Survey survey = surveyRepository.findByMemberIdAndIsDeletedFalse(member.getId())
                .orElseThrow(() -> BaseException.from(ErrorCode.SURVEY_NOT_FOUND));

        return SurveyResponseDto.from(survey);
    }
}

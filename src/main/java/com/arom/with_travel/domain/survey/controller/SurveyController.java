// SurveyController.java
package com.arom.with_travel.domain.survey.controller;

import com.arom.with_travel.domain.survey.dto.request.SurveyRequestDto;
import com.arom.with_travel.domain.survey.dto.response.SurveyResponseDto;
import com.arom.with_travel.domain.survey.service.SurveyService;
import com.arom.with_travel.domain.survey.swagger.GetMySurveys;
import com.arom.with_travel.domain.survey.swagger.GetSingleSurvey;
import com.arom.with_travel.domain.survey.swagger.PostNewSurvey;
import com.arom.with_travel.global.security.domain.AuthenticatedMember;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SurveyController {

    private final SurveyService surveyService;

    @PostNewSurvey
    @PostMapping("/surveys")
    public void saveSurvey(@AuthenticationPrincipal PrincipalDetails principal,
                             @Valid @RequestBody SurveyRequestDto dto) {

        AuthenticatedMember member = principal.getAuthenticatedMember();
        surveyService.saveSurvey(member.getEmail(), dto);
    }

    @GetMySurveys
    @GetMapping("/surveys/my")
    public SurveyResponseDto getSurvey(@AuthenticationPrincipal PrincipalDetails principal) {

        AuthenticatedMember member = principal.getAuthenticatedMember();
        return surveyService.getSurvey(member.getEmail());
    }
}

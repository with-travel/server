package com.arom.with_travel.domain.member.dto.request;


import com.arom.with_travel.domain.survey.dto.request.SurveyRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class SignupWithSurveyRequestDto {

    @NotNull
    private MemberSignupRequestDto extraInfo;   // 닉네임·성별·생년월일

    @NotNull
    private SurveyRequestDto survey;
}

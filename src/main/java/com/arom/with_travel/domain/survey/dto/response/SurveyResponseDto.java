package com.arom.with_travel.domain.survey.dto.response;

import com.arom.with_travel.domain.survey.Survey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyResponseDto {
    private Long surveyId;
    private String answer1;
    private String answer2;
    private String answer3;

    // Entity -> ResponseDto 변환 메서드
    public static SurveyResponseDto from(Survey survey) {
        return SurveyResponseDto.builder()
                .surveyId(survey.getId())
                .answer1(survey.getAnswer1())
                .answer2(survey.getAnswer2())
                .answer3(survey.getAnswer3())
                .build();
    }
}

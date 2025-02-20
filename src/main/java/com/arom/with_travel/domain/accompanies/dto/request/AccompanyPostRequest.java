package com.arom.with_travel.domain.accompanies.dto.request;

import com.arom.with_travel.domain.accompanies.model.*;
import com.arom.with_travel.global.annotation.Enum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NotNull
@Builder
public class AccompaniesPostRequest {

    @Enum(target = Continent.class, message = "대륙을 입력해주세요")
    private Continent continentName;
    @Enum(target = Country.class, message = "국가를 입력해주세요")
    private Country countryName;
    @Enum(target = City.class, message = "도시를 입력해주세요")
    private City cityName;
    private String destination;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endTime;
    private String title;
    private String description;
    private int registerCount;
    @Enum(target = AccompanyType.class, message = "동행 종류를 선택해주세요")
    private AccompanyType accompanyType;
}

package com.arom.with_travel.domain.accompanies.dto.request;

import com.arom.with_travel.domain.accompanies.Accompanies;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NotNull
public class AccompaniesPostRequest {

    private Accompanies.Continent continentName;
    private Accompanies.Country countryName;
    private Accompanies.City cityName;
    private String destination;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endTime;
    private String title;
    private String description;
    private int registerCount;
    private Accompanies.AccompanyType accompanyType;
}

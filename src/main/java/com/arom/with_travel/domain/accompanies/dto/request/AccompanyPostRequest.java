package com.arom.with_travel.domain.accompanies.dto.request;

import com.arom.with_travel.domain.accompanies.model.*;
import com.arom.with_travel.domain.image.dto.ImageRequest;
import com.arom.with_travel.global.annotation.Enum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class AccompanyPostRequest {

    @Enum(target = Continent.class, message = "대륙을 입력해주세요(한글)")
    private Continent continent;
    @Enum(target = Country.class, message = "국가를 입력해주세요(한글)")
    private Country country;
    @Enum(target = City.class, message = "도시를 입력해주세요(한글)")
    private City city;
    @Enum(target = AccompanyType.class, message = "동행 종류를 선택해주세요")
    private AccompanyType accompanyType;

    @NotEmpty private String destination;

    @NotNull(message = "startDate는 필수입니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  // ex) 2025-08-01
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "startTime은 필수입니다.")
    @DateTimeFormat(pattern = "HH:mm")  // ex) 09:30
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "endDate는 필수입니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "endDate는 과거일 수 없습니다.")
    private LocalDate endDate;

    @NotNull(message = "endTime은 필수입니다.")
    @DateTimeFormat(pattern = "HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "HH:mm")
    private LocalTime endTime;

    @NotEmpty private String title;
    @NotEmpty private String description;
    @Min(1) private int maxParticipants;
    private List<ImageRequest> images;
}

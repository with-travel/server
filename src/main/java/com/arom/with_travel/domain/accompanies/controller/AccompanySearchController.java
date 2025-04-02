package com.arom.with_travel.domain.accompanies.controller;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.domain.accompanies.model.City;
import com.arom.with_travel.domain.accompanies.model.Continent;
import com.arom.with_travel.domain.accompanies.model.Country;
import com.arom.with_travel.domain.accompanies.service.AccompanySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accompanies/search")
public class AccompanySearchController {

    private final AccompanySearchService accompanySearchService;

    @GetMapping
    public Slice<AccompanyBriefResponse> searchByKeyword(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Continent continent,
            @RequestParam(required = false) Country country,
            @RequestParam(required = false) City city,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size
    ) {
        return accompanySearchService.getFilteredAccompanies(keyword, continent, country, city, startDate, lastId, size);
    }

    // 아직은 대륙별 검색 기능만 제공
    @GetMapping("/search")
    public List<AccompanyBriefResponse> searchByContinent(
            @RequestParam(value = "continent", required = false) Continent continent,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        return accompanySearchService.searchByContinent(continent, pageable);
    }
}

package com.arom.with_travel.domain.accompanies.service;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.domain.accompanies.model.*;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccompanySearchService {

    private final AccompanyRepository accompanyRepository;

    @Transactional(readOnly = true)
    public List<AccompanyBriefResponse> searchByContinent(Continent continent, Pageable pageable){
        return accompanyRepository.findByContinent(continent, pageable)
                .stream()
                .map(AccompanyBriefResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Slice<AccompanyBriefResponse> getFilteredAccompanies(
            String keyword,
            Continent continent,
            Country country,
            City city,
            LocalDate startDate,
            Long lastId,
            int size
    ) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return accompanyRepository.findByFiltersWithNoOffset(
                keyword,
                continent,
                country,
                city,
                startDate,
                lastId,
                pageable).map(AccompanyBriefResponse::from);
    }

}

package com.arom.with_travel.domain.accompanies.controller;

import com.arom.with_travel.domain.accompanies.dto.request.AccompanyPostRequest;
import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.domain.accompanies.dto.response.AccompanyDetailsResponse;
import com.arom.with_travel.domain.accompanies.model.City;
import com.arom.with_travel.domain.accompanies.model.Continent;
import com.arom.with_travel.domain.accompanies.model.Country;
import com.arom.with_travel.domain.accompanies.service.AccompanyService;
import com.arom.with_travel.domain.accompanies.swagger.PostNewAccompany;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accompanies")
@Tag(name = "동행", description = "동행 api 정보")
// TODO : 로그인 작업 끝난 후 로그인 객체에서 회원 정보 가져오기
public class AccompanyController {

    private final AccompanyService accompanyService;

    @PostNewAccompany
    @PostMapping("/create")
    public String createNewAccompanyPost(@RequestBody AccompanyPostRequest request) {
        return accompanyService.save(request, 1L);
    }

    @GetMapping("/{accompanyId}")
    public AccompanyDetailsResponse showAccompanyDetails(@PathVariable Long accompanyId){
        return accompanyService.showDetails(accompanyId);
    }

    @PostMapping("/{accompanyId}/like")
    public boolean doLike(@PathVariable Long accompanyId){
        return accompanyService.pressLike(accompanyId, 1L);
    }

    @PostMapping("/{accompanyId}/apply")
    public String doApply(@PathVariable Long accompanyId){
        return accompanyService.applyAccompany(accompanyId, 1L);
    }

    // TODO : 동적 쿼리를 활용해 검색 기능에 유연함 제공
    // 아직은 대륙별 검색 기능만 제공
    @GetMapping("/search")
    public List<AccompanyBriefResponse> searchByContinent(
            @RequestParam(value = "continent", required = false) Continent continent,
            @RequestParam(value = "country", required = false) Country country,
            @RequestParam(value = "city", required = false) City city,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        return accompanyService.searchByContinent(continent, pageable);
    }

//    @PostMapping("/{accompanyId}/confirm")
//    public String doConfirm(@PathVariable Long accompanyId, Boolean isConfirmed){
//        return accompaniesService.confirmApply(accompanyId, 1L, 2L);
//    }
}

package com.arom.with_travel.domain.accompanies.controller;

import com.arom.with_travel.domain.accompanies.dto.request.AccompaniesPostRequest;
import com.arom.with_travel.domain.accompanies.dto.response.AccompaniesDetailsResponse;
import com.arom.with_travel.domain.accompanies.service.AccompaniesService;
import com.arom.with_travel.domain.accompanies.swagger.PostNewAccompany;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accompanies")
@Tag(name = "동행", description = "동행 api 정보")
// TODO : 로그인 작업 끝난 후 로그인 객체에서 회원 정보 가져오기
public class AccompaniesController {

    private final AccompaniesService accompaniesService;

    @PostNewAccompany
    @PostMapping("/create")
    public String createNewAccompanyPost(@RequestBody AccompaniesPostRequest request) {
        return accompaniesService.save(request, 1L);
    }

    @GetMapping("/{accompanyId}")
    public AccompaniesDetailsResponse showAccompanyDetails(@PathVariable Long accompanyId){
        return accompaniesService.showDetails(accompanyId);
    }

    @PostMapping("/{accompanyId}/like")
    public boolean doLike(@PathVariable Long accompanyId){
        return accompaniesService.pressLike(accompanyId, 1L);
    }

    @PostMapping("/{accompanyId}/apply")
    public String doApply(@PathVariable Long accompanyId){
        return accompaniesService.applyAccompany(accompanyId, 1L);
    }

//    @PostMapping("/{accompanyId}/confirm")
//    public String doConfirm(@PathVariable Long accompanyId, Boolean isConfirmed){
//        return accompaniesService.confirmApply(accompanyId, 1L, 2L);
//    }
}

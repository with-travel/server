package com.arom.with_travel.domain.accompanies.controller;

import com.arom.with_travel.domain.accompanies.dto.request.AccompanyPostRequest;
import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.domain.accompanies.dto.response.AccompanyDetailsResponse;
import com.arom.with_travel.domain.accompanies.dto.response.CursorSliceResponse;
import com.arom.with_travel.domain.accompanies.model.Country;
import com.arom.with_travel.domain.accompanies.service.AccompanyApplyService;
import com.arom.with_travel.domain.accompanies.service.AccompanyService;
import com.arom.with_travel.domain.accompanies.swagger.*;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accompanies")
@Tag(name = "동행 모집 글", description = "동행 모집 글 CRUD api")
public class AccompanyController {

    private final AccompanyService accompanyService;
    private final AccompanyApplyService accompanyApplyService;

    @PostNewAccompany
    @PostMapping
    public String createNewAccompanyPost(@AuthenticationPrincipal CustomOAuth2User user,
                                         @RequestBody @Valid AccompanyPostRequest request) {
        return accompanyService.createAccompany(request, user.getOauthId());
    }

    @GetAccompanyDetails
    @GetMapping("/{accompanyId}")
    public AccompanyDetailsResponse showAccompanyDetails(@PathVariable Long accompanyId){
        return accompanyService.showAccompanyDetails(accompanyId);
    }

    @PatchAccompanyLike
    @PatchMapping("/{accompanyId}/like")
    public boolean doLike(@AuthenticationPrincipal CustomOAuth2User user,
                          @PathVariable Long accompanyId){
        return accompanyService.toggleLike(accompanyId, user.getOauthId());
    }

    @PostAccompanyApply
    @PostMapping("/{accompanyId}/apply")
    public String doApply(@AuthenticationPrincipal CustomOAuth2User user,
                          @PathVariable Long accompanyId){
        return accompanyApplyService.applyAccompany(accompanyId, user.getOauthId());
    }

    @GetAccompaniesBrief
    @GetMapping
    public CursorSliceResponse<AccompanyBriefResponse> showAccompaniesBrief(
            @RequestParam(value = "country", required = false) Country country,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size){
        return accompanyService.showAccompaniesBrief(country, size, lastId);
    }
}

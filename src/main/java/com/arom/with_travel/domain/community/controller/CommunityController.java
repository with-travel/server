package com.arom.with_travel.domain.community.controller;

import com.arom.with_travel.domain.community.dto.CommunityCreateRequest;
import com.arom.with_travel.domain.community.dto.CommunityDetailResponse;
import com.arom.with_travel.domain.community.dto.CommunityListItemResponse;
import com.arom.with_travel.domain.community.dto.CommunityUpdateRequest;
import com.arom.with_travel.domain.community.service.CommunityService;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import com.arom.with_travel.global.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communities")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping
    public Long create(@AuthenticationPrincipal PrincipalDetails principal,
                       @RequestBody @Valid CommunityCreateRequest req) {
        String me = principal.getAuthenticatedMember().getEmail();
        return communityService.create(me, req);
    }

    @GetMapping("/{id}")
    public CommunityDetailResponse detail(@PathVariable Long id) {
        return communityService.readAndIncreaseView(id);
    }

    @GetMapping
    public Page<CommunityListItemResponse> list(
            @RequestParam(required = false) String continent,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false, name = "q") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return communityService.search(continent, country, city, keyword, pageable);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid CommunityUpdateRequest req) {
        Long me = SecurityUtils.currentMemberIdOrThrow();
        communityService.update(me, id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Long me = SecurityUtils.currentMemberIdOrThrow();
        communityService.delete(me, id);
    }
}
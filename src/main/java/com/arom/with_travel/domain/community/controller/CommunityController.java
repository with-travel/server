package com.arom.with_travel.domain.community.controller;

import com.arom.with_travel.domain.community.dto.CommunityCreateRequest;
import com.arom.with_travel.domain.community.dto.CommunityDetailResponse;
import com.arom.with_travel.domain.community.dto.CommunityListItemResponse;
import com.arom.with_travel.domain.community.dto.CommunityUpdateRequest;
import com.arom.with_travel.domain.community.enums.CommunityTag;
import com.arom.with_travel.domain.community.service.CommunityService;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communities")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping
    public Long create(
            @AuthenticationPrincipal PrincipalDetails principal,
            @RequestBody @Valid CommunityCreateRequest req
    ) {
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
            @RequestParam(required = false, name = "tag") CommunityTag tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return communityService.search(continent, country, city, keyword, tag, pageable);
    }

    @PatchMapping("/{id}")
    public CommunityDetailResponse update(
            @PathVariable Long id,
            @RequestBody @Valid CommunityUpdateRequest req,
            @AuthenticationPrincipal PrincipalDetails principal
    ) {
        Long me = principal.getAuthenticatedMember().getMemberId();
        return communityService.update(me, id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal PrincipalDetails principal
    ) {
        Long me = principal.getAuthenticatedMember().getMemberId();
        communityService.delete(me, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tags/{tag}")
    public Page<CommunityListItemResponse> listByTag(
            @PathVariable CommunityTag tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        int limitedSize = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(
                page,
                limitedSize,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return communityService.listByTag(tag, pageable);
    }

}
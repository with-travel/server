package com.arom.with_travel.domain.accompanies.controller;

import com.arom.with_travel.domain.accompanies.dto.request.AccompanyNewCommentRequest;
import com.arom.with_travel.domain.accompanies.dto.request.AccompanyUpdateCommentRequest;
import com.arom.with_travel.domain.accompanies.dto.response.AccompanyCommentSliceResponse;
import com.arom.with_travel.domain.accompanies.service.AccompanyCommentService;
import com.arom.with_travel.domain.accompanies.swagger.DeleteAccompanyComment;
import com.arom.with_travel.domain.accompanies.swagger.GetAccompanyComments;
import com.arom.with_travel.domain.accompanies.swagger.PatchAccompanyComment;
import com.arom.with_travel.domain.accompanies.swagger.PostAccompanyComment;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accompanies/{accompanyId}/comments")
@Tag(name = "동행 후기", description = "동행 후기 글 CRUD api")
public class AccompanyCommentController {

    private final AccompanyCommentService accompanyCommentService;

    @PostAccompanyComment
    @PostMapping
    public void createComments(@AuthenticationPrincipal CustomOAuth2User user,
                               @PathVariable Long accompanyId,
                               @RequestBody @Valid AccompanyNewCommentRequest request){
        accompanyCommentService.addCommentToAccompany(accompanyId, user.getOauthId(), request);
    }

    @GetAccompanyComments
    @GetMapping
    public AccompanyCommentSliceResponse getComments(
            @PathVariable Long accompanyId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size) {
        return accompanyCommentService.getComments(accompanyId, lastCreatedAt, lastId, size);
    }

    @PatchAccompanyComment
    @PatchMapping
    public void modifyComment(@AuthenticationPrincipal CustomOAuth2User user,
                              @PathVariable Long accompanyId,
                              @RequestBody @Valid AccompanyUpdateCommentRequest request){
        accompanyCommentService.updateComment(accompanyId, user.getOauthId() ,request);
    }

    @DeleteAccompanyComment
    @DeleteMapping
    public void removeComment(@AuthenticationPrincipal CustomOAuth2User user,
                              @PathVariable Long accompanyId){
        accompanyCommentService.deleteComment(accompanyId, user.getOauthId());
    }
}

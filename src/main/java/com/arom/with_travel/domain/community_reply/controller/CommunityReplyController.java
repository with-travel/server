package com.arom.with_travel.domain.community_reply.controller;

import com.arom.with_travel.domain.community_reply.dto.ReplyCreateRequest;
import com.arom.with_travel.domain.community_reply.dto.ReplyResponse;
import com.arom.with_travel.domain.community_reply.dto.ReplyUpdateRequest;
import com.arom.with_travel.domain.community_reply.service.CommunityReplyService;
import com.arom.with_travel.global.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommunityReplyController {

    private final CommunityReplyService replyService;

    @PostMapping("/communities/{communityId}/replies")
    public Long write(@PathVariable Long communityId, @RequestBody @Valid ReplyCreateRequest req) {
        Long me = SecurityUtils.currentMemberIdOrThrow();
        return replyService.write(me, communityId, req);
    }

    @GetMapping("/communities/{communityId}/replies")
    public Slice<ReplyResponse> list(
            @PathVariable Long communityId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        Long me = null;
        try { me = SecurityUtils.currentMemberIdOrThrow(); } catch (Exception ignore) {}

        int MAX_SIZE = 100;
        int safeSize = Math.min(size, MAX_SIZE);

        Pageable pageable = PageRequest.of(page, safeSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        return replyService.listSlice(me, communityId, pageable);
    }

    @PatchMapping("/replies/{replyId}")
    public void update(@PathVariable Long replyId, @RequestBody @Valid ReplyUpdateRequest req) {
        Long me = SecurityUtils.currentMemberIdOrThrow();
        replyService.update(me, replyId, req);
    }

    @DeleteMapping("/replies/{replyId}")
    public void delete(@PathVariable Long replyId) {
        Long me = SecurityUtils.currentMemberIdOrThrow();
        replyService.delete(me, replyId);
    }

    @PostMapping("/replies/{replyId}/likes")
    public void like(@PathVariable Long replyId) {
        Long me = SecurityUtils.currentMemberIdOrThrow();
        replyService.like(me, replyId);
    }

    @DeleteMapping("/replies/{replyId}/likes")
    public void unlike(@PathVariable Long replyId) {
        Long me = SecurityUtils.currentMemberIdOrThrow();
        replyService.unlike(me, replyId);
    }
}

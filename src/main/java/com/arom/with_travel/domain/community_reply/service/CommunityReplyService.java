package com.arom.with_travel.domain.community_reply.service;

import com.arom.with_travel.domain.community.Community;
import com.arom.with_travel.domain.community.repository.CommunityRepository;
import com.arom.with_travel.domain.community_reply.CommunityReply;
import com.arom.with_travel.domain.community_reply.CommunityReplyLike;
import com.arom.with_travel.domain.community_reply.dto.ReplyCreateRequest;
import com.arom.with_travel.domain.community_reply.dto.ReplyResponse;
import com.arom.with_travel.domain.community_reply.dto.ReplyUpdateRequest;
import com.arom.with_travel.domain.community_reply.repository.CommunityReplyLikeRepository;
import com.arom.with_travel.domain.community_reply.repository.CommunityReplyRepository;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityReplyService {
    private final CommunityRepository communityRepository;
    private final CommunityReplyRepository replyRepository;
    private final CommunityReplyLikeRepository likeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long write(Long currentMemberId, Long communityId, ReplyCreateRequest req) {
        Member writer = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> BaseException.from(ErrorCode.COMMUNITY_NOT_FOUND));

        CommunityReply saved = replyRepository.save(CommunityReply.create(community, writer, req.getContent()));
        return saved.getId();
    }

    @Transactional
    public void update(Long currentMemberId, Long replyId, ReplyUpdateRequest req) {
        CommunityReply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> BaseException.from(ErrorCode.REPLY_NOT_FOUND));
        if (!reply.getMember().getId().equals(currentMemberId)) {
            throw BaseException.from(ErrorCode.REPLY_FORBIDDEN);
        }
        reply.changeContent(req.getContent());
    }

    @Transactional
    public void delete(Long currentMemberId, Long replyId) {
        CommunityReply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> BaseException.from(ErrorCode.REPLY_NOT_FOUND));
        if (!reply.getMember().getId().equals(currentMemberId)) {
            throw BaseException.from(ErrorCode.REPLY_FORBIDDEN);
        }
        replyRepository.delete(reply);
    }

    @Transactional(readOnly = true)
    public Slice<ReplyResponse> listSlice(Long currentMemberIdOrNull, Long communityId, Pageable pageable) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> BaseException.from(ErrorCode.COMMUNITY_NOT_FOUND));

        return replyRepository.findByCommunityOrderByCreatedAtAsc(community, pageable)
                .map(r -> new ReplyResponse(
                        r.getId(),
                        r.getMember().getId(),
                        r.getMember().getNickname(),
                        r.getContent(),
                        likeRepository.countByReplyId(r.getId()),
                        currentMemberIdOrNull != null &&
                        likeRepository.existsByReplyIdAndMemberId(r.getId(), currentMemberIdOrNull),
                        r.getCreatedAt().toString()
                ));
    }

    @Transactional
    public void like(Long currentMemberId, Long replyId) {
        CommunityReply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> BaseException.from(ErrorCode.REPLY_NOT_FOUND));
        if (!likeRepository.existsByReplyIdAndMemberId(replyId, currentMemberId)) {
            Member me = memberRepository.findById(currentMemberId)
                    .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
            likeRepository.save(CommunityReplyLike.of(reply, me));
        }
    }

    @Transactional
    public void unlike(Long currentMemberId, Long replyId) {
        likeRepository.deleteByReplyIdAndMemberId(replyId, currentMemberId);
    }
}

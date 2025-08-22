package com.arom.with_travel.domain.community_reply.repository;

import com.arom.with_travel.domain.community_reply.CommunityReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityReplyLikeRepository extends JpaRepository<CommunityReplyLike, Long> {
    boolean existsByReplyIdAndMemberId(Long replyId, Long memberId);
    long countByReplyId(Long replyId);
    void deleteByReplyIdAndMemberId(Long replyId, Long memberId);
}
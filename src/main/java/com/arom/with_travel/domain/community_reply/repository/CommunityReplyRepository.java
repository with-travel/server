package com.arom.with_travel.domain.community_reply.repository;

import com.arom.with_travel.domain.community.Community;
import com.arom.with_travel.domain.community_reply.CommunityReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityReplyRepository extends JpaRepository<CommunityReply, Long> {

    @EntityGraph(attributePaths = {"member"})
    Page<CommunityReply> findByCommunity(Community community, Pageable pageable);
    Slice<CommunityReply> findByCommunityOrderByCreatedAtAsc(Community community, Pageable pageable);
}

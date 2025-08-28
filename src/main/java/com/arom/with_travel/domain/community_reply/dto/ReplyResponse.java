package com.arom.with_travel.domain.community_reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponse {
    Long id;
    Long memberId;
    String memberNickname;
    String content;
    long likeCount;
    boolean likedByMe;
    String createdAt;
}

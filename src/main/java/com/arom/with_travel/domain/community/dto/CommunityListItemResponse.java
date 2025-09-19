package com.arom.with_travel.domain.community.dto;

import com.arom.with_travel.domain.community.enums.CommunityTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityListItemResponse {
    Long id;
    String title;
    String snippet;
    CommunityTag tag;
    String continent;
    String country;
    String city;
    Long writerId;
    String writerNickname;
    long viewCount;
    long likeCount;
    long replyCount;
    String createdAt;
}

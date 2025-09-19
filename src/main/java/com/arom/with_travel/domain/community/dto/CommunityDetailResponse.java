package com.arom.with_travel.domain.community.dto;

import com.arom.with_travel.domain.community.enums.CommunityTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityDetailResponse {
    Long id;
    String title;
    String content;
    private CommunityTag tag;
    String continent;
    String country;
    String city;
    Long writerId;
    String writerNickname;
    long viewCount;
    long likeCount;
    long replyCount;
    List<String> imageUrls;
    String createdAt;
    String updatedAt;
}

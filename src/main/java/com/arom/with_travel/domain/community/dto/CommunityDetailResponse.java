package com.arom.with_travel.domain.community.dto;

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
    String continent;
    String country;
    String city;
    Long writerId;
    String writerNickname;
    long viewCount;
    List<String> imageUrls;
    String createdAt;
    String updatedA;
}

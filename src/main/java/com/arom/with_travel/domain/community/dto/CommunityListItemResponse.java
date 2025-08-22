package com.arom.with_travel.domain.community.dto;

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
    String continent;
    String country;
    String city;
    Long writerId;
    String writerNickname;
    long viewCount;
    String createdAt;
}

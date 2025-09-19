package com.arom.with_travel.domain.community.dto;

import com.arom.with_travel.domain.community.Community;
import com.arom.with_travel.domain.image.Image;

public class CommunityDetailResponseMapper {

    public static CommunityDetailResponse from(Community c) {
        return new CommunityDetailResponse(
                c.getId(),
                c.getTitle(),
                c.getContent(),
                c.getTag(),
                c.getContinent(),
                c.getCountry(),
                c.getCity(),
                c.getMember().getId(),
                c.getMember().getNickname(),
                c.getViewCount(),
                c.getLikeCount(),
                c.getReplyCount(),
                c.getImages().stream().map(Image::getImageUrl).toList(),
                c.getCreatedAt().toString(),
                c.getUpdatedAt().toString()
        );
    }
}

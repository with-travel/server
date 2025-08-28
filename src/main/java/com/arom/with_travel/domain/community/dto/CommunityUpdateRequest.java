package com.arom.with_travel.domain.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityUpdateRequest {
    private String title;
    private String content;
    private String continent;
    private String country;
    private String city;
    private List<ImageUpdate> images;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageUpdate {
        private String imageName;
        private String imageUrl;
    }
}

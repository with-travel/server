package com.arom.with_travel.domain.community.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityCreateRequest {
    @NotEmpty @Size(max = 120) String title;
    @NotEmpty String content;
    @NotEmpty String continent;
    @NotEmpty String country;
    @NotEmpty String city;
    private List<ImageCreate> images;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageCreate {
        private String imageName;
        private String imageUrl;
    }
}

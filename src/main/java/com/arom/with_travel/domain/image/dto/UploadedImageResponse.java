package com.arom.with_travel.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadedImageResponse {
    private String originalFileName;
    private String imageUrl;
}
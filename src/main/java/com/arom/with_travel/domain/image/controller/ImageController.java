package com.arom.with_travel.domain.image.controller;

import com.arom.with_travel.domain.image.PostUploadImages;
import com.arom.with_travel.domain.image.dto.UploadedImageResponse;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import com.arom.with_travel.global.s3.service.S3Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@Tag(name = "이미지", description = "이미지 업로드 api")
public class ImageController {

    private final S3Service s3Service;

    @PostUploadImages
    @PostMapping("/upload")
    public List<UploadedImageResponse> uploadAccompanyImages(@AuthenticationPrincipal CustomOAuth2User user,
                                                             @RequestParam("files") List<MultipartFile> files,
                                                             @RequestParam("dir") String directory) throws IOException {
        return s3Service.uploadFiles(files, directory);
    }
}
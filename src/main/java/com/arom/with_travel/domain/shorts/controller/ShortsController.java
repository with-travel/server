package com.arom.with_travel.domain.shorts.controller;

import com.arom.with_travel.global.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shorts")
public class ShortsController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public String upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("dir") String directory) throws IOException {
        log.info("Uploading file {}", file.getOriginalFilename());
        return s3Service.uploadFile(file, directory);
    }
}

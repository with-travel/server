package com.arom.with_travel.global.s3.service;

import com.arom.with_travel.domain.image.dto.UploadedImageResponse;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.s3.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.arom.with_travel.global.s3.properties.S3Properties.ALLOWED_IMAGE_TYPES;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Transactional
    public List<UploadedImageResponse> uploadFiles(List<MultipartFile> files, String directory) throws IOException {
        List<UploadedImageResponse> uploadedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            validateFileType(file);
            String fileKey = getFileKey(directory);
            PutObjectRequest request = createUploadObject(file, fileKey);
            upload(file, request);
            String url = getUploadObjectUrl(fileKey);
            uploadedImages.add(new UploadedImageResponse(file.getOriginalFilename(), url));
        }
        return uploadedImages;
    }

    private String getUploadObjectUrl(String fileKey) {
        return s3Client
                .utilities()
                .getUrl(builder -> builder
                        .bucket(S3Properties.BUCKET_NAME)
                        .key(fileKey))
                .toString();
    }

    private void upload(MultipartFile file, PutObjectRequest request) throws IOException {
        s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }

    private PutObjectRequest createUploadObject(MultipartFile file, String fileKey) {
        return PutObjectRequest.builder()
                .bucket(S3Properties.BUCKET_NAME)
                .key(fileKey)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();
    }

    private void validateFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType)) {
            throw BaseException.from(ErrorCode.INVALID_IMG_TYPE);
        }
    }

    private String getFileKey(String bucketDirectory) {
        return bucketDirectory +
                "/" +
                UUID.randomUUID();
    }
}
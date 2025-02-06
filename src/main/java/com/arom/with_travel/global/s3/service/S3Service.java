package com.arom.with_travel.global.s3.service;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.s3.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final S3Client s3Client;

    @Transactional
    public String uploadFile(final MultipartFile file, String directory) throws IOException {
        validateFileType(file);
        String fileKey = getFileKey(directory);
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(S3Properties.BUCKET_NAME)
                .key(fileKey)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();
        s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return s3Client
                .utilities()
                .getUrl(builder -> builder
                        .bucket(S3Properties.BUCKET_NAME)
                        .key(fileKey))
                .toString();
    }


    // TODO : 파일 형식 추후 지정 및 소프트 코딩
    private void validateFileType(MultipartFile file) {
        if (file.getContentType().equals("image/vnd.dwg")){
            throw BaseException.from(ErrorCode.TMP_ERROR);
        }
    }

    private String getFileKey(String bucketDirectory) {
        return bucketDirectory +
                "/" +
                UUID.randomUUID();
    }
}
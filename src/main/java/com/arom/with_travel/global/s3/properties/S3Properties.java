package com.arom.with_travel.global.s3.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class S3Properties {

    public static String BUCKET_NAME;
    public static final Set<String> ALLOWED_IMAGE_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp",
            "image/bmp"
    );

    public S3Properties(
            @Value("${cloud.aws.s3.bucket}") String bucketName
    ) {
        BUCKET_NAME = bucketName;
    }
}
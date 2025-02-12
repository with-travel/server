package com.arom.with_travel.global.s3.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3Properties {

    public static String BUCKET_NAME;
    public S3Properties(
            @Value("${cloud.aws.s3.bucket}") String bucketName
    ) {
        BUCKET_NAME = bucketName;
    }
}
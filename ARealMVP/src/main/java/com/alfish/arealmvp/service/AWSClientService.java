package com.alfish.arealmvp.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class AWSClientService {

    private static final Logger LOG = LogManager.getLogger(MailService.class);

    private final ConfigProvider cfg;
    private final S3Client client;
    private final S3Waiter waiter;

    @Autowired
    public AWSClientService(ConfigProvider cfg) {
        this.cfg = cfg;
        client = S3Client.builder().credentialsProvider(getProvider(cfg.S3_ACCESS_KEY(), cfg.S3_SECRET_KEY())).region(Region.EU_CENTRAL_1).build();
        waiter = client.waiter();
    }

    public String fetchObject(String key) {
        try {
            GetObjectRequest request = GetObjectRequest.builder().bucket(cfg.S3_BUCKET()).key(key).build();
            ResponseInputStream<GetObjectResponse> response = client.getObject(request);
            byte[] objectBytes = response.readAllBytes();
            response.close();
            return new String(objectBytes, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    public void publishObject(String key, String content) {
        PutObjectRequest request = PutObjectRequest.builder().bucket(cfg.S3_BUCKET()).key(key).build();
        client.putObject(request, RequestBody.fromString(content));
    }

    private AwsCredentialsProvider getProvider(String accessKey, String secretKey) {
        return () -> AwsBasicCredentials.create(accessKey, secretKey);
    }

}

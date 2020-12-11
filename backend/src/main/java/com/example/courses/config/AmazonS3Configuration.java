package com.example.courses.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Configuration {

    @Value("${amazon.s3.secret-key}")
    private String awsSecretKey;

    @Value("${amazon.s3.access-key}")
    private String awsAccessKey;

    @Bean
    public AmazonS3 buildAmazonS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}

package com.example.finalexamplespring.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class S3Config {
    @Value("${cloud.ncp.access.key}") // application.properties에서 aws.accessKey 설정
    private String accessKey;

    @Value("${cloud.ncp.secret.key}") // application.properties에서 aws.secretKey 설정
    private String secretKey;

    @Value("${cloud.aws.region.static}") // application.properties에서 aws.s3.region 설정
    private String region;

    @Value("${cloud.aws.s3.endpoint}") // application.properties에서 aws.s3.endpointUrl 설정
    private String endPoint;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucket;

    @Bean
    public AmazonS3 amazonS3Client() {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
        return s3;
    }
}

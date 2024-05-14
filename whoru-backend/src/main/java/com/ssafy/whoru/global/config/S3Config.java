package com.ssafy.whoru.global.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@AllArgsConstructor
public class S3Config {
    @Bean
    public S3Client s3ClientInitializer(){
        return S3Client.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(
                AwsCredentialsProviderChain.builder()
                    .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
                    .addCredentialsProvider(SystemPropertyCredentialsProvider.create())
                    .build()
        ).build();
    }
}

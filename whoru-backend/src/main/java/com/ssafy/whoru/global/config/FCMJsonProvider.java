package com.ssafy.whoru.global.config;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;

@Configuration
@Slf4j
public class FCMJsonProvider {

    private final String type;
    private final String projectId;
    private final String privateKeyId;
    private final String privateKey;
    private final String clientEmail;
    private final String clientId;
    private final String authUri;
    private final String tokenUri;
    private final String authProviderX509CertUrl;
    private final String clientX509CertUrl;
    private final String universeDomain;


    public FCMJsonProvider(
            @Value("${fcm.type}") String type,
            @Value("${fcm.project.id}") String projectId,
            @Value("${fcm.private.id}") String privateKeyId,
            @Value("${fcm.private.key}") String privateKey,
            @Value("${fcm.client.email}") String clientEmail,
            @Value("${fcm.client.id}") String clientId,
            @Value("${fcm.auth.uri}") String authUri,
            @Value("${fcm.token.uri}") String tokenUri,
            @Value("${fcm.auth.provider.x509.cert.url}") String authProviderX509CertUrl,
            @Value("${fcm.client.x509.cert.url}") String clientX509CertUrl,
            @Value("${fcm.universe.domain}") String universeDomain
    ){
        this.type = type;
        this.projectId = projectId;
        this.privateKeyId = privateKeyId;
        this.privateKey = privateKey;
        this.clientEmail = clientEmail;
        this.clientId = clientId;
        this.authUri = authUri;
        this.tokenUri = tokenUri;
        this.authProviderX509CertUrl = authProviderX509CertUrl;
        this.clientX509CertUrl = clientX509CertUrl;
        this.universeDomain = universeDomain;
    }

    @Bean
    @Qualifier("fcmInputStream")
    public InputStream getInputStream(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JsonGenerator generator = getJsonGenerator(byteArrayOutputStream);
        makeFCMJson(generator);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private JsonGenerator getJsonGenerator(OutputStream outputStream){
        try{
            return new JsonFactory().createGenerator(outputStream);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private void makeFCMJson(JsonGenerator generator){
        try{
            generator.writeStartObject();
            generator.writeStringField("type", type);
            generator.writeStringField("project_id", projectId);
            generator.writeStringField("private_key_id", privateKeyId);
            generator.writeStringField("private_key", privateKey);
            generator.writeStringField("client_email", clientEmail);
            generator.writeStringField("client_id", clientId);
            generator.writeStringField("auth_uri", authUri);
            generator.writeStringField("token_uri", tokenUri);
            generator.writeStringField("auth_provider_x509_cert_url", authProviderX509CertUrl);
            generator.writeStringField("client_x509_cert_url", clientX509CertUrl);
            generator.writeStringField("universe_domain",universeDomain);
            generator.writeEndObject();
            generator.close();
        }catch(IOException e){
            log.warn(e.getMessage());
            new RuntimeException(e.getMessage());
        }
    }


    @Override
    public String toString() {
        return "FCMJsonProvider{" +
                "type='" + type + '\'' +
                ", projectId='" + projectId + '\'' +
                ", privateKeyId='" + privateKeyId + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", clientId='" + clientId + '\'' +
                ", authUri='" + authUri + '\'' +
                ", tokenUri='" + tokenUri + '\'' +
                ", authProviderX509CertUrl='" + authProviderX509CertUrl + '\'' +
                ", clientX509CertUrl='" + clientX509CertUrl + '\'' +
                ", universeDomain='" + universeDomain + '\'' +
                '}';
    }
}
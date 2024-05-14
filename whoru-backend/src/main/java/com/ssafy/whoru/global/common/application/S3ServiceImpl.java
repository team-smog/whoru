package com.ssafy.whoru.global.common.application;

import com.ssafy.whoru.global.common.dto.S3PathType;
import com.ssafy.whoru.global.common.exception.InvalidFileStreamException;
import com.ssafy.whoru.global.common.exception.S3UploadException;
import java.util.UUID;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements S3Service {

    private final S3Client s3;

    private static final String USED_BUCKET_NAME = "whoru";

    private static final String s3UrlPrefix = "https://whoru.s3.ap-northeast-2.amazonaws.com/";

    @Override
    public Optional<String> upload(MultipartFile file, S3PathType pathType) {
        StringBuilder fileName = new StringBuilder();
        String resultFileName = makeFileName(file.getOriginalFilename());
        fileName.append(pathType.getS3Path()).append(resultFileName);
        try{
            log.info("file info : {}", file.getContentType());
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(USED_BUCKET_NAME)
                .key(fileName.toString())
                .contentType(file.getContentType())
                .contentDisposition("inline")
                .build();
            s3.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        }catch(IOException e){
            throw new InvalidFileStreamException();
        }catch(SdkException e){
            log.info("s3 Exception {}", e);
            throw new S3UploadException();
        }
        return Optional.of(uploadUrlMaker(pathType.getS3Path(),resultFileName));
    }

    private String makeFileName(String originalFileName){
        StringBuilder sb= new StringBuilder();
        sb.append(System.currentTimeMillis()).append(UUID.randomUUID()).append(originalFileName);
        return sb.toString();
    }

    private String uploadUrlMaker(String s3Path, String uploadedFileName) {
        StringBuilder sb = new StringBuilder(s3UrlPrefix);
        sb.append(s3Path).append(uploadedFileName);
        return sb.toString();
    }
}

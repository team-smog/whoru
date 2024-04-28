package com.ssafy.whoru.global.common.application;

import com.ssafy.whoru.global.common.dto.S3PathType;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

public interface S3Service {

    Optional<String> upload(MultipartFile file, S3PathType pathType);

}

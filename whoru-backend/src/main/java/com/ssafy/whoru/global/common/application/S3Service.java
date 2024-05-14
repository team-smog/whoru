package com.ssafy.whoru.global.common.application;

import com.ssafy.whoru.global.common.dto.S3PathType;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    Optional<String> upload(MultipartFile file, S3PathType pathType);

}

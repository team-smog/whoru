package com.ssafy.whoru.global.common.dto;

import com.ssafy.whoru.domain.message.dto.ContentType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public enum FileType {

    IMG(ContentType.image, Arrays.asList(MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE), S3PathType.MESSAGE_IMG_PATH),

    VOICE(ContentType.voice, Arrays.asList("audio/mpeg", "audio/wav", "audio/weba"), S3PathType.MESSAGE_VOICE_PATH)
    ;

    final ContentType contentType;
    final List<String> mediaType;
    final S3PathType s3PathType;

    public static Optional<FileType> getFileType(MultipartFile file) throws RuntimeException{
        return Arrays.stream(FileType.values())
            .filter(type -> type.mediaType.contains(file.getContentType()))
            .findAny();
    }
}

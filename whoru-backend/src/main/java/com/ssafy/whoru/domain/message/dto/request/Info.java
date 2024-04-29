package com.ssafy.whoru.domain.message.dto.request;

import com.ssafy.whoru.domain.message.dto.ContentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "IMAGE / VOICE 메세지 전송 DTO")
@Builder
@Getter
public class Info {

    @Schema(description = "메세지 타입", pattern = "[ text,  image, voice ]")
    ContentType contentType;

    @Schema(description = "메세지 작성자 고유번호")
    Long senderId;

}

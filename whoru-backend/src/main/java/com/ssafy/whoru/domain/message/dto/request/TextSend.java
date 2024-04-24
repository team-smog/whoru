package com.ssafy.whoru.domain.message.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Text 메세지 전송 DTO")
@Builder
@Getter
public class TextSend {

    @Schema(description = "메세지 작성자 고유번호")
    private Long senderId;

    @Schema(description = "Text 메세지 내용")
    private String content;
}

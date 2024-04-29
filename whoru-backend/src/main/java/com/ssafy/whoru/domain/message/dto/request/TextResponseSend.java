package com.ssafy.whoru.domain.message.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Text 답장 메세지 전송 DTO")
@Builder
@Getter
public class TextResponseSend {

    @Schema(description = "메세지 작성자 고유번호")
    Long senderId;

    @Schema(description = "Text 메세지 내용")
    String content;

    @Schema(description = "대상 메세지 고유번호")
    Long messageId;
}

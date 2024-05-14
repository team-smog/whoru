package com.ssafy.whoru.domain.message.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Schema(description = "Text 메세지 전송 DTO")
@Builder
@Getter
@ToString
public class TextSend {

    @Size(min = 2, message = "메세지 내용이 너무 짧습니다.")
    @Size(max = 200, message = "메세지 내용이 너무 많습니다.")
    @Schema(description = "Text 메세지 내용")
    private String content;

    @JsonCreator // Jackson single field issue
    public TextSend(String content){
        this.content = content;
    }
}

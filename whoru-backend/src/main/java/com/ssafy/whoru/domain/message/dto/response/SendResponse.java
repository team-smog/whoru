package com.ssafy.whoru.domain.message.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Schema(description = "Text 혹은 VOICE, IMAGE 메세지 전송 후 응답 DTO")
public class SendResponse {

    @Schema(description = "랜덤박스 획득 여부", pattern = "[ true: 얻음, false: 더이상 얻을 수 없음 ]")
    private boolean randomBoxProvided;
}

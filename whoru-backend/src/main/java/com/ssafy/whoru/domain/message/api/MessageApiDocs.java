package com.ssafy.whoru.domain.message.api;

/*
* Author 김회창
* */

import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.domain.message.dto.response.MessageResponse;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "메세지 API", description = "메세지 전송 및 조회에 관한 Controller")
public interface MessageApiDocs {

    @Operation(summary = "Text 메세지 전송", description = "Text 메세지를 작성자 고유번호와 함께 보내어 content를 랜덤한 사용자에게 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "메세지 전송 완료"),
            @ApiResponse(responseCode = "403", description = "정지된 유저 메세지 전송 권한거부")
    })
    ResponseEntity<WrapResponse<Void>> sendTextMessage(@RequestBody TextSend textSend);

}

package com.ssafy.whoru.domain.message.api;

/*
* Author 김회창
* */

import com.ssafy.whoru.domain.message.dto.request.Info;
import com.ssafy.whoru.domain.message.dto.request.ResponseInfo;
import com.ssafy.whoru.domain.message.dto.request.TextResponseSend;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import com.ssafy.whoru.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "메세지 API", description = "메세지 전송 및 조회에 관한 Controller")
public interface MessageApiDocs {

    @Operation(summary = "Text 메세지 전송", description = "Text 메세지를 작성자 고유번호와 함께 보내어 content를 랜덤한 사용자에게 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Text 메세지 전송 완료", content = @Content(schema = @Schema(implementation = WrapResponse.class))),
            @ApiResponse(responseCode = "403", description = "정지된 유저 메세지 전송 권한거부", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "받는사람의 FCM 토큰이 비었거나 null 상태일때", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<WrapResponse<Void>> sendTextMessage(@RequestBody TextSend textSend);

    @Operation(summary = "Text 답장 메세지 전송", description = "Text 답장메세지를 작성자 고유번호, 내용, 어떤 메세지에 대한 답장인지를 담아서 전송합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Text 답장 메세지 전송완료",  content = @Content(schema = @Schema(implementation = WrapResponse.class))),
        @ApiResponse(responseCode = "403", description = "정지된 유저 메세지 전송 권한거부", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "451", description = "신고된 메세지 답장 전송 권한거부", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422", description = "받는사람의 FCM 토큰이 비었거나 null 상태일때", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<WrapResponse<Void>> sendTextResponseMessage(@RequestBody TextResponseSend responseSend);

    @Operation(summary = "이미지, 보이스 메세지 전송", description = "MultipartForm 을 통해서 이미지 / 보이스 파일과 파일에 대한 타입, 보내는 사람의 고유번호를 담아서 전송")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = {
            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "이미지 / 보이스 메세지 전송 성공",  content = @Content(schema = @Schema(implementation = WrapResponse.class))),
        @ApiResponse(responseCode = "415", description = "허용되지 않는 확장자를 넣은 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "정지된 유저 메세지 전송 권한거부", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "업로드된 파일이 손상된 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422", description = "받는사람의 FCM 토큰이 비었거나 null 상태일때", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "503", description = "AW3 Upload 에 실패했을 때", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<WrapResponse<Void>> sendFileMessage(
        @RequestPart @Schema(description = "업로드 할 파일", format = "binary", allowableValues = {"image/jpeg", "image/gif", "image/png", "audio/mpeg", "audio/wav"}) MultipartFile file,
        @RequestPart @Schema(description = "파일의 정보, 메세지 송신자 고유번호", format = "json", implementation = Info.class) Info info
    );

    @Operation(summary = "이미지, 보이스 답장 메세지 전송", description = "MultipartForm 을 통해서 이미지 / 보이스 파일과 파일에 대한 타입, 보내는 사람의 고유번호, 어떤 메세지의 답장인지를 담아서 전송")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = {
            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "이미지 / 보이스 답장 메세지 전송 성공",  content = @Content(schema = @Schema(implementation = WrapResponse.class))),
        @ApiResponse(responseCode = "415", description = "허용되지 않는 확장자를 넣은 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "정지된 유저 메세지 전송 권한거부", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "451", description = "신고된 메세지 답장 전송 권한거부", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "업로드된 파일이 손상된 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422", description = "받는사람의 FCM 토큰이 비었거나 null 상태일때", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "503", description = "AW3 Upload 에 실패했을 때", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<WrapResponse<Void>> sendFileResponseMessage(
        @RequestPart @Schema(description = "업로드 할 파일", format = "binary", allowableValues = {"image/jpeg", "image/gif", "image/png", "audio/mpeg", "audio/wav"}) MultipartFile file,
        @RequestPart @Schema(description = "파일의 정보, 메세지 송신자 고유번호", format = "json", implementation = ResponseInfo.class) ResponseInfo info
    );



}

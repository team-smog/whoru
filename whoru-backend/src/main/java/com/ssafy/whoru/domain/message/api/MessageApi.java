package com.ssafy.whoru.domain.message.api;

/*
* Author: 김회창
* */


import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.message.application.MessageService;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.domain.message.dto.response.MessageResponse;
import com.ssafy.whoru.domain.message.dto.response.SendResponse;
import com.ssafy.whoru.global.common.dto.response.ResponseWithSuccess;
import com.ssafy.whoru.domain.message.dto.response.SliceMessageResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
@Slf4j
@Validated
public class MessageApi implements MessageApiDocs{

    MessageService service;

    @PostMapping("")
    public ResponseEntity<WrapResponse<SendResponse>> sendTextMessage(@AuthenticationPrincipal CustomOAuth2User member, @RequestBody @Valid TextSend textSend) {
        log.info("request Member -> {}", member.getId());
        log.info("request body -> {}", textSend);
        return ResponseEntity.ok(WrapResponse.create(
                service.sendTextMessage(textSend, member.getId()),
                SuccessType.STATUS_201
        ));
    }

    @PostMapping("/{messageId}/text")
    public ResponseEntity<WrapResponse<Void>> sendTextResponseMessage(
        @AuthenticationPrincipal CustomOAuth2User member,
        @RequestBody @Valid TextSend textSend,
        @PathVariable @Min(value = 1, message = "답장을 보낼 대상 메세지의 고유번호는 0보다 커야 합니다.") Long messageId
    ){
        log.info("request Member -> {}", member.getId());
        log.info("request body -> {}", textSend);
        log.info("request Pathvariable -> {}", messageId);
        service.responseTextMessage(textSend, member.getId(), messageId);
        return ResponseEntity.ok(WrapResponse.create(
                SuccessType.STATUS_201
        ));
    }

    @PostMapping(value = "/file", consumes = {
        MediaType.MULTIPART_FORM_DATA_VALUE,
        MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE,
        MediaType.APPLICATION_JSON_VALUE, "audio/mpeg", "audio/wav", "audio/weba", "audio/webm"
    })
    public ResponseEntity<WrapResponse<SendResponse>> sendFileMessage(@AuthenticationPrincipal CustomOAuth2User member, @RequestPart MultipartFile file){
        log.info("request Member -> {}", member.getId());
        log.info("request file -> fileName: {}, content-type: {}", file.getOriginalFilename(), file.getContentType());
        return ResponseEntity.ok(WrapResponse.create(
            service.sendMediaMessage(file, member.getId()),
            SuccessType.STATUS_201
        ));
    }

    @PostMapping(value = "/{messageId}/file", consumes = {
        MediaType.MULTIPART_FORM_DATA_VALUE,
        MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE,
        MediaType.APPLICATION_JSON_VALUE, "audio/mpeg", "audio/wav", "audio/webm", "audio/weba"
    })

    public ResponseEntity<WrapResponse<Void>> sendFileResponseMessage(
        @AuthenticationPrincipal CustomOAuth2User member,
        @RequestPart MultipartFile file,
        @PathVariable @Valid @Min(value = 1, message = "답장을 보낼 대상 메세지의 고유번호는 0보다 커야 합니다.") Long messageId
    ){
        log.info("request Member -> {}", member.getId());
        log.info("request Pathvariable -> {}", messageId);
        log.info("request file -> fileName: {}, content-type: {}", file.getOriginalFilename(), file.getContentType());
        service.responseFileMessage(file, member.getId(), messageId);
        return ResponseEntity.ok(WrapResponse.create(
            SuccessType.STATUS_201
        ));
    }

    @GetMapping("/recent")
    public ResponseEntity<WrapResponse<List<MessageResponse>>> findRecentMessages(
        @AuthenticationPrincipal CustomOAuth2User member,
        @NotNull(message = "최소 번호를 적어주세요") @Min(value = 1, message = "0보다는 커야 합니다.") @RequestParam(name = "firstid") Long firstId
        )
    {   log.info("request Member -> {}", member.getId());
        log.info("request param -> firstId: {}", firstId);
        ResponseWithSuccess<List<MessageResponse>> response = service.getRecentMessages(firstId, 20, member.getId());
        return ResponseEntity.ok(WrapResponse.create(
            response.getBody(),
            response.getSuccessType()
        ));
    }


    @GetMapping("")
    public ResponseEntity<WrapResponse<SliceMessageResponse>> findOldMessages(
        @AuthenticationPrincipal CustomOAuth2User member,
        @Valid @Min(value = 1, message = "id 최대값은 0보다 커야합니다.")  @RequestParam(required = false, name = "lastid") Long lastId,
        @Valid @Min(value = 20, message = "size는 최소 20 이상이어야 합니다.") @RequestParam Integer size)
    {
        log.info("request Member -> {}", member.getId());
        log.info("request param -> lastid: {}, size: {}", lastId, size);
        ResponseWithSuccess<SliceMessageResponse> response = service.getOldMessages(lastId, size, member.getId());
        return ResponseEntity.ok(WrapResponse.create(
            response.getBody(),
            response.getSuccessType()
        ));
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<WrapResponse<?>> findMessage(@PathVariable("messageId") Long messageId) {
        log.info("request Pathvariable -> {}", messageId);
        MessageResponse response = service.findMessage(messageId);
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.SIMPLE_STATUS));
    }

    @Override
    @GetMapping("/daily/old")
    public ResponseEntity<WrapResponse<SliceMessageResponse>> findDailyOldMessages(
        @Valid @Min(value = 1, message = "id 최대값은 0보다 커야합니다.") @RequestParam(required = false, name = "lastid") Long lastId,
        @Valid @Min(value = 20, message = "size는 최소 20 이상이어야 합니다.") @RequestParam Integer size) {
        log.info("request param -> lastid: {}, size: {}", lastId, size);
        ResponseWithSuccess<SliceMessageResponse> response = service.getDailyOldMessages(lastId, size);
        return ResponseEntity.ok(WrapResponse.create(
            response.getBody(),
            response.getSuccessType()
        ));
    }

    @Override
    @GetMapping("/daily/recent")
    public ResponseEntity<WrapResponse<List<MessageResponse>>> findDailyRecentMessages(
        @NotNull(message = "최소 번호를 적어주세요") @Min(value = 1, message = "0보다는 커야 합니다.") @RequestParam(name = "firstid") Long firstId) {
        log.info("request param -> firstid: {}", firstId);
        ResponseWithSuccess<List<MessageResponse>> response = service.getDailyRecentMessages(firstId, 20);
        return ResponseEntity.ok(WrapResponse.create(
            response.getBody(),
            response.getSuccessType()
        ));
    }

    @Override
    @PatchMapping("/{messageId}")
    public ResponseEntity<WrapResponse<Void>> receiveMessage (
        @PathVariable @Min(value = 1, message = "최소 1이상의 고유값이 필요합니다.")  Long messageId,
        @AuthenticationPrincipal CustomOAuth2User member
    ) throws Exception
    {
        log.info("request Member -> {}", member.getId());
        log.info("request pathvariable -> {}", messageId);

        service.updateReceiver(messageId, member.getId());
        return ResponseEntity.ok(WrapResponse.create(
            SuccessType.STATUS_204
        ));
    }

    @Override
    @GetMapping("/undelivered-message/recent")
    public ResponseEntity<WrapResponse<List<MessageResponse>>> findNotReceivedRecentMessages(
        @NotNull(message = "최소 번호를 적어주세요") @Min(value = 1, message = "0보다는 커야 합니다.") @RequestParam(name = "firstid")  Long firstId,
        @AuthenticationPrincipal CustomOAuth2User member
    ) {
        log.info("request Member -> {}", member.getId());
        log.info("request param -> firstId: {}", firstId);

        ResponseWithSuccess<List<MessageResponse>> response = service.getNotReceivedRecentMessages(firstId, 20, member.getId());
        return ResponseEntity.ok(WrapResponse.create(
            response.getBody(),
            response.getSuccessType()
        ));
    }

    @Override
    @GetMapping("/undelivered-message/old")
    public ResponseEntity<WrapResponse<SliceMessageResponse>> findNotReceivedOldMessages(
        @RequestParam(name = "lastid", required = false) @Valid @Min(value = 1, message = "id 최대값은 0보다 커야합니다.") Long lastId,
        @RequestParam @Valid @Min(value = 20, message = "size는 최소 20 이상이어야 합니다.") Integer size,
        @AuthenticationPrincipal CustomOAuth2User member
    ) {
        log.info("request Member -> {}", member.getId());
        log.info("request param -> lastid: {}, size: {}", lastId, size);

        ResponseWithSuccess<SliceMessageResponse> response = service.getNotReceivedOldMessages(lastId, size, member.getId());
        return ResponseEntity.ok(WrapResponse.create(
            response.getBody(),
            response.getSuccessType()
        ));
    }
}

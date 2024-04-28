package com.ssafy.whoru.domain.message.api;

/*
* Author: 김회창
* */


import com.ssafy.whoru.domain.message.application.MessageService;
import com.ssafy.whoru.domain.message.dto.request.Info;
import com.ssafy.whoru.domain.message.dto.request.TextResponseSend;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
@Slf4j
public class MessageApi implements MessageApiDocs{

    MessageService service;

    @PostMapping("")
    public ResponseEntity<WrapResponse<Void>> sendTextMessage(@RequestBody TextSend textSend) {
        service.sendTextMessageToRandomMember(textSend);
        return ResponseEntity.ok(WrapResponse.create(
                SuccessType.STATUS_201
        ));
    }

    @PostMapping("/response/text")
    public ResponseEntity<WrapResponse<Void>> sendTextResponseMessage(@RequestBody TextResponseSend responseSend){
        service.responseTextMessage(responseSend);
        return ResponseEntity.ok(WrapResponse.create(
                SuccessType.STATUS_201
        ));
    }

    @PostMapping(value = "/file", consumes = {
        MediaType.MULTIPART_FORM_DATA_VALUE,
        MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE,
        MediaType.APPLICATION_JSON_VALUE, "audio/mpeg", "audio/wav"
    })
    public ResponseEntity<WrapResponse<Void>> sendFileMessage(@RequestPart MultipartFile file, @RequestPart Info info){
        service.sendMediaMessageToRandomMember(file, info);
        return ResponseEntity.ok(WrapResponse.create(
           SuccessType.STATUS_201
        ));
    }
}

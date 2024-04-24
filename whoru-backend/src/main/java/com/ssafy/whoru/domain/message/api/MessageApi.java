package com.ssafy.whoru.domain.message.api;

/*
* Author: 김회창
* */


import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.application.MessageService;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.domain.message.dto.response.MessageResponse;
import com.ssafy.whoru.global.common.domain.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageApi implements MessageApiDocs{

    MessageService service;
    @PostMapping("")
    public ResponseEntity<WrapResponse<Void>> sendTextMessage(@RequestBody TextSend textSend) {
        service.sendTextMessageToRandomMember(textSend);
        return ResponseEntity.ok(WrapResponse.create(
                SuccessType.STATUS_201
        ));
    }
}

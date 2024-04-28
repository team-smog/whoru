package com.ssafy.whoru.domain.message.application;

import com.ssafy.whoru.domain.message.dto.request.Info;
import com.ssafy.whoru.domain.message.dto.request.ResponseInfo;
import com.ssafy.whoru.domain.message.dto.request.TextResponseSend;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.domain.message.dto.response.MessageResponse;
import org.springframework.web.multipart.MultipartFile;


public interface MessageService {
    void sendTextMessageToRandomMember(TextSend textSend);

    void responseTextMessage(TextResponseSend responseSend);

    void sendMediaMessageToRandomMember(MultipartFile file, Info info);

    void responseFileMessage(MultipartFile file, ResponseInfo info);
}

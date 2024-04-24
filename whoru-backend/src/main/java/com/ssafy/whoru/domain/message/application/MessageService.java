package com.ssafy.whoru.domain.message.application;

import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.domain.message.dto.response.MessageResponse;


public interface MessageService {
    void sendTextMessageToRandomMember(TextSend textSend);
}

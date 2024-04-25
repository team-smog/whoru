package com.ssafy.whoru.domain.message.dto.request;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TextResponseSend {
    Long senderId;
    String content;
    Long messageId;
}

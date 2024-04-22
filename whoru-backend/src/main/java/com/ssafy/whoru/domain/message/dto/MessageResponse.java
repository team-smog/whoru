package com.ssafy.whoru.domain.message.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageResponse {
    String content;
}

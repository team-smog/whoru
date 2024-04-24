package com.ssafy.whoru.domain.message.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponse {
    Long id;
    String name;
}

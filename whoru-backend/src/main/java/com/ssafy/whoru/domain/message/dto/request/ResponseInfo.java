package com.ssafy.whoru.domain.message.dto.request;

import com.ssafy.whoru.domain.message.dto.ContentType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseInfo {

    ContentType contentType;

    Long senderId;

    Long messageId;

}

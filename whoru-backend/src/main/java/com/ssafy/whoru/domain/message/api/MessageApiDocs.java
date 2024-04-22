package com.ssafy.whoru.domain.message.api;

/*
* Author 김회창
* */

import com.ssafy.whoru.domain.message.dto.MessageResponse;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

public interface MessageApiDocs {
    @Tag(name="Message String API Test", description="String test용 api")
    ResponseEntity<WrapResponse<MessageResponse>> test200();

}

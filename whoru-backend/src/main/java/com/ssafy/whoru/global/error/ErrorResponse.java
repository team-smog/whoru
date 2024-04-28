package com.ssafy.whoru.global.error;

import lombok.Builder;

@Builder
public record ErrorResponse(int errorCode, String errorMessage) {

}

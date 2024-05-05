package com.ssafy.whoru.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    String username;
    String token;
}

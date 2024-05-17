package com.ssafy.whoru.domain.admin.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AdminTokenResponse{
        String name;
        String token;
}

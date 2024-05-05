package com.ssafy.whoru.domain.member.dto.response;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private String username;
    private String iconUrl;
}

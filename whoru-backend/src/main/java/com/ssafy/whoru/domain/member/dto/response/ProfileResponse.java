package com.ssafy.whoru.domain.member.dto.response;

import com.ssafy.whoru.domain.member.dto.LanguageType;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private String username;
    private String iconUrl;
    private String fcmToken;
    private boolean pushAlarm;
    private LanguageType languageType;
}

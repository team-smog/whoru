package com.ssafy.whoru.domain.member.dto.response;

import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.dto.LanguageType;
import com.ssafy.whoru.domain.member.dto.ProviderType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProfileResponse {

    private Long id;

    private String userName;

    private ProviderType provider;

    private String memberIdentifier;

    private int boxCount;

    private String role;

    private LocalDateTime createDate = LocalDateTime.now();

    private Integer reportCount = 0;

    private LanguageType languageType;

    private String iconUrl;

    private String fcmToken;

    private boolean pushAlarm;

}

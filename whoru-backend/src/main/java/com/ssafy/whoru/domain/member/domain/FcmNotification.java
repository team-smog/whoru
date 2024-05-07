package com.ssafy.whoru.domain.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "fcm_notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FcmNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "fcm_token")
    private String fcmToken;

    @Column(name = "is_enabled")
    @Builder.Default
    private Boolean isEnabled = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Member member;

    public void updateNotificationsEnabled(Boolean status) { this.isEnabled = status; }

    public FcmNotification setFcmToken(String token) {
        this.fcmToken = token;
        return this;
    }
}

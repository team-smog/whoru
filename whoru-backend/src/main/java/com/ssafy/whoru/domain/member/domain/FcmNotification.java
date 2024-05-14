package com.ssafy.whoru.domain.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "fcm_notification")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class FcmNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, name = "fcm_token")
    private String fcmToken;

    @Column(name = "is_enabled")
    @Builder.Default
    private Boolean isEnabled = true;

    @Column(name = "mark")
    @Default
    private Boolean mark = false;


    public void updateNotificationsEnabled(Boolean status) { this.isEnabled = status; }

    public void updateMark(Boolean status){
        this.mark = status;
    }

    public void addNotification(Member member){
        this.setMember(member);
        this.getMember().getFcmNotifications().add(this);
    }

    public void removeNotification(){
        this.getMember().getFcmNotifications().remove(this);
    }
}

package com.ssafy.whoru.domain.member.domain;

import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.dto.LanguageType;
import com.ssafy.whoru.domain.member.dto.ProviderType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Enumerated(value = EnumType.STRING)
    private ProviderType provider;

    @Column(updatable = false, name = "member_identifier")
    private String memberIdentifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id")
    private Icon icon;

    @Column(name = "box_count")
    private int boxCount;

    @Column(name = "role")
    private String role;

    @Column(nullable = false, updatable = false, name = "create_date")
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(nullable = false, name = "report_count")
    @Builder.Default
    private Integer reportCount = 0;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "language_type")
    private LanguageType languageType;

    @Column(name = "is_enabled", nullable = false)
    @Default
    Boolean isEnabled = true;

    public void updateNotificationsEnabled(Boolean status) { this.isEnabled = status; }

//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    private FcmNotification fcmNotification;
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Default
    private List<FcmNotification> fcmNotifications = new ArrayList<>();


    public void updateBoxIncrease() {
        this.boxCount++;
    }

    public void updateBoxDecrease() { this.boxCount--; }

    public void increaseReportCount() {this.reportCount++; }

    public void updateReportcountZeroInit() { this.reportCount = 0; }

    public void updateIcon(Icon changeIcon) { this.icon = changeIcon; }


}

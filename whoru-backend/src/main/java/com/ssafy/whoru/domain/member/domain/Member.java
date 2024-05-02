package com.ssafy.whoru.domain.member.domain;

import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.dto.LanguageType;
import com.ssafy.whoru.domain.member.dto.ProviderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private Icon iconId;

    @Column(name = "box_count")
    private int boxCount;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fcm_id", referencedColumnName = "id")
    private FcmNotification fcmNotification;



    public void updateBoxIncrease() {
        this.boxCount++;
    }

    public void updateBoxDecrease() { this.boxCount--; }

    public void updateReportcountZeroInit() { this.reportCount = 0; }

}

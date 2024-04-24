package com.ssafy.whoru.domain.member.domain;

import com.ssafy.whoru.domain.collect.domain.Icon;
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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Enumerated(value = EnumType.STRING)
    private ProviderType provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id")
    private Icon iconId;

    private int boxCount;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(nullable = false)
    private int reportCount = 0;

    private String refreshToken;

    private String fcmToken;

    public void updateBoxCount() {
        this.boxCount++;
    }

    public void updateBoxDecrease() { this.boxCount--; }

    public void updateReportCount() {
        this.reportCount++;
    }


}

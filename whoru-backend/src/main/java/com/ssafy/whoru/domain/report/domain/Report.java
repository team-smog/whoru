package com.ssafy.whoru.domain.report.domain;

import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.report.dto.ReportType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Message message;

    @Column(name = "report_date", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime reportDate = LocalDateTime.now();

    @Column(name = "is_reviewed")
    @Builder.Default
    private Boolean isReviewed = false;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "report_type")
    private ReportType reportType;

    public void updateReviewedStatus() { this.isReviewed = !this.isReviewed; }

}

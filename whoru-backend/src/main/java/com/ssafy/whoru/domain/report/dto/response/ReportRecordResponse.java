package com.ssafy.whoru.domain.report.dto.response;

import com.ssafy.whoru.domain.report.dto.ReportType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportRecordResponse {

    private Long reportId;

    private Long memberId;

    private Long messageId;

    private LocalDateTime reportDate;

    private Boolean isReviewed;

    private ReportType reportType;

}

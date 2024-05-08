package com.ssafy.whoru.domain.report.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostReportRequest {

    private Long messageId;

    private Long senderId;
}

package com.ssafy.whoru.domain.report.application;

import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;

public interface ReportService {

    public void reportMember(PostReportRequest request);
}

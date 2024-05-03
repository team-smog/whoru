package com.ssafy.whoru.domain.report.application;

import com.ssafy.whoru.domain.report.dto.ReportType;
import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;
import com.ssafy.whoru.domain.report.dto.response.ReportRecordResponse;
import com.ssafy.whoru.global.common.dto.SliceResponse;

public interface ReportService {

    public void reportMember(PostReportRequest request);

    public void banMember(Long memberId);

    public SliceResponse<ReportRecordResponse> getReportRecord(int page, int size, ReportType reportType);
}

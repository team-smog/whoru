package com.ssafy.whoru.domain.report.api;

import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.report.application.ReportService;
import com.ssafy.whoru.domain.report.dto.ReportType;
import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;
import com.ssafy.whoru.domain.report.dto.response.ReportRecordResponse;
import com.ssafy.whoru.global.common.dto.SliceResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/report")
public class ReportController implements ReportControllerDocs {

    private final ReportService reportService;

    @PostMapping("/member")
    public ResponseEntity<WrapResponse<Void>> reportMember(@RequestBody PostReportRequest request) {

        reportService.reportMember(request);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.STATUS_201));
    }

    @PostMapping("/ban/{memberId}")
    public ResponseEntity<WrapResponse<Void>> banMember(@PathVariable("memberId") Long memberId, @RequestParam("reportId") Long reportId) {

        reportService.banMember(memberId, reportId);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.STATUS_201));
    }

    @GetMapping("/list")
    public ResponseEntity<WrapResponse<SliceResponse<ReportRecordResponse>>> getReportRecord(@RequestParam("page") int page, @RequestParam(value = "size", required = false) @Min(1) @Max(30) int size, @RequestParam(value = "condition", required = false)
        ReportType reportType) {

        SliceResponse<ReportRecordResponse> result = reportService.getReportRecord(page, size, reportType);
        return ResponseEntity.ok(WrapResponse.create(result, SuccessType.SIMPLE_STATUS));
    }
}

package com.ssafy.whoru.domain.report.api;

import com.ssafy.whoru.domain.report.application.ReportService;
import com.ssafy.whoru.domain.report.dto.ReportType;
import com.ssafy.whoru.domain.report.dto.response.ReportRecordResponse;
import com.ssafy.whoru.global.common.dto.SliceResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/admin/report")
public class ReportAdminApi implements ReportAdminApiDocs {

    private final ReportService reportService;

    @PostMapping("/ban/{memberId}")
    public ResponseEntity<WrapResponse<Void>> banMember(@PathVariable("memberId") Long memberId, @RequestParam("reportId") Long reportId) {

        log.info("request Pathvariable -> {}", memberId);

        log.info("request param -> {}", reportId);

        reportService.banMember(memberId, reportId);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.STATUS_201));
    }

    @GetMapping("/list")
    public ResponseEntity<WrapResponse<SliceResponse<ReportRecordResponse>>> getReportRecord(@RequestParam("page") int page, @RequestParam(value = "size", required = false) @Min(1) @Max(30) int size, @RequestParam(value = "condition", required = false)
    ReportType reportType) {

        log.info("request param -> page: {}, size: {}, ReportType: {}", page, size, reportType);

        SliceResponse<ReportRecordResponse> result = reportService.getReportRecord(page, size, reportType);
        return ResponseEntity.ok(WrapResponse.create(result, SuccessType.SIMPLE_STATUS));
    }

}

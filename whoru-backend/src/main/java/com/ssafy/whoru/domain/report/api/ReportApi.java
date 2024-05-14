package com.ssafy.whoru.domain.report.api;

import com.ssafy.whoru.domain.report.application.ReportService;
import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@RequestMapping("/report")
public class ReportApi implements ReportApiDocs {

    private final ReportService reportService;

    @PostMapping("/member")
    public ResponseEntity<WrapResponse<Void>> reportMember(@RequestBody PostReportRequest request) {

        log.info("request body -> {}", request);

        reportService.reportMember(request);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.STATUS_201));
    }


}

package com.ssafy.whoru.domain.report.api;

import com.ssafy.whoru.domain.report.application.ReportService;
import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;
import com.ssafy.whoru.domain.report.dto.response.ReportRecordResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/report")
public class ReportController implements ReportControllerDocs {

    private final ReportService reportService;

    @PostMapping("/member")
    public ResponseEntity<WrapResponse<Void>> reportMember(@RequestBody PostReportRequest request) {

        reportService.reportMember(request);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.STATUS_201));
    }

    @PostMapping("/{memberId}/ban")
    public ResponseEntity<WrapResponse<Void>> banMember(@PathVariable("memberId") Long memberId) {

        reportService.banMember(memberId);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.STATUS_201));
    }
}

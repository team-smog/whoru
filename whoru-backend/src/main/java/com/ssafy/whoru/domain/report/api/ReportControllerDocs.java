package com.ssafy.whoru.domain.report.api;

import com.ssafy.whoru.domain.report.dto.ReportType;
import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;
import com.ssafy.whoru.domain.report.dto.response.SliceResponse;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Swagger 문서 작업을 위한 추상 인터페이스
 * Controller 코드를 간결화하기 위해 해당 인터페이스에서 어노테이션 작업을 수행
 * **/
@Tag(name ="신고 관련 Controller", description = "신고 API")
public interface ReportControllerDocs {


    @PostMapping("/member")
    @Operation(summary = "사용자 신고", description = "불건전한 메시지 사용자에 대해 신고를 할 수 있다.")
    public ResponseEntity<WrapResponse<Void>> reportMember(@RequestBody PostReportRequest request);

    @PostMapping("/{memberId}/ban")
    @Operation(summary = "사용자 이용 정지", description = "관리자는 사용자 신고 내역을 확인 후 이용 정지를 시킬 수 있다.")
    public ResponseEntity<WrapResponse<Void>> banMember(@PathVariable("memberId") Long memberId);

    @GetMapping("/list")
    @Operation(summary = "사용자 신고 내역 조회", description = "사용자 신고 내역을 Slice 처리하여 응답")
    @Parameters( value = {
        @Parameter(name = "page", required = true, description = "최초 페이지 = 0, 응답으로는 +1 값을 리턴"),
        @Parameter(name = "size", description = "default = 12, 1이상 30이하의 값만 가능"),
        @Parameter(name = "condition", description = "신고 종류에 따라 검색 조건을 설정할 수 있다.")
    })
    @ApiResponse(responseCode = "200", description = "Custom Slice Response", content = @Content(schema = @Schema(implementation = SliceResponse.class)))
    public ResponseEntity<WrapResponse<?>> getReportRecord(@RequestParam("page") int page, @RequestParam(value = "size", required = false) @Min(1) @Max(30) int size, @RequestParam(value = "condition", required = false)
    ReportType reportType);

}

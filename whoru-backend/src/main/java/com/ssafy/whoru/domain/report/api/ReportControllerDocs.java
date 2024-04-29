package com.ssafy.whoru.domain.report.api;

import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Swagger 문서 작업을 위한 추상 인터페이스
 * Controller 코드를 간결화하기 위해 해당 인터페이스에서 어노테이션 작업을 수행
 * **/
@Tag(name ="신고 관련 Controller", description = "신고 API")
public interface ReportControllerDocs {


    @PostMapping("/member")
    @Operation(summary = "사용자 신고", description = "불건전한 메시지 사용자에 대해 신고를 할 수 있다.")
    public ResponseEntity<WrapResponse<Void>> reportMember(@RequestBody PostReportRequest request);

}

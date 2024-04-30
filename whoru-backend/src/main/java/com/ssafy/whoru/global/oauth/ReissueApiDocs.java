package com.ssafy.whoru.global.oauth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;


@Tag(name = "토큰 재발급 API", description = "토큰 재발급에 관한 Controller")
public interface ReissueApiDocs {
    @Operation(summary = "토큰 재발급", description = "Refresh토큰을 받으면 그 토큰이 유효한지 검사하고 새토큰을 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "토큰 발급 정상 완료"),
            @ApiResponse(responseCode = "400", description = "토큰 발급중 오류")
    })
    ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response);
}

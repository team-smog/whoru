package com.ssafy.whoru.domain.board.api;

import com.ssafy.whoru.domain.board.dto.request.PostInquiryBoardRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryRecordResponse;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.global.common.dto.SliceResponse;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name ="게시판 관련 Controller", description = "문의사항, 공지사항 관련 API")
public interface BoardApiDocs {


    @Operation(summary = "문의사항 작성 요청", description = "사용자는 문의사항을 작성할 수 있다.")
    @Parameter(content = @Content(schema = @Schema(implementation = PostInquiryBoardRequest.class)))
    @ApiResponse(responseCode = "201")
    @PostMapping("")
    public ResponseEntity<WrapResponse<Void>> postInquiryBoard(@RequestBody PostInquiryBoardRequest request);

    @Operation(summary = "사용자 문의사항 조회 API", description = "사용자는 자신이 작성한 문의사항들을 조회할 수 있다.")
    @Parameters( value = {
        @Parameter(name = "page", required = true, description = "최초 페이지 = 0, 응답으로는 +1 값을 리턴"),
        @Parameter(name = "size", description = "default = 12 || 1이상 30이하의 값만 가능"),
    })
    @ApiResponse(responseCode = "200", description = "Custom Slice Response", content = @Content(schema = @Schema(implementation = SliceResponse.class)))
    @GetMapping("")
    public ResponseEntity<WrapResponse<SliceResponse<InquiryRecordResponse>>> getInquiryBoard(@AuthenticationPrincipal CustomOAuth2User member,
        @RequestParam("page") int page,
        @RequestParam(value = "size", required = false) @Min(value = 1, message = "size는 최소 1이상이어야 합니다.") @Max(value = 30, message = "size는 최대 30까지만 적용됩니다.") int size);

    @Operation(summary = "문의사항 삭제 요청", description = "사용자는 자신이 작성한 문의사항을 삭제할 수 있다.")
    @Parameter(description = "PathVariable로 게시판의 ID를 전달")
    @ApiResponse(responseCode = "204", description = "성공적으로 삭제 시 204 No Content 반환")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<WrapResponse<Void>> deleteInquiryBoard(@AuthenticationPrincipal CustomOAuth2User member, @PathVariable("boardId") Long boardId);

}

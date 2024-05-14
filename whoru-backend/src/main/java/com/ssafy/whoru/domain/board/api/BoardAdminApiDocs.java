package com.ssafy.whoru.domain.board.api;

import com.ssafy.whoru.domain.board.dto.request.PatchInquiryCommentRequest;
import com.ssafy.whoru.domain.board.dto.request.PatchNotificationRequest;
import com.ssafy.whoru.domain.board.dto.request.PostInquiryCommentRequest;
import com.ssafy.whoru.domain.board.dto.request.PostNotificationRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryDetailResponse;
import com.ssafy.whoru.domain.board.dto.response.InquiryRecordResponse;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.global.common.dto.SliceResponse;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import com.ssafy.whoru.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name ="게시판 관련 ADMIN Controller", description = "문의사항, 공지사항 관련 관리자 API [USER_ROLE ADMIN 권한 필요]")
public interface BoardAdminApiDocs {

    @Operation(summary = "문의사항 답글 작성 요청", description = "관리자는 문의사항 답글을 작성할 수 있다.")
    @Parameter(content = @Content(schema = @Schema(implementation = PostInquiryCommentRequest.class)))
    @ApiResponse(responseCode = "201")
    @PostMapping("/comment")
    public ResponseEntity<WrapResponse<Void>> postComment(@AuthenticationPrincipal CustomOAuth2User admin, @RequestBody PostInquiryCommentRequest request);

    @Operation(summary = "문의사항 조회 요청", description = "관리자는 전체 문의사항을 작성할 수 있다.")
    @Parameters( value = {
        @Parameter(name = "page", required = true, description = "최초 페이지 = 0, 응답으로는 +1 값을 리턴"),
        @Parameter(name = "size", description = "default = 12 || 1이상 30이하의 값만 가능"),
        @Parameter(name = "condition", description = "0으로 보낼 시 전체 조회, 1로 보낼 시 답글이 안달린 문의사항들만 조회")
    })
    @ApiResponse(responseCode = "200", description = "Custom Slice Response", content = @Content(schema = @Schema(implementation = SliceResponse.class)))
    @GetMapping("")
    public ResponseEntity<WrapResponse<SliceResponse<InquiryDetailResponse>>> getTotalInquiry(@RequestParam("page") @Min(value = 0, message = "page는 최소 0이상이어야 합니다.") int page,
        @RequestParam(value = "size", required = false) @Min(value = 1, message = "size는 최소 1이상이어야 합니다.") @Max(value = 30, message = "size는 최대 30까지만 적용됩니다.") int size,
        @RequestParam("condition") @Min(value = 0, message = "조건값은 0 또는 1이어야 합니다.") @Max(value = 1, message = "조건값은 0 또는 1이어야 합니다.") int condition);

    @Operation(summary = "문의사항 답글 수정", description = "어떤 관리자든 문의사항 답글을 수정할 수 있다.")
    @Parameter(content = @Content(schema = @Schema(implementation = PatchInquiryCommentRequest.class)))
    @PatchMapping("/{commentId}")
    public ResponseEntity<WrapResponse<Void>> patchComment(@PathVariable("commentId") Long commentId, @RequestBody PatchInquiryCommentRequest request);

    @Operation(summary = "공지사항 작성하기", description = "관리자의 공지사항 작성 기능")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공적으로 공지사항이 등록되고, 모든 다른 유저들에게 공지사항 알림이 발송됨", content = @Content(schema = @Schema(implementation = WrapResponse.class))),
        @ApiResponse(responseCode = "400", description = "공지사항 제목이 너무 짧거나(2자 미만) 너무 긴 경우(30자 이상), 내용이 너무 짧거나(2자 미만) 너무 긴 경우(200자 이상)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "권한 없을 경우에 반응옴", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/noti")
    public ResponseEntity<WrapResponse<Void>> writeNotification(@AuthenticationPrincipal CustomOAuth2User admin, @RequestBody @Valid PostNotificationRequest postNotificationRequest);

    @Operation(summary = "공지사항 수정하기", description = "관리자의 공지사항 수정 기능, content 만 따로 업데이트 할 수 있으며, subject만 따로 업데이트 할 수 있음")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "204", description = "성공적으로 업데이트 되었음을 의미함, NO_CONTENT(204)는 말그대로 업데이트 명령 수행에 대한 body는 없기 때문", content = @Content(schema = @Schema(implementation = WrapResponse.class))),
        @ApiResponse(responseCode = "400", description = "공지사항 제목이 너무 짧거나(2자 미만) 너무 긴 경우(30자 이상), 내용이 너무 짧거나(2자 미만) 너무 긴 경우(200자 이상), boardId가 0이하인 경우", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "권한 없을 경우에 반응옴", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/noti/{boardId}")
    public ResponseEntity<WrapResponse<Void>> updateNotification(@AuthenticationPrincipal CustomOAuth2User admin, @RequestBody @Valid PatchNotificationRequest patchNotificationRequest, @PathVariable @Min(value = 1, message = "적어도 0보다 커야 합니다.") Long boardId);
}

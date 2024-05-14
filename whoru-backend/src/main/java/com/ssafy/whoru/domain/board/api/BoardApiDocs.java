package com.ssafy.whoru.domain.board.api;

import com.ssafy.whoru.domain.board.dto.request.PostInquiryBoardRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryDetailResponse;
import com.ssafy.whoru.domain.board.dto.response.NotificationResponse;
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
    @PostMapping("/inquiry")
    public ResponseEntity<WrapResponse<Void>> postInquiryBoard(@AuthenticationPrincipal CustomOAuth2User member, @RequestBody PostInquiryBoardRequest request);

    @Operation(summary = "사용자 문의사항 조회 API", description = "사용자는 자신이 작성한 문의사항들을 조회할 수 있다.")
    @Parameters( value = {
        @Parameter(name = "page", required = true, description = "최초 페이지 = 0, 응답으로는 +1 값을 리턴"),
        @Parameter(name = "size", description = "default = 12 || 1이상 30이하의 값만 가능"),
    })
    @ApiResponse(responseCode = "200", description = "Custom Slice Response", content = @Content(schema = @Schema(implementation = SliceResponse.class)))
    @GetMapping("")
    public ResponseEntity<WrapResponse<SliceResponse<InquiryDetailResponse>>> getInquiryBoard(@AuthenticationPrincipal CustomOAuth2User member,
        @RequestParam("page") int page,
        @RequestParam(value = "size", required = false) @Min(value = 1, message = "size는 최소 1이상이어야 합니다.") @Max(value = 30, message = "size는 최대 30까지만 적용됩니다.") int size);

    @Operation(summary = "문의사항 삭제 요청", description = "사용자는 자신이 작성한 문의사항을 삭제할 수 있다.")
    @Parameter(description = "PathVariable로 게시판의 ID를 전달")
    @ApiResponse(responseCode = "204", description = "성공적으로 삭제 시 204 No Content 반환")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<WrapResponse<Void>> deleteInquiryBoard(@AuthenticationPrincipal CustomOAuth2User member, @PathVariable("boardId") Long boardId);

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 무한스크롤로 size만큼 조회")
    @Parameters(value = {
        @Parameter(name = "page", description = "페이지 번호, 이전에 조회한 적 있다면 그 response에 currentPage에 담긴 값을 넣으면 됨, 최초라면 0"),
        @Parameter(name = "size", description = "한 페이지 번호당 조회할 공지사항 글 개수, 1이상 30이하")
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 조회 되었음", useReturnTypeSchema = true),
        @ApiResponse(responseCode = "204", description = "성공적으로 조회 되었으나 보여줄 목록이 없음", content = @Content(schema = @Schema(implementation = SliceResponse.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 쿼리파라미터로 인해 조회할 수 없음", content = @Content(schema =@Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/noti")
    public ResponseEntity<WrapResponse<SliceResponse<NotificationResponse>>>  getNotifications(
        @AuthenticationPrincipal CustomOAuth2User member,
        @RequestParam("page") @Min(value = 0, message = "페이지 번호는 최소 0이상이어야 합니다.") int page,
        @RequestParam(value = "size") @Min(value = 1, message = "사이즈가 너무 작습니다.") @Max(value = 30, message = "사이즈가 너무 큽니다.") int size
    );

}

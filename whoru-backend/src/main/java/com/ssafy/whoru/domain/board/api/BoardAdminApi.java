package com.ssafy.whoru.domain.board.api;


import com.ssafy.whoru.domain.board.application.BoardService;
import com.ssafy.whoru.domain.board.dto.request.PostCommentRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryRecordResponse;
import com.ssafy.whoru.global.common.dto.SliceResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/admin/board")
public class BoardAdminApi {

    private final BoardService boardService;

    @PostMapping("/comment")
    public ResponseEntity<WrapResponse<Void>> postComment(@RequestBody PostCommentRequest request) {

        /**
         * 시큐리티 콘텍스트에서 권한 체크
         * **/
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .filter(role -> role.getAuthority().equals("ROLE_ADMIN"))
                    .findFirst()
                        .orElseThrow(() -> new BusinessLogicException(ErrorCode.FORBIDDEN_ERROR));

        boardService.postComment(request);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.STATUS_201));
    }

    @GetMapping("")
    public ResponseEntity<WrapResponse<SliceResponse<InquiryRecordResponse>>> getTotalInquiry(@RequestParam("page") int page,
        @RequestParam(value = "size", required = false) @Min(value = 1, message = "size는 최소 1이상이어야 합니다.") @Max(value = 30, message = "size는 최대 30까지만 적용됩니다.") int size,
        @RequestParam("condition") @Min(value = 0, message = "조건값은 0 또는 1이어야 합니다.") @Max(value = 1, message = "조건값은 0 또는 1이어야 합니다.") int condition) {

        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .filter(role -> role.getAuthority().equals("ROLE_ADMIN"))
            .findFirst()
            .orElseThrow(() -> new BusinessLogicException(ErrorCode.FORBIDDEN_ERROR));

        SliceResponse<InquiryRecordResponse> response = boardService.getTotalInquiry(page, size, condition);
        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.SIMPLE_STATUS));
    }
}

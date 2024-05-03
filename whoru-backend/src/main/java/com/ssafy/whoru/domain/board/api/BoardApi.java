package com.ssafy.whoru.domain.board.api;

import com.ssafy.whoru.domain.board.application.BoardService;
import com.ssafy.whoru.domain.board.dto.request.PostBoardRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryRecordResponse;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.global.common.dto.SliceResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/board")
public class BoardApi implements BoardApiDocs{

    private final BoardService boardService;

    /**
     * 문의사항 작성 요청 API
     * **/
    @PostMapping("")
    public ResponseEntity<WrapResponse<Void>> postInquiryBoard(@RequestBody PostBoardRequest request) {

        boardService.postInquiryBoard(request);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.STATUS_201));
    }


    /**
     * 사용자가 작성한 문의사항 조회 API
     * **/
    @GetMapping("")
    public ResponseEntity<WrapResponse<SliceResponse<InquiryRecordResponse>>> getInquiryBoard(@AuthenticationPrincipal CustomOAuth2User member,
        @RequestParam("page") int page,
        @RequestParam(value = "size", required = false) @Min(value = 1, message = "size는 최소 1이상이어야 합니다.") @Max(value = 30, message = "size는 최대 30까지만 적용됩니다.") int size) {

        SliceResponse<InquiryRecordResponse> response = boardService.getInquiryBoard(member.getId(), page, size);

        return ResponseEntity.ok(WrapResponse.create(response, SuccessType.SIMPLE_STATUS));
    }

    /**
     * 작성된 문의사항 삭제 API
     */
    @DeleteMapping("/{boardId}")
    public ResponseEntity<WrapResponse<Void>> deleteInquiryBoard(@AuthenticationPrincipal CustomOAuth2User member, @PathVariable("boardId") Long boardId) {

        boardService.deleteInquiryBoard(member.getId(), boardId);
        return ResponseEntity.ok(WrapResponse.create(SuccessType.STATUS_204));
    }

}
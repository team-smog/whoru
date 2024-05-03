package com.ssafy.whoru.domain.board.api;

import com.ssafy.whoru.domain.board.dto.request.PostCommentRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryRecordResponse;
import com.ssafy.whoru.global.common.dto.SliceResponse;
import com.ssafy.whoru.global.common.dto.WrapResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface BoardAdminApiDocs {

    @PostMapping("/comment")
    public ResponseEntity<WrapResponse<Void>> postComment(@RequestBody PostCommentRequest request);

    @GetMapping("")
    public ResponseEntity<WrapResponse<SliceResponse<InquiryRecordResponse>>> getTotalInquiry(@RequestParam("page") int page,
        @RequestParam(value = "size", required = false) @Min(value = 1, message = "size는 최소 1이상이어야 합니다.") @Max(value = 30, message = "size는 최대 30까지만 적용됩니다.") int size,
        @RequestParam("condition") @Min(value = 0, message = "조건값은 0 또는 1이어야 합니다.") @Max(value = 1, message = "조건값은 0 또는 1이어야 합니다.") int condition);


}

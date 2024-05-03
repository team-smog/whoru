package com.ssafy.whoru.domain.board.application;

import com.ssafy.whoru.domain.board.dto.request.PostBoardRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryRecordResponse;
import com.ssafy.whoru.global.common.dto.SliceResponse;

public interface BoardService {

    public void postInquiryBoard(PostBoardRequest request);

    public SliceResponse<InquiryRecordResponse> getInquiryBoard(Long memberId, int page, int size);

}

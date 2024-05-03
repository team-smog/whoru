package com.ssafy.whoru.domain.board.application;

import com.ssafy.whoru.domain.board.dto.request.PostBoardRequest;
import com.ssafy.whoru.domain.board.dto.request.PostCommentRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryRecordResponse;
import com.ssafy.whoru.global.common.dto.SliceResponse;

public interface BoardService {

    public void postInquiryBoard(PostBoardRequest request);

    public SliceResponse<InquiryRecordResponse> getInquiryBoard(Long memberId, int page, int size);

    public SliceResponse<InquiryRecordResponse> getTotalInquiry(int page, int size, int condition);

    public void postComment(PostCommentRequest request);

    public void deleteInquiryBoard(Long memberId, Long boardId);
}

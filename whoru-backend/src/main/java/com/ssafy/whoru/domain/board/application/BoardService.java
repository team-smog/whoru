package com.ssafy.whoru.domain.board.application;

import com.ssafy.whoru.domain.board.dto.request.PatchInquiryCommentRequest;
import com.ssafy.whoru.domain.board.dto.request.PatchNotificationRequest;
import com.ssafy.whoru.domain.board.dto.request.PostInquiryBoardRequest;
import com.ssafy.whoru.domain.board.dto.request.PostInquiryCommentRequest;
import com.ssafy.whoru.domain.board.dto.request.PostNotificationRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryRecordResponse;
import com.ssafy.whoru.global.common.dto.SliceResponse;

public interface BoardService {

    public void postInquiryBoard(PostInquiryBoardRequest request);

    public SliceResponse<InquiryRecordResponse> getInquiryBoard(Long memberId, int page, int size);

    public SliceResponse<InquiryRecordResponse> getTotalInquiry(int page, int size, int condition);

    public void postComment(PostInquiryCommentRequest request);

    public void deleteInquiryBoard(Long memberId, Long boardId);

    public void patchComment(Long commentId, PatchInquiryCommentRequest request);

    public void postNotification(Long adminId, PostNotificationRequest postNotificationRequest);

    public void patchNotification(Long adminId, PatchNotificationRequest patchNotificationRequest, Long boardId);
}

package com.ssafy.whoru.domain.board.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostInquiryCommentRequest {

    private Long boardId;

    private Long commenterId;

    private String content;
}

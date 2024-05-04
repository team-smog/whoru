package com.ssafy.whoru.domain.board.dto.request;

import lombok.Data;

@Data
public class PostInquiryCommentRequest {

    private Long boardId;

    private Long commenterId;

    private String content;
}

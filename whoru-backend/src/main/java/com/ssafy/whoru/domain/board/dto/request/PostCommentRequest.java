package com.ssafy.whoru.domain.board.dto.request;

import lombok.Data;

@Data
public class PostCommentRequest {

    private Long boardId;

    private Long commenterId;

    private String content;
}

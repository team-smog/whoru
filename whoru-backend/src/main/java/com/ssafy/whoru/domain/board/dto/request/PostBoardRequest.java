package com.ssafy.whoru.domain.board.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostBoardRequest {

    private Long memberId;

    private String subject;

    private String content;

}

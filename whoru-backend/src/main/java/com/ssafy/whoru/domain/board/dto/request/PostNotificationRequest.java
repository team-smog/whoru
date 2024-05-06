package com.ssafy.whoru.domain.board.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostNotificationRequest {

    @Size(min = 2, max = 30, message = "공지사항 제목은 2자 이상 30자 이하입니다.")
    private String subject;

    @Size(min = 2, max = 200, message = "공지사항 내용은 2자 이상 200자 이하입니다.")
    private String content;
}

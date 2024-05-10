package com.ssafy.whoru.domain.board.dto.response;

import com.ssafy.whoru.domain.board.dto.BoardType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InquiryDetailResponse {

    private Long id;

    private String subject;

    private String writerName;

    private String content;

    private BoardType boardType;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private CommentDto comment;
}

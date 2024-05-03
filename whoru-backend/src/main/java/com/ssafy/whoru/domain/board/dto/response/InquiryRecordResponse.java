package com.ssafy.whoru.domain.board.dto.response;

import com.ssafy.whoru.domain.board.domain.Comment;
import com.ssafy.whoru.domain.board.dto.BoardType;
import com.ssafy.whoru.domain.member.domain.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryRecordResponse {

    private Long id;

    private String subject;

    private Member writer;

    private String content;

    private BoardType boardType;

    private LocalDateTime createDate;

//    private Comment comment;

    private Boolean isCommented;
}

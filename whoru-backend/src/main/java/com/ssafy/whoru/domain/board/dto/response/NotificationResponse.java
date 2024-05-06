package com.ssafy.whoru.domain.board.dto.response;

import com.ssafy.whoru.domain.board.dto.BoardType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "공지사항 정보 DTO")
public class NotificationResponse {

    @Schema(description = "공지사항 글 고유번호")
    private Long id;

    @Schema(description = "공지사항 글 제목")
    private String subject;

    @Schema(description = "공지사항 글쓴이")
    private String writerName;

    @Schema(description = "공지사항 글 내용")
    private String content;

    @Schema(description = "글 종류")
    private BoardType boardType;

    @Schema(description = "공지사항 글 쓴 최초 날짜")
    private LocalDateTime createDate;

    @Schema(description = "공지사항 글 마지막 업데이트 날짜")
    private LocalDateTime updateDate;

    @Schema(description = "글 종류상 댓글을 달 수 있는 지 여부")
    private Boolean isCommented;
}

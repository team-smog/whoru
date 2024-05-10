package com.ssafy.whoru.domain.board.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    private Long id;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private String content;

    private String commenterName;
}

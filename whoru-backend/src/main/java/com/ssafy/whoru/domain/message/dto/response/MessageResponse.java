package com.ssafy.whoru.domain.message.dto.response;


import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.dto.ContentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "메세지 정보에 관한 DTO")
public class MessageResponse {

    @Schema(description = "메세지 고유번호")
    private Long id;

    @Schema(description = "보내는 사람 고유번호")
    private Long senderId;

    @Schema(description = "받는사람 고유 번호")
    private Long receiverId;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "내용의 종류", pattern = "[ text, image, voice ]")
    private ContentType contentType;

    @Schema(description = "읽음 여부 처리")
    private Boolean readStatus;

    // 답장인지 아닌지
    @Schema(description = "메세지 종류", pattern = "[ true: 답장 메세지, false: 일반 메세지 ]")
    private Boolean isResponse;

    @Schema(description = "상위 메세지", pattern = "[ null아님: 답장 메세지, null: 일반 메세지 ]")
    private Long parentId; //상위 메시지

    @Schema(description = "신고 받음여부 처리", pattern = "[ true: 신고받은 메세지, false: 신고받지 않은 메세지 ]")
    private Boolean isReported;

    @Schema(description = "받은사람 기준 답장을 보낸적 있는지 여부", pattern = "[ true: 이 메세지에 대한 답장 메세지가 존재함, false: 이 메세지는 아직 받은사람이 답장 보내지 않았음, null: 답장 메세지의 경우 해당 필드값 ]")
    private Boolean responseStatus;

}

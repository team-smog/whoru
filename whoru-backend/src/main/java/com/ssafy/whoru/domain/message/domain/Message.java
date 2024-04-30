package com.ssafy.whoru.domain.message.domain;


import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.dto.ContentType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member receiver;

    private String content;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private Boolean readStatus;

    // 답장인지 아닌지
    @Column(name = "is_response", columnDefinition = "TINYINT")
    private Boolean isResponse;

    @ManyToOne
    @JoinColumn(name = "parent_id") //상위 메시지와 연결을 위한 외래키
    private Message parent; //상위 메시지

    @Column(name = "is_reported", columnDefinition = "TINYINT")
    private Boolean isReported;

    // 받는사람 입장에서 해당 메세지에 답장을 보냈었는지
    @Column(name = "response_status", columnDefinition = "TINYINT")
    private Boolean responseStatus;

    public void updateReadStatus(Boolean status) {this.readStatus = status; }

    public void updateIsResponse(Boolean status) {this.isResponse = status; }

    public void updateIsReported(Boolean status) {this.isReported = status; }

    public void updateResponseStatus(Boolean status) {this.responseStatus = status; }
    
}

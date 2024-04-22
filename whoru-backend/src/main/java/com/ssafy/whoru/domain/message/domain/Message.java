package com.ssafy.whoru.domain.message.domain;

<<<<<<< whoru-backend/src/main/java/com/ssafy/whoru/domain/message/domain/Message.java
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.dto.ContentType;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member receiverId;

    private String content;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private Boolean readStatus;

    private Boolean isResponse;

    @ManyToOne
    @JoinColumn(name = "parent_id") //상위 메시지와 연결을 위한 외래키
    private Message parent; //상위 메시지

    private Boolean isReported;

    private Boolean responseStatus;

    public void updateReadStatus(Boolean status) {this.readStatus = status; }

    public void updateIsResponse(Boolean status) {this.isResponse = status; }

    public void updateIsReported(Boolean status) {this.isReported = status; }

    public void updateResponseStatus(Boolean status) {this.responseStatus = status; }
    
}

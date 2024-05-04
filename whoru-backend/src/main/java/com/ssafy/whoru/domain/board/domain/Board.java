package com.ssafy.whoru.domain.board.domain;

import com.ssafy.whoru.domain.board.dto.BoardType;
import com.ssafy.whoru.domain.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    private String content;

    @Enumerated(value = EnumType.STRING)
    private BoardType boardType;

    @OneToOne(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Comment comment;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updateDate = LocalDateTime.now();

    @Transient
    @Builder.Default
    private Boolean isCommented = false;
    public void updateIsCommented(Boolean status) {
        this.isCommented = status;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void setUpdateDate() { this.updateDate = LocalDateTime.now(); }


}

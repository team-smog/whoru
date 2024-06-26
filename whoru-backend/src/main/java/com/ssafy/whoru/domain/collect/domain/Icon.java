package com.ssafy.whoru.domain.collect.domain;

import com.ssafy.whoru.domain.collect.dto.IconGradeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.util.Objects;
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
public class Icon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false, name = "icon_url")
    private String iconUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, updatable = false, name = "icon_grade")
    private IconGradeType iconGrade;

    @Transient
    @Builder.Default
    private Boolean isAvailable = false;

    public void isAvailableUpdate(Boolean status) {
        this.isAvailable = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // id를 사용하여 hashCode를 생성
    }

}

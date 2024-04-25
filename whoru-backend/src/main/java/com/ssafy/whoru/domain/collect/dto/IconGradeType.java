package com.ssafy.whoru.domain.collect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IconGradeType {
    COMMON("일반", 85),
    RARE("고급", 95),
    ADVANCED("희귀", 100),
    ;

    private String grade;
    private int probability;
}

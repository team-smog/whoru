package com.ssafy.whoru.domain.member.dto.response;

import com.ssafy.whoru.domain.collect.dto.IconGradeType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChangeIconResponse {

    private Integer iconId;

    private String iconUrl;

    private IconGradeType iconGrade;
}

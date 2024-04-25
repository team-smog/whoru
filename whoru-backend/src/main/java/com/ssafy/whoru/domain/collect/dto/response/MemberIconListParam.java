package com.ssafy.whoru.domain.collect.dto.response;

import com.ssafy.whoru.domain.collect.dto.IconGradeType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
public class MemberIconListParam {

    private Long iconId;

    private String iconUrl;

    private IconGradeType iconGrade;

    private Boolean isAvailable;

}

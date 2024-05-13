package com.ssafy.whoru.domain.collect.dto.response;

import com.ssafy.whoru.domain.collect.dto.IconGradeType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GetIconResponse {

    private String id;

    private String iconUrl;

    private IconGradeType iconGrade;

    private Boolean isDuplicat;

    private int boxCount;

}

package com.ssafy.whoru.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportType {

    vilification(1, "욕설 메시지"),
    disease(2, "불건전한 메시지")

    ;

    private int type;
    private String Content;

}

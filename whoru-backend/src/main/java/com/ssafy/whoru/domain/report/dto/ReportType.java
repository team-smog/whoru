package com.ssafy.whoru.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportType {

    vilification("욕설 메시지"),
    disease("불건전한 메시지"),
    SPAM("스팸 메시지"),


    ;

    private String Content;

}

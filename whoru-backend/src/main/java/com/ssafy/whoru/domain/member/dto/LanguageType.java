package com.ssafy.whoru.domain.member.dto;

public enum LanguageType {

    KOREAN("ko"),
    ENGLISH("en")
    ;

    private String code;

    LanguageType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

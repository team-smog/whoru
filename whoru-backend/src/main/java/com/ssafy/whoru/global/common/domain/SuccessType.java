package com.ssafy.whoru.global.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@AllArgsConstructor
@Getter

public enum SuccessType {

    SIMPLE_STATUS(200, "성공적인 API"),
    STATUS_201(201,"CREATED 인데용")
    ;

    final Integer status;
    final String msg;
}

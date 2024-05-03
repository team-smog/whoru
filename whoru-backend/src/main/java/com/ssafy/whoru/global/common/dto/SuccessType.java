package com.ssafy.whoru.global.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@AllArgsConstructor
@Getter

public enum SuccessType {

    SIMPLE_STATUS(200, "요청에 대하여 성공적으로 처리되었습니다."),
    STATUS_201(201,"요청에 대하여 처리되어 콘텐츠 생성에 성공하였습니다."),
    STATUS_204(204, "요청이 성공적으로 처리되었습니다.")
    ;

    final Integer status;
    final String msg;
}

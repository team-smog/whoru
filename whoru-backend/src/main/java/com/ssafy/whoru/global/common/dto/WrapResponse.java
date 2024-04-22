package com.ssafy.whoru.global.common.dto;

import com.ssafy.whoru.global.common.domain.SuccessType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class WrapResponse<T>{
    T data;
    Integer status;
    String msg;

    public static <T> WrapResponse<T> create(T body, SuccessType httpType){
        return WrapResponse.<T>builder()
                .data(body)
                .msg(httpType.getMsg())
                .status(httpType.getStatus())
                .build();
    }
}

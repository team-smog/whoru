package com.ssafy.whoru.global.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "공통 응답 객체")
public class WrapResponse<T>{

    @Schema(description = "내용이 담겨있는 data")
    T data;

    @Schema(description = "HttpStatus 코드")
    Integer status;

    @Schema(description = "HttpStatus 코드에 따른 msg")
    String msg;

    public static <T> WrapResponse<T> create(T body, SuccessType httpType){
        return WrapResponse.<T>builder()
                .data(body)
                .msg(httpType.getMsg())
                .status(httpType.getStatus())
                .build();
    }
    public static <T> WrapResponse<T> create(SuccessType httpType){
        return create(null, httpType);
    }
}

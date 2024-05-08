package com.ssafy.whoru.global.common.dto.response;

import com.ssafy.whoru.global.common.dto.SuccessType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWithSuccess<T> {

    SuccessType successType;

    T body;

    public ResponseWithSuccess(T body){
        successType = SuccessType.SIMPLE_STATUS;
        this.body = body;
    }

    public ResponseWithSuccess(T body, SuccessType successType){
        this.body = body;
        this.successType = successType;
    }

}

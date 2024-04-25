package com.ssafy.whoru.domain.collect.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class BoxCountInvalidException extends BusinessLogicException {

    public BoxCountInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}

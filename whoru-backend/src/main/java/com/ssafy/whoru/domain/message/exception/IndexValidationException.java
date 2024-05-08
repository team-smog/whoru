package com.ssafy.whoru.domain.message.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.error.exception.InvalidValueException;

public class IndexValidationException extends InvalidValueException {

    public IndexValidationException() {
        super(ErrorCode.INPUT_VALUE_INVALID);
    }
}

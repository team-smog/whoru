package com.ssafy.whoru.domain.message.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class UnacceptableFileTypeException extends BusinessLogicException {

    public UnacceptableFileTypeException() {
        super(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
    }
}

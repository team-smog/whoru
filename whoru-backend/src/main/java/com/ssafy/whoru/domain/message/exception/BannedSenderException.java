package com.ssafy.whoru.domain.message.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class BannedSenderException extends BusinessLogicException {

    public BannedSenderException() {
        super(ErrorCode.FORBIDDEN_ERROR);
    }
}

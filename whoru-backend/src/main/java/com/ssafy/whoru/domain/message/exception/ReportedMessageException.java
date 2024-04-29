package com.ssafy.whoru.domain.message.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class ReportedMessageException extends BusinessLogicException {
    public ReportedMessageException() {
        super(ErrorCode.REPORTED_MESSAGE_EXCEPTION);
    }
}

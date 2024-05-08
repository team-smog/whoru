package com.ssafy.whoru.domain.report.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class AlreadyBanException extends BusinessLogicException {

    public AlreadyBanException() {
        super(ErrorCode.ALREADY_BAN_MEMBER);
    }
}

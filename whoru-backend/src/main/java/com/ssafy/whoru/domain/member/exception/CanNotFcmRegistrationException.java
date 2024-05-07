package com.ssafy.whoru.domain.member.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class CanNotFcmRegistrationException extends BusinessLogicException {

    public CanNotFcmRegistrationException() {
        super(ErrorCode.FCM_NOT_AVAILABLE);
    }
}

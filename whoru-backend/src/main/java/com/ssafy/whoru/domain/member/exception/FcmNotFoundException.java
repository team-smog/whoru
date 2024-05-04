package com.ssafy.whoru.domain.member.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class FcmNotFoundException extends BusinessLogicException {

    public FcmNotFoundException() {
        super(ErrorCode.FCM_NOT_AVAILABLE);
    }
}

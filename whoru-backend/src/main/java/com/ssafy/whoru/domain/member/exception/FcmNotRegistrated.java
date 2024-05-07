package com.ssafy.whoru.domain.member.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class FcmNotRegistrated extends BusinessLogicException {

    public FcmNotRegistrated() {
        super(ErrorCode.NOT_SAVED_FCM);
    }
}

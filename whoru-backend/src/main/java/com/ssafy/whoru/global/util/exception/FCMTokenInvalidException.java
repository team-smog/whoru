package com.ssafy.whoru.global.util.exception;

import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.error.exception.SimpleException;

public class FCMTokenInvalidException extends SimpleException {


    public FCMTokenInvalidException() {
        super(ErrorCode.FCM_TOKEN_INVALID);
    }
}

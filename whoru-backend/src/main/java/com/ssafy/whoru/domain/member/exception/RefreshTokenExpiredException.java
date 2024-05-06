package com.ssafy.whoru.domain.member.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class RefreshTokenExpiredException extends BusinessLogicException {

    public RefreshTokenExpiredException() {
        super(ErrorCode.REFRESHTOKEN_EXPIRED);
    }
}

package com.ssafy.whoru.domain.member.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class RefreshTokenNotFoundException extends BusinessLogicException {

    public RefreshTokenNotFoundException() {
        super(ErrorCode.TOKEN_NOT_FOUND);
    }
}

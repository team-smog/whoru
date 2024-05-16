package com.ssafy.whoru.global.error.exception;

public class AccessTokenExpiredException extends BusinessLogicException{

    public AccessTokenExpiredException() {
        super(ErrorCode.ACCESSTOKEN_EXPIRED);
    }
}

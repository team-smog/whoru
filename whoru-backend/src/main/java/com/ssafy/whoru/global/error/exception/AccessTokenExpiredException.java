package com.ssafy.whoru.global.error.exception;

public class AccessTokenExpiredException extends RuntimeException{

    ErrorCode errorCode;
    public AccessTokenExpiredException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return errorCode.getStatus();
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}

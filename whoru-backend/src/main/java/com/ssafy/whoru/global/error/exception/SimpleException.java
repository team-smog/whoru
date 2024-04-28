package com.ssafy.whoru.global.error.exception;

public class SimpleException extends RuntimeException{

    ErrorCode errorCode;
    public SimpleException(ErrorCode errorCode) {
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

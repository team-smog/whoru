package com.ssafy.whoru.global.error.exception;

public class BusinessLogicException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessLogicException(ErrorCode errorCode) {
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

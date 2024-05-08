package com.ssafy.whoru.global.error.exception;

public class InvalidValueException extends RuntimeException{

    private final ErrorCode errorCode;

    public InvalidValueException(ErrorCode errorCode) {
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

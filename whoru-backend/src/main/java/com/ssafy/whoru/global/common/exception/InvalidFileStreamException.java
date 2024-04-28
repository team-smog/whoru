package com.ssafy.whoru.global.common.exception;

import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.error.exception.SimpleException;

public class InvalidFileStreamException extends SimpleException {

    public InvalidFileStreamException() {
        super(ErrorCode.FILE_INPUT_STREAM_EXCEPTION);
    }
}

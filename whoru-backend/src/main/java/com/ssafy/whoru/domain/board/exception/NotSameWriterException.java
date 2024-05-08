package com.ssafy.whoru.domain.board.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class NotSameWriterException extends BusinessLogicException {

    public NotSameWriterException() {
        super(ErrorCode.NOT_SAME_WRITER);
    }
}

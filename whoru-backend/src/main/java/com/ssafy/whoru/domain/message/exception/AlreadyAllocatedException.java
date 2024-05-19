package com.ssafy.whoru.domain.message.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class AlreadyAllocatedException extends BusinessLogicException {

    public AlreadyAllocatedException() {
        super(ErrorCode.ALREADY_ALLOCATED);
    }
}

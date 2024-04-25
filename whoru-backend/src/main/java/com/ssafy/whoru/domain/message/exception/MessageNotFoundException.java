package com.ssafy.whoru.domain.message.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class MessageNotFoundException extends BusinessLogicException {

    public MessageNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}

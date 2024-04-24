package com.ssafy.whoru.domain.member.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class MemberNotFoundException extends BusinessLogicException {

    public MemberNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}

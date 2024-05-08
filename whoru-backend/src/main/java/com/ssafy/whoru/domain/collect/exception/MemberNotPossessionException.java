package com.ssafy.whoru.domain.collect.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class MemberNotPossessionException extends BusinessLogicException {

    public MemberNotPossessionException() {
        super(ErrorCode.MEMBER_NOT_POSSESSION);
    }
}

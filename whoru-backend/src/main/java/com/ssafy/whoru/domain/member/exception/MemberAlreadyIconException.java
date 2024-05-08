package com.ssafy.whoru.domain.member.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class MemberAlreadyIconException extends BusinessLogicException {

    public MemberAlreadyIconException() {
        super(ErrorCode.MEMBER_ALREADY_ICON);
    }
}

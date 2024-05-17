package com.ssafy.whoru.domain.admin.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class AdminPasswordNotCorrectException extends BusinessLogicException {

    public AdminPasswordNotCorrectException() {
        super(ErrorCode.ADMIN_NOT_FOUND);
    }
}

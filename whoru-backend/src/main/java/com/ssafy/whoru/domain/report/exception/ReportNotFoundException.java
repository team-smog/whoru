package com.ssafy.whoru.domain.report.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class ReportNotFoundException extends BusinessLogicException {

    public ReportNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}

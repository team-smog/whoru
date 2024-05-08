package com.ssafy.whoru.domain.report.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class DuplicatedReportException extends BusinessLogicException {

    public DuplicatedReportException() {
        super(ErrorCode.DUPLICATE_REPORT_MESSAGE);
    }
}

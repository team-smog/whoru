package com.ssafy.whoru.domain.board.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class BoardNotFoundException extends BusinessLogicException {

    public BoardNotFoundException() {
        super(ErrorCode.CONTENT_NOT_FOUND);
    }
}

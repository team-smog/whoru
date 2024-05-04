package com.ssafy.whoru.domain.board.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class CommentNotFoundException extends BusinessLogicException {

    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}

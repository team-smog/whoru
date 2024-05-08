package com.ssafy.whoru.domain.collect.exception;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;

public class IconNotFoundException extends BusinessLogicException {

    public IconNotFoundException() {
        super(ErrorCode.ICON_NOT_FOUND);
    }
}

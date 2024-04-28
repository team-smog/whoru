package com.ssafy.whoru.global.common.exception;

import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.error.exception.SimpleException;

public class S3UploadException extends SimpleException {


    public S3UploadException() {
        super(ErrorCode.S3_UPLOAD_EXCEPTION);
    }
}

package com.ssafy.whoru.global.error;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.error.exception.InvalidValueException;
import com.ssafy.whoru.global.error.exception.SimpleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 서버 내부에서 잘못된 비즈니스 로직으로 인해 발생하는 예외처리 핸들러 (500번 에러로 처리)
     * **/
    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponse> handleBusinessLogicException(final BusinessLogicException e) {
        final ErrorResponse errorResponse = new ErrorResponse(e.getStatus(), e.getMessage());
        log.error(e.getMessage(), e);

        return ResponseEntity.internalServerError()
            .body(errorResponse);
    }

    /**
     * 잘못된 값에 의한 예외처리 핸들러 (400번 에러로 처리)
     * **/
    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidValueException(final InvalidValueException e) {
        final ErrorResponse errorResponse = new ErrorResponse(e.getStatus(), e.getMessage());
        log.error(e.getMessage(), e);

        return ResponseEntity.badRequest()
            .body(errorResponse);
    }

    /**
     * 잘못된 파일 형식에 대한 예외처리 핸들러
     * **/
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        ErrorCode errorType = ErrorCode.UNSUPPORTED_MEDIA_TYPE;
        log.error(errorType.getMessage(), e);
        final ErrorResponse errorResponse = new ErrorResponse(errorType.getStatus(), errorType.getMessage());

        return ResponseEntity.internalServerError()
            .body(errorResponse);
    }

    /**
     * 파일크기가 제한된 파일크기를 초과했을 때에 대한 예외처리 핸들러
     * **/
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleUploadSizeExceededException(MaxUploadSizeExceededException e){
        ErrorCode errorType = ErrorCode.PAYLOAD_TOO_LARGE;
        log.error(errorType.getMessage(), e);
        final ErrorResponse errorResponse = new ErrorResponse(errorType.getStatus(), errorType.getMessage());

        return ResponseEntity.badRequest()
            .body(errorResponse);
    }

    /**
     * 그 밖의 예외사항에 대한 예외처리 핸들러 (400번 에러로 처리)
     * **/
    @ExceptionHandler(SimpleException.class)
    public ResponseEntity<ErrorResponse> handleSimpleException(final SimpleException e){
        final ErrorResponse errorResponse = new ErrorResponse(e.getStatus(), e.getMessage());
        log.error(e.getMessage(), e);

        return ResponseEntity.internalServerError()
            .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        ErrorCode errorType = ErrorCode.ARGUMENT_TYPE_MISMATCH;
        final ErrorResponse errorResponse = new ErrorResponse(errorType.getStatus(), errorType.getMessage());
        log.error(e.getMessage(), e);

        return ResponseEntity.badRequest()
            .body(errorResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(final IllegalStateException e) {
        ErrorCode errorType = ErrorCode.ARGUMENT_TYPE_MISMATCH;
        final ErrorResponse errorResponse = new ErrorResponse(errorType.getStatus(), errorType.getMessage());
        log.error(e.getMessage(), e);

        return ResponseEntity.badRequest()
            .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> IllegalArgumentException(final IllegalArgumentException e) {
        ErrorCode errorType = ErrorCode.ARGUMENT_TYPE_MISMATCH;
        final ErrorResponse errorResponse = new ErrorResponse(errorType.getStatus(), errorType.getMessage());
        log.error(e.getMessage(), e);

        return ResponseEntity.badRequest()
            .body(errorResponse);
    }

}

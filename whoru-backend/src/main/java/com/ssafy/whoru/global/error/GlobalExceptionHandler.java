package com.ssafy.whoru.global.error;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.InvalidValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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


}

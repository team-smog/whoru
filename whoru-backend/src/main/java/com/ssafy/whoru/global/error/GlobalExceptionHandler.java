package com.ssafy.whoru.global.error;

import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import com.ssafy.whoru.global.error.exception.InvalidValueException;
import com.ssafy.whoru.global.error.exception.SimpleException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    /**
     * MethodArgumentNotValidException handler (400번 에러로 처리)
     * springframework.bind 어노테이션에 의한 검사시에 발생하는 Exception
     * **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e, BindingResult bindingResult){
        ErrorCode errorType = ErrorCode.INPUT_VALUE_INVALID;
        StringBuffer sb = new StringBuffer();
        bindingResult.getFieldErrors().forEach(fieldError -> sb.append(fieldError.getDefaultMessage()).append(", "));

        final ErrorResponse errorResponse = new ErrorResponse(errorType.getStatus(), sb.toString());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * MissingServletRequestParameterException handler (400번 에러로 처리)
     * 쿼리 파라미터가 누락되었을 때 발생하는 Exception
     * **/
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e){
        ErrorCode errorType = ErrorCode.INPUT_VALUE_INVALID;
        StringBuffer sb = new StringBuffer();
        sb.append(e.getParameterName()).append("가 비었습니다.");
        if(sb.isEmpty()){
            sb.append(errorType.getMessage());
        }
        final ErrorResponse errorResponse = new ErrorResponse(errorType.getStatus(), sb.toString());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    /*
    * ConstraintViolationException handler (400번 에러로 처리)
    * jakarta.validation 의 어노테이션에서 발생하는 Exception
    * */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(final ConstraintViolationException e){
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String [] errors = violations.stream()
            .map(ConstraintViolation::getMessage)
            .toArray(String[]::new);
        ErrorCode type = ErrorCode.INPUT_VALUE_INVALID;
        String clientMessage = type.getMessage();
        if(errors.length > 0) {
            clientMessage = String.join(", ", errors);
        }
       final ErrorResponse errorResponse = new ErrorResponse(type.getStatus(), clientMessage);
       log.error(e.getMessage(), e);
       return ResponseEntity.badRequest()
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

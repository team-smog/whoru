package com.ssafy.whoru.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Global
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류입니다."),
    METHOD_NOT_ALLOWED(405, "허용되지 않은 HTTP method입니다."),
    INPUT_VALUE_INVALID(400, "유효하지 않은 입력입니다."),
    INPUT_TYPE_INVALID(400, "입력 타입이 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE(400, "request message body가 없거나, 값 타입이 올바르지 않습니다."),
    HTTP_HEADER_INVALID(400, "request header가 유효하지 않습니다."),
    ENTITY_NOT_FOUND(404, "존재하지 않는 Entity입니다."),
    FORBIDDEN_ERROR(403, "작업을 수행하기 위한 권한이 없습니다."),
    UNSUPPORTED_MEDIA_TYPE(415, "지원하지 않는 파일 형식입니다."),

    // Member
    MEMEBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),

    // Collet
    COLLECT_VALUE_INVALID(400, "박스 개수가 부족합니다."),
    COLLECT_RANDOM_ERROR(500, "아이템 확률 난수 생성 오류입니다."),
    ICON_NOT_FOUND(404, "해당하는 아이콘이 존재하지 않습니다."),

    // FCM
    FCM_TOKEN_INVALID(422, "토큰이 잘못되거나 변조되어 작업을 실행할 수 없습니다."),

    // Message
    REPORTED_MESSAGE_EXCEPTION(403, "신고한 메세지에는 답장할 수 없습니다."),

    // S3 & File
    BUCKET_NOT_FOUND_EXCEPTION(404, "버킷 로드에 실패했습니다."),
    PAYLOAD_TOO_LARGE(413, "요청 본문의 크기가 제한을 초과했습니다."),
    FILE_INPUT_STREAM_EXCEPTION(400, "파일이 변조되었거나 잘못되었습니다."),
    S3_UPLOAD_EXCEPTION(503, "클라우드에 파일 업로드 도중 문제가 발생하였습니다.")
    ;

    private final int status;
    private final String message;

}

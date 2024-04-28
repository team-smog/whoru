package com.ssafy.whoru.global.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum S3PathType {

    MESSAGE_VOICE_PATH("message/voice/"),

    MESSAGE_IMG_PATH("message/img/")

    ;

    final String s3Path;

}

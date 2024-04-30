package com.ssafy.whoru.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import redis.embedded.Redis;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@Slf4j
public enum RedisKeyType {

    BAN("ban"),

    TODAY_BOX("today_cnt"),

    REFRESHTOKEN("refresh")
    ;

    final String prefix;

    static final String SEPARATOR = "::";

    public String makeKey(String ...keyFrags){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getPrefix());
        Arrays.stream(keyFrags)
                .forEach((keyName) -> sb.append(SEPARATOR).append(keyName));

        return sb.toString();
    }
}

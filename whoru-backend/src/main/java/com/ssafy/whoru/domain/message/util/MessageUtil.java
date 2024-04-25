package com.ssafy.whoru.domain.message.util;

import com.ssafy.whoru.global.common.domain.RedisKeyType;
import com.ssafy.whoru.global.util.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class MessageUtil {

    RedisUtil redisUtil;

    public boolean isBanned(Long senderId){
        String key = RedisKeyType.BAN.makeKey(String.valueOf(senderId));
        // 정지여부 체크
        Optional<String> banResult = redisUtil.findValueByKey(key);
        return banResult.isPresent();
    }


}

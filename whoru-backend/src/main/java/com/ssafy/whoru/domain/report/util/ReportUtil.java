package com.ssafy.whoru.domain.report.util;

import com.ssafy.whoru.global.common.dto.RedisKeyType;
import com.ssafy.whoru.global.util.RedisUtil;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ReportUtil {

    RedisUtil redisUtil;

    public boolean isBanned(Long senderId){
        String key = RedisKeyType.BAN.makeKey(String.valueOf(senderId));
        // 정지여부 체크
        Optional<String> banResult = redisUtil.findValueByKey(key);
        return banResult.isPresent();
    }


}

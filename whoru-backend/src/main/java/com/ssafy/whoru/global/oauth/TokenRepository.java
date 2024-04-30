package com.ssafy.whoru.global.oauth;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class TokenRepository {

    private final String setReference = "User";

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOps;

    public void saveRefreshToken(Long userId, String refreshToken) {setOps.add(Long.toString(userId), refreshToken);}

    public boolean isExist(Long userId, String refreshToken) { return setOps.isMember(Long.toString(userId), refreshToken) != null;}

    public void deleteRefreshToken(Long userId, String refreshToken) {setOps.remove(Long.toString(userId),refreshToken);}
}

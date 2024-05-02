package com.ssafy.whoru.global.util;

import java.time.Duration;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class RedisUtil {

    private RedisTemplate<String, String> redisTemplate;

    private static final TimeUnit TIME_UNIT_SET = TimeUnit.SECONDS;

    public Optional<String> findValueByKey(String key){
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    public void insert(String key, String value, Duration between){
        redisTemplate.opsForValue().set(key, value);
    }

    public void insert(String key, String value, long time){
        redisTemplate.opsForValue().set(key, value, time, TIME_UNIT_SET);
    }

    public void flushAll(){
        Set<String> keys = redisTemplate.keys("*");
        if(keys!=null && !keys.isEmpty()){
            redisTemplate.delete(keys);
        }
    }

    public boolean delete(String key){
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }


}

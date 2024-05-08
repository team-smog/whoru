package com.ssafy.whoru.global.util;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.ssafy.whoru.global.util.exception.FCMTokenInvalidException;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FCMUtil {

    static final String FCM_CONTENT = "누군가로부터 메세지가 도착했어요!";

    public void sendMessage(String receiverToken){
        if(tokenCheck(receiverToken)) throw new FCMTokenInvalidException();
        Message message = messageBuilder(receiverToken);
        FirebaseMessaging.getInstance().sendAsync(message);
    }

    @Async("fcm-send")
    public void sendMessage(String receiverToken, String title, String content){
        if(tokenCheck(receiverToken)) return;
        Message message = messageBuilder(receiverToken, title, content);
        log.info("thread name: {}, preparing sendAsync", Thread.currentThread().getName());
        FirebaseMessaging.getInstance().sendAsync(message);
    }

    private boolean tokenCheck(String token){
        return token == null || token.isBlank();
    }

    public String makeDateTitle(String title, LocalDateTime createDate){
        StringBuilder sb = new StringBuilder();
        sb.append("[ ").append(createDate.getMonthValue()).append("/").append(createDate.getDayOfMonth())
            .append(" ] ").append(title);
        return sb.toString();
    }

    private Message messageBuilder(String token){
        return Message.builder()
                .setToken(token)
                .setWebpushConfig(WebpushConfig.builder()
                        .putHeader("ttl", "300")
                        .setNotification(new WebpushNotification("지금 확인하러 고고", FCM_CONTENT))
                        .build()
                ).build();
    }

    private Message messageBuilder(String token, String title, String content){
        return Message.builder()
            .setToken(token)
            .setWebpushConfig(WebpushConfig.builder()
                .putHeader("ttl", "300")
                .setNotification(new WebpushNotification(title, content))
                .build()
            ).build();
    }
}
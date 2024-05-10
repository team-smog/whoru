package com.ssafy.whoru.global.util;

import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.ssafy.whoru.domain.member.application.CrossFcmService;
import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FCMUtil {

    static final String FCM_CONTENT = "누군가로부터 메세지가 도착했어요!";

    private final CrossFcmService crossFcmService;
    @Async("fcm-send")
    public void sendMessage(String token, Long fcmId){
        try{
            log.info("thread name: {}, preparing sendAsync", Thread.currentThread().getName());
            FirebaseMessaging.getInstance().send(messageBuilder(token));
        }catch(FirebaseException e){
            log.error("Firebase exception : {}", e);
            crossFcmService.markingUnusedToken(fcmId);

        }
    }


    @Async("fcm-send")
    public void sendMessage(String receiverToken, Long fcmId, String title, String content){
        try{
            FirebaseMessaging.getInstance().send(messageBuilder(receiverToken, title, content));
        }catch(FirebaseException e){
            log.error("Firebase exception : {}", e);
            crossFcmService.markingUnusedToken(fcmId);
        }
    }

    public String makeDateTitle(String title, LocalDateTime createDate){
        StringBuilder sb = new StringBuilder();
        sb.append("[ ").append(createDate.getMonthValue()).append("/").append(createDate.getDayOfMonth())
            .append(" ] ").append(title);
        return sb.toString();
    }

    private Message messageBuilder(String token){
        HashMap<String, String> body = new HashMap<>();
        body.put("title", "지금 확인하러 고고");
        body.put("content", FCM_CONTENT);
        return Message.builder()
                .setToken(token)
                .putAllData(body)
                .setWebpushConfig(WebpushConfig.builder()
                        .putHeader("ttl", "300")
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
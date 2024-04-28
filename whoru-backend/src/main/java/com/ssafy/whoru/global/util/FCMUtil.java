package com.ssafy.whoru.global.util;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.ssafy.whoru.global.util.exception.FCMTokenInvalidException;
import org.springframework.stereotype.Component;

@Component
public class FCMUtil {

    static final String FCM_CONTENT = "익명으로부터 메세지가 발송되었어요";

    public void sendMessage(String receiverToken){
        if(receiverToken == null || receiverToken.isBlank()) throw new FCMTokenInvalidException();
        Message message = messageBuilder(receiverToken);
        FirebaseMessaging.getInstance().sendAsync(message);
    }

    private Message messageBuilder(String token){
        return Message.builder()
                .setToken(token)
                .setWebpushConfig(WebpushConfig.builder()
                        .putHeader("ttl", "300")
                        .setNotification(new WebpushNotification("메세지가 도착했어요", FCM_CONTENT))
                        .build()
                ).build();
    }
}
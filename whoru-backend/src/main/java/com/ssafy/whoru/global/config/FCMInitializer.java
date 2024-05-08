package com.ssafy.whoru.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.InputStream;


@Component
@Slf4j
public class FCMInitializer {

    InputStream fcmInputStream;

    public FCMInitializer(@Qualifier("fcmInputStream") InputStream fcmInputStream){
        this.fcmInputStream = fcmInputStream;
    }

    @PostConstruct
    public void initialize(){
        try{
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(fcmInputStream))
                    .build();
            if(FirebaseApp.getApps().isEmpty()){
                FirebaseApp.initializeApp(options);
            }
        }catch(Exception e){
            log.warn("FCM initialize IOException 발생 : {}", e.getMessage());
        }
    }
}

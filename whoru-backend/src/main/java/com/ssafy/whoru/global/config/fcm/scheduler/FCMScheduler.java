package com.ssafy.whoru.global.config.fcm.scheduler;

import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FCMScheduler {

    private final FcmRepository fcmRepository;


    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
    public void removeAllMarked(){
        List<FcmNotification> fcmNotifications = fcmRepository.findAllMarked();
        log.info("delete fcmTokens -> {}", fcmNotifications);
        fcmRepository.deleteAll(fcmNotifications);
    }


}

package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService{

    private final FcmRepository fcmRepository;

    @Override
    public void changeIsEnabled(FcmNotification fcm) {
        fcm.updateNotificationsEnabled(!fcm.getIsEnabled());
        fcmRepository.save(fcm);
    }

    @Override
    public void registrationFcm(String token) {
        try {
            FcmNotification fcm = FcmNotification
                    .builder()
                    .fcmToken(token)
                    .isEnabled(true)
                    .build();
            fcmRepository.save(fcm);
        }catch(Exception e){

        }
    }


}

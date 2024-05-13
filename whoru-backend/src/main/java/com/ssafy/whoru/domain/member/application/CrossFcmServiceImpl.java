package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrossFcmServiceImpl implements CrossFcmService{

    private final FcmRepository fcmRepository;
    @Override
    @Transactional
    public void changeEnabled(Member member) {
        List<FcmNotification> fcmNotifications = member.getFcmNotifications();
        fcmNotifications.forEach(fcmNotification -> {
            if(!fcmNotification.getMark()){
                fcmNotification.updateNotificationsEnabled(!fcmNotification.getIsEnabled());
            }
        });
    }

    @Override
    @Transactional
    public void markingUnusedToken(Long id) {
        marking(fcmRepository.findById(id));
    }

    @Override
    @Transactional
    public void markingUnusedToken(Long memberId, String token) {
        log.info("fcm : {}, memberId : {}", token, memberId);
        marking(fcmRepository.findFcmNotificationByMemberAndFcmToken(memberId, token));
    }

    private void marking(Optional<FcmNotification> daoResult){
        if(daoResult.isEmpty()){
            return;
        }
        
        FcmNotification fcmNotification = daoResult.get();
        log.info("fcm entity {} ", fcmNotification);
        fcmNotification.updateMark(true);
    }
}

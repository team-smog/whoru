package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.exception.FcmNotRegistrated;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService{

    private final FcmRepository fcmRepository;
    private final CrossMemberService crossMemberService;

    @Override
    public void changeIsEnabled(FcmNotification fcm) {
        fcm.updateNotificationsEnabled(!fcm.getIsEnabled());
        fcmRepository.save(fcm);
    }

    @Override
    @Transactional
    public void registrationFcm(Long memberId, String token) {
        Member member = crossMemberService.findByIdToEntity(memberId);
        try {
            FcmNotification fcm = FcmNotification
                    .builder()
                    .fcmToken(token)
                    .isEnabled(true)
                    .build();
            FcmNotification save = fcmRepository.save(fcm);
            member.setFcm(save);
        }catch(Exception e){
            throw new FcmNotRegistrated();
        }
    }


}

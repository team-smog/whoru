package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService{

    private final FcmRepository fcmRepository;
    private final CrossMemberService crossMemberService;

    @Override
    @Transactional
    public void updateFcm(Long memberId, String token) {

        Member member = crossMemberService.findByIdToEntity(memberId);
        List<FcmNotification> used = member.getFcmNotifications();
        Optional<FcmNotification> found = Optional.empty();
        for(FcmNotification fcmNotification: used){
            if(fcmNotification.getFcmToken().equals(token)){
                found = Optional.of(fcmNotification);
                break;
            }
        }
        if(found.isPresent()){
            FcmNotification present = found.get();
            if(present.getMark()) {
                present.updateMark(false);
            }
        }else{
            FcmNotification fcmNotification = FcmNotification.builder()
                .fcmToken(token)
                .build();

            fcmNotification.addNotification(member);
            fcmRepository.save(fcmNotification);
        }
    }

    @Override
    @Transactional
    public void changeEnabled(Member member) {
        member.updateNotificationsEnabled(!member.getIsEnabled());
    }

    @Override
    @Transactional
    public void markingUnusedToken(Long id) {
        marking(fcmRepository.findById(id));
    }

    @Override
    @Transactional
    public void markingUnusedToken(Long memberId, String token) {
        marking(fcmRepository.findFcmNotificationByMemberAndFcmToken(memberId, token));
    }

    private void marking(Optional<FcmNotification> daoResult){
        if(daoResult.isEmpty()){
            return;
        }
        FcmNotification fcmNotification = daoResult.get();
        fcmNotification.updateMark(true);
    }
}
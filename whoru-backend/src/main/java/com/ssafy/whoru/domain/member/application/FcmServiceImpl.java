package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService{

    private final FcmRepository fcmRepository;
    private final CrossMemberService crossMemberService;

    @Override
//    @Transactional
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
        if(found.isPresent()) return; // 이미 해당 멤버가 들고있는 토큰이라면 추가 할 필요가 없으므로
        FcmNotification fcmNotification = FcmNotification.builder()
            .isEnabled(true)
            .fcmToken(token)
            .build();

        fcmNotification.addNotification(member);
        fcmRepository.save(fcmNotification);
    }
}
package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.global.error.exception.BusinessLogicException;
import com.ssafy.whoru.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
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
    private final MemberRepository memberRepository;

    @Override
    public void changeIsEnabled(FcmNotification fcm) {
        fcm.updateNotificationsEnabled(!fcm.getIsEnabled());
        fcmRepository.save(fcm);
    }

    @Override
    @Transactional
    public void updateFcm(Long memberId, String token) {
        Optional<Member> member = Optional.ofNullable(memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ErrorCode.MEMBER_NOT_FOUND)));

        FcmNotification fcmNotification = member.get().getFcmNotification();

        member.get().setFcm(fcmNotification.setFcmToken(token));

    }

}
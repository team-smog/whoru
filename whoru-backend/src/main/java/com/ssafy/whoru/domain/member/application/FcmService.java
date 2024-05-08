package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.domain.FcmNotification;

public interface FcmService {

    void changeIsEnabled(FcmNotification fcm);
    void updateFcm(Long memberId, String token);
}

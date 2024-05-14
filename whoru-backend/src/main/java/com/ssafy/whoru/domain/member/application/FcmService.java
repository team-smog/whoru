package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;

public interface FcmService {

    void updateFcm(Long memberId, String token);

    void changeEnabled(Member member);

    void markingUnusedToken(Long id);

    void markingUnusedToken(Long memberId, String token);

}

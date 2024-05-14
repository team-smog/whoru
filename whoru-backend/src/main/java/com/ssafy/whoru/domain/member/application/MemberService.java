package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.ProfileResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;

public interface MemberService {

    ChangeIconResponse changeIcon(Long memberId, int iconId);

    void setPush(Long memberId);

    void logout(Long memberId, String fcmToken);


    TokenResponse reGenerateToken(Long memberId, String refresh);

    ProfileResponse getProfile(Long id);

}

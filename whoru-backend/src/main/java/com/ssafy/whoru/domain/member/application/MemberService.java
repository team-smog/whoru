package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;

public interface MemberService {

    ChangeIconResponse changeIcon(Long memberId, int iconId);

    void logout(Long memberId);

    TokenResponse reGenerateToken(Long memberId);
}

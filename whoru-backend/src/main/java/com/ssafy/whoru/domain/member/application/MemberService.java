package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;

public interface MemberService {

    public ChangeIconResponse changeIcon(CustomOAuth2User member, int iconId);

    void logout(CustomOAuth2User member);

    public TokenResponse reGenerateToken(CustomOAuth2User member);
}

package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.CustomOAuth2User;
import com.ssafy.whoru.domain.member.dto.response.ChangeIconResponse;
import com.ssafy.whoru.domain.member.dto.response.ProfileResponse;
import com.ssafy.whoru.domain.member.dto.response.TokenResponse;

public interface MemberService {

    ChangeIconResponse changeIcon(Long memberId, int iconId);

    void setPush(Long memberId);

    void logout(Long memberId);


    TokenResponse reGenerateToken(Long memberId, String refresh);

    ProfileResponse getProfile(Long id);

}

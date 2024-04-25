package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dto.response.MemberResponse;

public interface MemberService {
    MemberResponse findOne(Long memberId);

    MemberResponse findRandom(Long senderId);

    void updateBoxIncrease(Long memberId);
}

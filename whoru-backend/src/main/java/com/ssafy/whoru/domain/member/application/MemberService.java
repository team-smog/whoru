package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.response.MemberResponse;

public interface MemberService {
    MemberResponse findOne(Long memberId);

    Member findByIdWithEntity(Long memberId);

    MemberResponse findRandom(Long senderId);

    void updateBoxIncrease(Long memberId);

    void updateBoxDecrease(Long memberId);

}

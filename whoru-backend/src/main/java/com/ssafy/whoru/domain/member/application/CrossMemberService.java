package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.domain.Member;

public interface CrossMemberService {

    Member findByIdToEntity(Long memberId);

    Member findRandomToEntity(Long senderId);

}

package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.global.oauth.Member;

public interface CrossMemberService {

    Member findByIdToEntity(Long memberId);

    Member findRandomToEntity(Long senderId);

}

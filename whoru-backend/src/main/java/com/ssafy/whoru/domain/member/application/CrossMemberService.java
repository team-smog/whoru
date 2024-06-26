package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.domain.Member;
import java.util.List;

public interface CrossMemberService {

    Member findByIdToEntity(Long memberId);

    Member findRandomToEntity(Long senderId);

    List<Member> findAllMemberEntities();

}

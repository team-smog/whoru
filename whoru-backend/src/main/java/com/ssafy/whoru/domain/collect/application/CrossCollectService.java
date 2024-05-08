package com.ssafy.whoru.domain.collect.application;

import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.collect.domain.MemberIcon;
import com.ssafy.whoru.domain.member.domain.Member;

public interface CrossCollectService {

    public Icon findByIdToEntity(Integer iconId);

    public MemberIcon findByMemberAndIcon(Member member, Integer iconId);
}

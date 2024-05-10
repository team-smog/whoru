package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.domain.Member;

public interface CrossFcmService {

    public void changeEnabled(Member member);

    public void markingUnusedToken(Long id);

    public void markingUnusedToken(Long memberId, String token);

}

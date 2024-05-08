package com.ssafy.whoru.domain.collect.application;

import com.ssafy.whoru.domain.collect.dto.response.GetIconResponse;
import com.ssafy.whoru.domain.collect.dto.response.MemberIconResponse;

public interface CollectService {

    GetIconResponse redeemRandomIcon(Long memberId);

    MemberIconResponse findMemberIcon(Long memberId);
}

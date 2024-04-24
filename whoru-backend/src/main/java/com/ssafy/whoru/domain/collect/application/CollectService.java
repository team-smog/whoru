package com.ssafy.whoru.domain.collect.application;

import com.ssafy.whoru.domain.collect.dto.response.GetIconResponse;

public interface CollectService {

    GetIconResponse redeemRandomIcon(Long memberId);

}

package com.ssafy.whoru.domain.collect.application;

import com.ssafy.whoru.domain.collect.domain.Icon;

public interface CrossCollectService {

    public Icon findByIdToEntity(Integer iconId);
}

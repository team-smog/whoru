package com.ssafy.whoru.domain.message.application;

import com.ssafy.whoru.domain.message.domain.Message;

public interface CrossMessageService {

    public Message findByIdToEntity(Long messageId);
}

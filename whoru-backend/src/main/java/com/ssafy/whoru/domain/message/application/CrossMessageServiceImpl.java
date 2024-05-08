package com.ssafy.whoru.domain.message.application;

import com.ssafy.whoru.domain.member.exception.MemberNotFoundException;
import com.ssafy.whoru.domain.message.dao.MessageRepository;
import com.ssafy.whoru.domain.message.domain.Message;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrossMessageServiceImpl implements CrossMessageService{

    private final MessageRepository messageRepository;
    @Override
    public Message findByIdToEntity(Long messageId) {

        Optional<Message> message = messageRepository.findById(messageId);
        return message.orElseThrow(MemberNotFoundException::new);
    }
}

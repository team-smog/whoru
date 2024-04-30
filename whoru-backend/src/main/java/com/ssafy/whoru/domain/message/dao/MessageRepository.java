package com.ssafy.whoru.domain.message.dao;

import com.ssafy.whoru.domain.message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {


}

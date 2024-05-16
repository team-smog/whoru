package com.ssafy.whoru.domain.message.dao;

import com.ssafy.whoru.domain.message.domain.Message;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findTopByOrderByIdDesc();

    @Query(nativeQuery = true, value = "select * from (select * from message where receiver_id = :receiverId and id > :firstId and is_reported = 0 limit :size) as `temp` order by id desc")
    List<Message> findRecentMessages(Long receiverId, Long firstId, Integer size);

    @Query(nativeQuery = true, value = "select * from (select * from message where sender_id is not null and receiver_id is not null and id > :firstId and create_date between :start and :end and is_reported = 0 limit :size) as `temp` order by id desc;")
    List<Message> findRecentMessagesWithDaily(Long firstId, Integer size, LocalDateTime start, LocalDateTime end);
}

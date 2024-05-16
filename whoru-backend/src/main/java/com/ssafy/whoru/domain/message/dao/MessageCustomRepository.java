package com.ssafy.whoru.domain.message.dao;

import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import org.springframework.data.domain.Slice;

public interface MessageCustomRepository {

    Slice<Message> findAllBySizeWithNotReported(Long lastId, Integer size, Member receiver);

    Slice<Message> findAllBySizeWithNotReportedAndToday(Long lastId, Integer size);

}

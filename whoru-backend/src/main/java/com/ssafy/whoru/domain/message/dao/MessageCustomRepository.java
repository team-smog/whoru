package com.ssafy.whoru.domain.message.dao;

import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MessageCustomRepository {

    Slice<Message> findAllBySizeWithNotReported(Long lastId, Integer size, Member receiver);

}

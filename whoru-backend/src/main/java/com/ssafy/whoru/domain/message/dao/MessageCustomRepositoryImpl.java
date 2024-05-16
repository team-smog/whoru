package com.ssafy.whoru.domain.message.dao;


import static com.ssafy.whoru.domain.message.domain.QMessage.message;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MessageCustomRepositoryImpl implements MessageCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<Message> findAllBySizeWithNotReported(Long lastId, Integer size, Member receiver) {
        BooleanExpression whereConditions = message.isReported.isFalse()
            .and(message.receiver.id.eq(receiver.getId()));

        if(lastId != null && lastId > 0){
            whereConditions = whereConditions.and(message.id.lt(lastId));
        }
        List<Message> content = jpaQueryFactory.
            selectFrom(message)
            .where(
                whereConditions
            ).orderBy(message.id.desc()).limit(size+1).fetch();

        // 일부러 하나 더 많이 리턴하게 만들고, 실제로 하나 더 많게 된다면 앞으로 더 내보낼 페이지가 있다는 의미
        boolean hasNext = false;
        if(content.size() > size){
            hasNext = true;
        }

        return new SliceImpl<Message>(content, PageRequest.ofSize(size), hasNext);
    }

    @Override
    public Slice<Message> findAllBySizeWithNotReportedAndToday(Long lastId, Integer size) {
        LocalDate today = LocalDate.now();
        log.info("now: {}, nextDay: {}", today.atStartOfDay() , today.atTime(23, 59, 59));
        BooleanExpression whereConditions = message.isReported.isFalse()
            .and(message.receiver.id.isNotNull())
            .and(message.sender.id.isNotNull())
            .and(message.createDate.between(today.atStartOfDay(),  today.atTime(23, 59, 59)));

        if(lastId != null && lastId > 0){
            whereConditions = whereConditions.and(message.id.lt(lastId));
        }
        List<Message> content = jpaQueryFactory.
            selectFrom(message)
            .where(
                whereConditions
            ).orderBy(message.id.desc()).limit(size+1).fetch();

        // 일부러 하나 더 많이 리턴하게 만들고, 실제로 하나 더 많게 된다면 앞으로 더 내보낼 페이지가 있다는 의미
        boolean hasNext = false;
        if(content.size() > size){
            hasNext = true;
        }

        return new SliceImpl<Message>(content, PageRequest.ofSize(size), hasNext);
    }
}

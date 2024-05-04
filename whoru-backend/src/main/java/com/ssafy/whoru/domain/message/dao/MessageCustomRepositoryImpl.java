package com.ssafy.whoru.domain.message.dao;


import static com.ssafy.whoru.domain.message.domain.QMessage.message;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.domain.QMember;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.domain.QMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
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
    public List<Message> findAllByRecent(Long firstId, Integer size, Member receiver) {
        BooleanExpression whereConditions =
            message.isReported.isFalse()
            .and(message.id.between(firstId+1, firstId+size))
            .and(message.receiver.id.eq(receiver.getId()))
            .and(message.readStatus.isFalse());

        return jpaQueryFactory.
            selectFrom(message)
            .where(
                whereConditions
            ).orderBy(message.id.desc())
            .limit(size).fetch();
    }
}

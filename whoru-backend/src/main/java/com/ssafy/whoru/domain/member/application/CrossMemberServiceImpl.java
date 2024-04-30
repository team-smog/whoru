package com.ssafy.whoru.domain.member.application;

import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.exception.MemberNotFoundException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CrossMemberServiceImpl implements CrossMemberService{

    MemberRepository memberRepository;

    @Override
    public Member findByIdToEntity(Long memberId) {
        Optional<Member> result = memberRepository.findById(memberId);
        return result.orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public Member findRandomToEntity(Long senderId) {
        Optional<Member> result = memberRepository.findRandom(senderId);
        return result.orElseThrow(MemberNotFoundException::new);
    }
}

package com.ssafy.whoru.domain.member.application;


import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.response.MemberResponse;
import com.ssafy.whoru.domain.member.exception.MemberNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    MemberRepository memberRepository;
    @Override
    public MemberResponse findOne(Long memberId) {
        Optional<Member> result = memberRepository.findById(memberId);
        Member member = result.orElseThrow(MemberNotFoundException::new);
        return MemberResponse.toDto(member);
    }

    @Override
    public MemberResponse findRandom(Long senderId) {
        Optional<Member> result = memberRepository.findRandom(senderId);
        Member member = result.orElseThrow(MemberNotFoundException::new);
        return MemberResponse.toDto(member);
    }

    @Override
    public void updateBoxIncrease(Long memberId) {
        Optional<Member> result = memberRepository.findById(memberId);
        Member member = result.orElseThrow(MemberNotFoundException::new);
        member.updateBoxIncrease();
    }
}

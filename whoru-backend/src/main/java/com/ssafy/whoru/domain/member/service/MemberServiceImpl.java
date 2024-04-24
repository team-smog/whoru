package com.ssafy.whoru.domain.member.service;


import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.response.MemberResponse;
import com.ssafy.whoru.domain.member.exception.MemberNotFoundException;
import jakarta.annotation.PostConstruct;
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
    public Member findByIdWithEntity(Long memberId) {
        Optional<Member> result = memberRepository.findById(memberId);
        return result.orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public MemberResponse findRandom(Long senderId) {
        Optional<Member> result = memberRepository.findRandom(senderId);
        Member member = result.orElseThrow(MemberNotFoundException::new);
        return MemberResponse.toDto(member);
    }

    @Override
    public void updateBoxCount(Long memberId) {
        Optional<Member> result = memberRepository.findById(memberId);
        Member member = result.orElseThrow(MemberNotFoundException::new);
        member.updateBoxCount();
    }

    @Override
    public void updateBoxDecrease(Long memberId) {
        Optional<Member> result = memberRepository.findById(memberId);
        Member member = result.orElseThrow(MemberNotFoundException::new);
        member.updateBoxDecrease();
    }


    @PostConstruct
    void init() {
        Member member = Member.builder()
            .userName("정현")
            .build();

        memberRepository.save(member);
    }
}

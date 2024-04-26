package com.ssafy.whoru.domain.member.application;


import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.LanguageType;
import com.ssafy.whoru.domain.member.dto.ProviderType;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    

    @PostConstruct
    void init() {
        Member member = Member.builder()
//            .iconId(icon)
            .provider(ProviderType.kakao)
            .memberIdentifier(1L)
            .boxCount(3)
//            .fcmNotification(fcm)
            .createDate(LocalDateTime.now())
            .refreshToken("fdafasfasfasfafdfa")
            .reportCount(0)
            .userName("MEMBER3000")
            .languageType(LanguageType.KOREAN)
            .build();

        memberRepository.save(member);
    }
}

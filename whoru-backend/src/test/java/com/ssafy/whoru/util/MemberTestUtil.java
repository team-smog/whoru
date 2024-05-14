package com.ssafy.whoru.util;

import com.ssafy.whoru.domain.collect.dao.MemberIconRepository;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.collect.domain.MemberIcon;
import com.ssafy.whoru.domain.collect.dto.IconGradeType;
import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.LanguageType;
import com.ssafy.whoru.domain.member.dto.ProviderType;
import com.ssafy.whoru.global.common.dto.RedisKeyType;
import com.ssafy.whoru.global.util.JWTUtil;
import com.ssafy.whoru.global.util.RedisUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.ssafy.whoru.domain.collect.dao.IconRepository;
import com.ssafy.whoru.domain.member.dao.MemberRepository;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class MemberTestUtil implements InitializingBean {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    IconRepository collectRepository;

    @Autowired
    FcmRepository fcmRepository;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    MemberIconRepository memberIconRepository;

    static final String MEMBER3000_FCM_TOKEN = "dqOx-CjHHuSaY8Otgy6qZK:APA91bF-VONyDxVU967mKKz1VoDFm1KQJsDhb_9skx6ZkFg4o4T7cu2OvBGXkhP5a-L44f_zjYxDvSjXx8Kh57V2NitZlKVNyJ9yxvnVMbdtr5KDq6JULFYqFitMxUMPzhgDtGwsNcrr";

    static final String MEMBER3001_FCM_TOKEN = "fb2WjAsrMh5Qi42mwf4nvj:APA91bGWkGHezTWzwYWmLBsxRY575VSWzDO4_ezLJVlQy25FooRJZLpwms77Cv4_gk1Ta0ebnYBEOH0E8Mn9DEjzktjD7vQSev4YKImrsZh6itTHy0xxp_SOzlsx0s3DHcdY4tzK1FsE";

    static final String MEMBER_ERROR_FCM_TOKEN = "";

    static final String MEMBER_HEADER_PREFIX = "Bearer ";

    public static final String MEMBER_HEADER_AUTH = "Authorization";

    RedisUtil redisUtil;

    @Autowired
    public MemberTestUtil(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }

    private Duration banDuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        // bean 초기화 직후 실행하는 메소드MEMBER_HEADER_AUTH
        LocalDateTime next = LocalDate.now().plusDays(1).atStartOfDay();
        banDuration = Duration.between(LocalDateTime.now(), next);
    }

    public List<Member> 기본_멤버_세팅() {
        List<Member> memberInfoList = new ArrayList<>();
        Icon icon = 아이콘_추가();
        collectRepository.save(icon);
        Member member1 = Member3000_멤버추가(icon);
        Member member2 = Member3001_멤버추가(icon);

        memberInfoList.add(member1);
        memberInfoList.add(member2);
        return memberInfoList;
    }

    public Icon 아이콘_추가(){
        Icon icon = Icon.builder()
            .iconUrl("test_icon")
            .iconGrade(IconGradeType.COMMON)
            .build();
        return icon;
    }

    public Member Member3000_멤버추가(Icon icon){

        /*
        * .fcmNotification(FcmNotification.builder()
                        .fcmToken(MEMBER3000_FCM_TOKEN)
                        .build())
        * */
        Member member = Member.builder()
                .icon(icon)
                .provider(ProviderType.kakao)
                .memberIdentifier("1")
                .boxCount(3)
                .createDate(LocalDateTime.now())
                .refreshToken("fdafasfasfasfafdfa")
                .reportCount(0)
                .role("ROLE_USER")
                .userName("MEMBER3000")
                .languageType(LanguageType.KOREAN)
                .build();
        memberRepository.save(member);

        FcmNotification fcmNotification = FcmNotification.builder()
            .isEnabled(true)
            .fcmToken(MEMBER3000_FCM_TOKEN)
            .mark(false)
            .member(member)
            .build();
        fcmNotification.addNotification(member);
        fcmRepository.save(fcmNotification);
        return member;
    }

    public Member Member3001_멤버추가(Icon icon){

        Member member = Member.builder()
            .icon(icon)
            .provider(ProviderType.kakao)
            .memberIdentifier("1")
            .boxCount(3)
            .role("ROLE_USER")
            .createDate(LocalDateTime.now())
            .refreshToken("fdafasfasfasfafdfa")
            .reportCount(0)
            .userName("MEMBER3001")
            .languageType(LanguageType.KOREAN)
            .build();
        memberRepository.save(member);

        FcmNotification fcmNotification = FcmNotification.builder()
            .isEnabled(true)
            .fcmToken(MEMBER3001_FCM_TOKEN)
            .member(member)
            .mark(false)
            .build();
        fcmNotification.addNotification(member);
        fcmRepository.save(fcmNotification);
        return member;
    }

    public void 멤버_보유_아이콘_추가(Member member, Icon icon) {
        MemberIcon memberIcon = MemberIcon
            .builder()
            .member(member)
            .icon(icon)
            .build();
        memberIconRepository.save(memberIcon);


    }

    public Member Member_Error_Fcm_token멤버추가(Icon icon){
        Member member = Member.builder()
            .icon(icon)
            .provider(ProviderType.kakao)
            .memberIdentifier("1")
            .boxCount(3)
            .createDate(LocalDateTime.now())
            .refreshToken("fdafasfasfasfafdfa")
            .reportCount(0)
            .role("ROLE_USER")
            .userName("ERROR_MEMBER")
            .languageType(LanguageType.KOREAN)
            .build();
        memberRepository.save(member);

        FcmNotification fcmNotification = FcmNotification.builder()
            .isEnabled(true)
            .fcmToken(MEMBER_ERROR_FCM_TOKEN)
            .mark(false)
            .member(member)
            .build();
        fcmRepository.save(fcmNotification);
        return member;

    }

    public Member 관리자_멤버_추가(Icon icon){

        Member member = Member.builder()
            .icon(icon)
            .provider(ProviderType.kakao)
            .memberIdentifier("1")
            .boxCount(3)
            .createDate(LocalDateTime.now())
            .refreshToken("fdafasfasfasfafdfa")
            .reportCount(0)
            .userName("ADMIN_MEMBER")
            .role("ROLE_ADMIN")
            .languageType(LanguageType.KOREAN)
            .build();
        memberRepository.save(member);

        FcmNotification fcmNotification = FcmNotification.builder()
            .isEnabled(true)
            .fcmToken(MEMBER3000_FCM_TOKEN)
            .mark(false)
            .member(member)
            .build();
        fcmRepository.save(fcmNotification);
        return member;
    }

    public String 유저_AccessToken_만들고_헤더값_리턴(Member member){
        StringBuilder sb = new StringBuilder();
        sb.append(MEMBER_HEADER_PREFIX).append(jwtUtil.createAccessToken(member.getId(), "access", "ROLE_USER"));
        return sb.toString();
    }

    public String 관리자_AccessToken_만들고_헤더값_리턴(Member member) {
        StringBuilder sb = new StringBuilder();
        sb.append(MEMBER_HEADER_PREFIX).append(jwtUtil.createAccessToken(member.getId(), "access", "ROLE_ADMIN"));
        return sb.toString();
    }

    public void 유저_정지_먹이기(Member member){
        redisUtil.insert(RedisKeyType.BAN.makeKey(String.valueOf(member.getId())), "test_ban",banDuration.getSeconds());
    }



}

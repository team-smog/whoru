package com.ssafy.whoru.util;

import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.collect.dto.IconGradeType;
import com.ssafy.whoru.domain.member.dao.FcmRepository;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.LanguageType;
import com.ssafy.whoru.domain.member.dto.ProviderType;
import com.ssafy.whoru.global.common.dto.RedisKeyType;
import com.ssafy.whoru.global.util.RedisUtil;
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

    static final String MEMBER3000_FCM_TOKEN = "cfMx6tEB1EUp2Eb484bePq:APA91bG_iJU6Olx_aSkQSB7Q6j8wyCyRtx5Gb9jfDRLigIaYdRKJbY14kD34nIZjwWIepkxmm02WlOHaLvbbDck8FamulUEttEcGEKzseph_p3X1tkjpXWqvwCh8I-jiKkzE9RYJJ2uI";

    static final String MEMBER3001_FCM_TOKEN = "e8cnURUUWvX7vbwch21Egk:APA91bGgQKlqD7xsbbUstiypDx7KuAwOBi1TvxaR0Cvgqhjf8o3gVuRpBAqeiRFbo0Vj70CCx1CVyDb5b6nmwHzZOuIMyAFIBpzhqpsmwY-oR6GiAXqNJEV40xng934TA6Pse2ykBhV7";

    static final String MEMBER_ERROR_FCM_TOKEN = "";

    RedisUtil redisUtil;

    @Autowired
    public MemberTestUtil(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }

    private Duration banDuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        // bean 초기화 직후 실행하는 메소드
        LocalDateTime next = LocalDate.now().plusDays(1).atStartOfDay();
        banDuration = Duration.between(LocalDateTime.now(), next);
    }


    public Icon 아이콘_추가(MockMvc mockMvc){
        Icon icon = Icon.builder()
            .iconUrl("test_icon")
            .iconGrade(IconGradeType.COMMON)
            .build();
        return icon;
    }

    public Member Member3000_멤버추가(Icon icon, MockMvc mockMvc){
        FcmNotification fcm = FcmNotification.builder()
            .fcmToken(MEMBER3000_FCM_TOKEN)
            .deviceName("IPhone")
            .build();

        fcmRepository.save(fcm);

        return Member.builder()
                .iconId(icon)
                .provider(ProviderType.kakao)
                .memberIdentifier(1L)
                .boxCount(3)
                .fcmNotification(fcm)
                .createDate(LocalDateTime.now())
                .refreshToken("fdafasfasfasfafdfa")
                .reportCount(0)
                .userName("MEMBER3000")
                .languageType(LanguageType.KOREAN)
                .build();
    }

    public Member Member3001_멤버추가(Icon icon, MockMvc mockMvc){

        FcmNotification fcm = FcmNotification.builder()
            .fcmToken(MEMBER3001_FCM_TOKEN)
            .deviceName("Galaxy S20")
            .build();

        fcmRepository.save(fcm);

        return Member.builder()
                .iconId(icon)
                .provider(ProviderType.kakao)
                .memberIdentifier(2L)
                .boxCount(3)
                .fcmNotification(fcm)
                .createDate(LocalDateTime.now())
                .refreshToken("fdafasfasfasfafdfa")
                .reportCount(0)
                .userName("MEMBER3001")
                .languageType(LanguageType.KOREAN)
                .build();
    }

    public Member Member_Error_Fcm_token멤버추가(Icon icon, MockMvc mockMvc){

        FcmNotification fcm = FcmNotification.builder()
            .fcmToken(MEMBER_ERROR_FCM_TOKEN)
            .deviceName("Galaxy S20")
            .build();

        fcmRepository.save(fcm);

        return Member.builder()
                .iconId(icon)
                .provider(ProviderType.kakao)
                .memberIdentifier(3L)
                .boxCount(3)
                .fcmNotification(fcm)
                .createDate(LocalDateTime.now())
                .refreshToken("fdafadfas")
                .reportCount(0)
                .userName("MEMBER_ERROR_FCM_TOKEN")
                .languageType(LanguageType.KOREAN)
                .build();

    }

    public void 유저_정지_먹이기(Member member){
        redisUtil.insert(RedisKeyType.BAN.makeKey(String.valueOf(member.getId())), "test_ban",banDuration.getSeconds());
    }



}
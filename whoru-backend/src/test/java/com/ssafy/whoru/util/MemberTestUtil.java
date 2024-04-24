package com.ssafy.whoru.util;

import com.ssafy.whoru.domain.collect.dao.IconRepository;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.ProviderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@Component
public class MemberTestUtil {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    IconRepository collectRepository;

    static final String MEMBER3000_FCM_TOKEN = "cfMx6tEB1EUp2Eb484bePq:APA91bG_iJU6Olx_aSkQSB7Q6j8wyCyRtx5Gb9jfDRLigIaYdRKJbY14kD34nIZjwWIepkxmm02WlOHaLvbbDck8FamulUEttEcGEKzseph_p3X1tkjpXWqvwCh8I-jiKkzE9RYJJ2uI";

    static final String MEMBER3001_FCM_TOKEN = "e8cnURUUWvX7vbwch21Egk:APA91bGgQKlqD7xsbbUstiypDx7KuAwOBi1TvxaR0Cvgqhjf8o3gVuRpBAqeiRFbo0Vj70CCx1CVyDb5b6nmwHzZOuIMyAFIBpzhqpsmwY-oR6GiAXqNJEV40xng934TA6Pse2ykBhV7";
    static final String MEMBER_ERROR_FCM_TOKEN = "DFAFADSF";

    public Icon 아이콘_추가(MockMvc mockMvc){
        Icon icon = Icon.builder()
                .iconUrl("test_icon")
                .build();
        return icon;
    }

    public Member Member3000_멤버추가(Icon icon, MockMvc mockMvc){
        return Member.builder()
                .iconId(icon)
                .provider(ProviderType.kakao)
                .boxCount(3)
                .fcmToken(MEMBER3000_FCM_TOKEN)
                .createDate(LocalDateTime.now())
                .refreshToken("fdafasfasfasfafdfa")
                .reportCount(0)
                .userName("MEMBER3000")
                .build();
    }

    public Member Member3001_멤버추가(Icon icon, MockMvc mockMvc){
        return Member.builder()
                .iconId(icon)
                .provider(ProviderType.kakao)
                .boxCount(3)
                .fcmToken(MEMBER3001_FCM_TOKEN)
                .createDate(LocalDateTime.now())
                .refreshToken("fdafasfasfasfafdfa")
                .reportCount(0)
                .userName("MEMBER3001")
                .build();
    }

    public Member Member_Error_Fcm_token멤버추가(Icon icon, MockMvc mockMvc){
        return Member.builder()
                .iconId(icon)
                .provider(ProviderType.kakao)
                .boxCount(3)
                .fcmToken(MEMBER_ERROR_FCM_TOKEN)
                .createDate(LocalDateTime.now())
                .refreshToken("fdafasfasfasfafdfa")
                .reportCount(0)
                .userName("MEMBER_ERROR_FCM_TOKEN")
                .build();

    }
}

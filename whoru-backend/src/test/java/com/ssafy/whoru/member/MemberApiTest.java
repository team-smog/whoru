package com.ssafy.whoru.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.whoru.TestPrepare;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.util.MemberTestUtil;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
public class MemberApiTest extends TestPrepare {

    @Test
    @Transactional
    void 사용자_아이콘_변경_성공_200() throws Exception {

        //사용자를 하나 넣어놓고,
        //아이콘을 하나 넣어놓고,
        //사용자 아이콘을 새로 추가하고,
        //테스트 진행
        Icon icon = memberTestUtil.아이콘_추가();
        Icon icon2 = memberTestUtil.아이콘_추가();

        collectRepository.save(icon);
        collectRepository.save(icon2);

        Member member3000 = memberTestUtil.Member3000_멤버추가(icon);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        memberTestUtil.멤버_보유_아이콘_추가(member3000, icon);
        memberTestUtil.멤버_보유_아이콘_추가(member3000, icon2);

        member3000.updateIcon(icon2);

        StringBuilder sb = new StringBuilder();
        sb.append("/member/icon?iconId=").append(icon.getId());

        /**
         * 사용자 아이콘 변경 요청 API 호출
         * **/

        mockMvc.perform(
                patch(sb.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));

    }

}

package com.ssafy.whoru.report;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.whoru.TestPrepare;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;
import com.ssafy.whoru.util.MemberTestUtil;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
public class ReportApiTest extends TestPrepare {

    @Test
    @Transactional
    void 사용자_신고_성공_201() throws Exception {
        //테스트 코드 작성
        //메시지 생성
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member3001 = memberTestUtil.Member3001_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberRepository.save(member3001);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        Message message = messageTestUtil.Text_메세지(mockMvc, member3000, member3001, false);
        messageRepository.save(message);

        /**
         * 사용자 신고 API 호출
         * **/
        PostReportRequest postReportRequest = PostReportRequest.builder()
            .senderId(member3000.getId())
            .messageId(message.getId())
            .build();

        mockMvc.perform(
            post("/report/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postReportRequest))
                .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
        )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));;

    }

    @Test
    @Transactional
    void 사용자_신고_실패_409() throws Exception {
        //테스트 코드 작성
        //메시지 생성
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member3001 = memberTestUtil.Member3001_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberRepository.save(member3001);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        //isReported true SETTING
        Message message = messageTestUtil.Text_메세지(mockMvc, member3000, member3001, true);
        messageRepository.save(message);

        /**
         * 사용자 신고 API 호출
         * **/
        PostReportRequest postReportRequest = PostReportRequest.builder()
            .senderId(member3000.getId())
            .messageId(message.getId())
            .build();


        mockMvc.perform(
                post("/report/member")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(postReportRequest))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.CONFLICT.value()));;

    }

    @Test
    @Transactional
    void 사용자_정지_요청_성공_201() throws Exception{

        //사용자 생성
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        /**
         * 사용자 이용정지 요청 API 호출
         * **/
        StringBuffer sb = new StringBuffer();
        sb.append("/report/").append(member3000.getId()).append("/ban");
        mockMvc.perform(
                post(sb.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));;

    }

    @Test
    @Transactional
    void 이미_정지된_사용자_정지_요청_실패_409() throws Exception{

        //사용자 생성
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member = memberRepository.save(member3000);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        memberTestUtil.유저_정지_먹이기(member);
        /**
         * 사용자 이용정지 요청 API 호출
         * **/
        StringBuffer sb = new StringBuffer();
        sb.append("/report/").append(member.getId()).append("/ban");
        mockMvc.perform(
                post(sb.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.CONFLICT.value()));;

    }

}

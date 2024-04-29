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
    void 사용자_신고_성공() throws Exception {
        //테스트 코드 작성
        //메시지 생성
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member3001 = memberTestUtil.Member3001_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberRepository.save(member3001);

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
        )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));;

    }

    @Test
    @Transactional
    void 사용자_신고_실패() throws Exception {
        //테스트 코드 작성
        //메시지 생성
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member3001 = memberTestUtil.Member3001_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberRepository.save(member3001);

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
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.CONFLICT.value()));;

    }

}

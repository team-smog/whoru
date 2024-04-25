package com.ssafy.whoru.message;

import com.ssafy.whoru.TestPrepare;
import com.ssafy.whoru.domain.collect.dao.IconRepository;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.dao.MessageRepository;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.dto.request.TextResponseSend;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.util.MemberTestUtil;
import com.ssafy.whoru.util.MessageTestUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
public class MessageApiTest extends TestPrepare {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    IconRepository collectRepository;

    @Autowired
    MemberTestUtil memberTestUtil;

    @Autowired
    MessageTestUtil messageTestUtil;



    @Test
    void Text_메세지_전송_성공_201() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member3001 = memberTestUtil.Member3001_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberRepository.save(member3001);

        TextSend textSend = TextSend.builder()
                .senderId(member3001.getId())
                .content("반갑습니다.")
                .build();

        mockMvc.perform(
            post("/message")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(textSend))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));


    }

    @Test
    void 정지된_유저_매세지_전송_실패_403() throws Exception {
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberTestUtil.유저_정지_먹이기(member3000);
        TextSend textSend = TextSend.builder()
                .senderId(member3000.getId())
                .content("정지된 유저이지롱")
                .build();

        mockMvc.perform(
                post("/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(textSend))
        )
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.FORBIDDEN.value()));

    }

    @Test
    void Text_답장_메시지_전송_성공_201() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member3001 = memberTestUtil.Member3001_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberRepository.save(member3001);
        Message message = messageTestUtil.Text_메세지(mockMvc, member3000, member3001, false);
        messageRepository.save(message);

        TextResponseSend textResponseSend = TextResponseSend.builder()
                .content("답장 test 메세지")
                .messageId(message.getId())
                .senderId(member3001.getId())
                .build();

        mockMvc.perform(
                post("/message/response/text")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(textResponseSend))
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));

    }

    @Test
    void 정지된_유저_답장_전송_실패_403() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member3001 = memberTestUtil.Member3001_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberRepository.save(member3001);
        Message message = messageTestUtil.Text_메세지(mockMvc, member3000, member3001, false);
        messageRepository.save(message);
        memberTestUtil.유저_정지_먹이기(member3001);
        TextResponseSend textResponseSend = TextResponseSend.builder()
                .content("답장 test 메세지")
                .messageId(message.getId())
                .senderId(member3001.getId())
                .build();

        mockMvc.perform(
                        post("/message/response/text")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(textResponseSend))
                )
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    void 신고된_Text_메세지에_답장_전송_실패_400() throws Exception {
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member3001 = memberTestUtil.Member3001_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberRepository.save(member3001);
        Message message = messageTestUtil.Text_메세지(mockMvc, member3000, member3001, true);
        messageRepository.save(message);

        TextResponseSend textResponseSend = TextResponseSend.builder()
                .messageId(message.getId())
                .senderId(message.getReceiver().getId())
                .content("정지된 메세지에 답장 Test")
                .build();

        mockMvc.perform(
          post("/message/response/text")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(textResponseSend))
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }
}

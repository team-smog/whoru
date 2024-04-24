package com.ssafy.whoru.message;

import com.ssafy.whoru.TestPrepare;
import com.ssafy.whoru.domain.collect.dao.CollectRepository;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.dao.MessageRepository;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.global.common.domain.RedisKeyType;
import com.ssafy.whoru.util.MemberTestUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    CollectRepository collectRepository;

    @Autowired
    MemberTestUtil memberTestUtil;



    @Test
    void Text_메세지_전송_성공_200() throws Exception{
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
        LocalDateTime next = LocalDate.now().plusDays(1).atStartOfDay();
        long duration = Duration.between(LocalDateTime.now(), next).getSeconds();
        redisUtil.insert(RedisKeyType.BAN.makeKey(String.valueOf(member3000.getId())), "test_ban", duration);
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
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.FORBIDDEN.value()));

    }
}

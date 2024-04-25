package com.ssafy.whoru.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.dto.ContentType;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@Component
public class MessageTestUtil {
    public Message Text_메세지(MockMvc mockMvc, Member sender, Member receiver, Boolean isReported){
        return Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content("test")
                .contentType(ContentType.text)
                .isResponse(false)
                .responseStatus(false)
                .isReported(isReported)
                .parent(null)
                .readStatus(false)
                .build();
    }

}

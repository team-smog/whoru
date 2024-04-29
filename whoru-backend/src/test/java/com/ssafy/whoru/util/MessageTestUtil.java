package com.ssafy.whoru.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.dto.ContentType;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;


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

    public Message 미디어_메세지(MockMvc mockMvc, Member sender, Member receiver, Boolean isReported){
        return Message.builder()
            .sender(sender)
            .receiver(receiver)
            .content("test")
            .contentType(ContentType.image)
            .isResponse(false)
            .responseStatus(false)
            .isReported(isReported)
            .parent(null)
            .readStatus(false)
            .build();
    }

    public MockMultipartFile 이미지_생성(){

        byte [] imageContent = "dummy".getBytes(StandardCharsets.UTF_8);
        MockMultipartFile imageFile = new MockMultipartFile(
            "file",
            "image.png",
            "image/png",
            imageContent
        );
        return imageFile;
    }

    public MockMultipartFile JSON_생성(ObjectMapper objectMapper, Long senderId) throws IOException{
        HashMap<String, Object> jsonHash = new HashMap<>();
        jsonHash.put("senderId", senderId);
        jsonHash.put("contentType", ContentType.image.name());
        String json = objectMapper.writeValueAsString(jsonHash);
        MockMultipartFile jsonFile = new MockMultipartFile(
            "info",
            "temp.json",
            "application/json",
            json.getBytes(StandardCharsets.UTF_8)
        );

        return jsonFile;
    }

    public MockMultipartFile JSON_생성(ObjectMapper objectMapper, Long senderId, Long messageId) throws IOException{
        HashMap<String, Object> jsonHash = new HashMap<>();
        jsonHash.put("senderId", senderId);
        jsonHash.put("contentType", ContentType.image.name());
        jsonHash.put("messageId", messageId);
        String json = objectMapper.writeValueAsString(jsonHash);
        MockMultipartFile jsonFile = new MockMultipartFile(
            "info",
            "temp.json",
            "application/json",
            json.getBytes(StandardCharsets.UTF_8)
        );

        return jsonFile;
    }

    public MockMultipartFile Error_File_생성(){
        byte [] imageContent = "dummy".getBytes(StandardCharsets.UTF_8);
        MockMultipartFile pdfFile = new MockMultipartFile(
            "file",
            "error.pdf",
            MediaType.APPLICATION_PDF_VALUE,
            imageContent
        );
        return pdfFile;
    }

}

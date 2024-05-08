package com.ssafy.whoru.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.dto.ContentType;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;


@Component
public class MessageTestUtil {

    static final String EXCEED_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod leo ac quam aliquam semper. Nulla facilisi. Sed ac quam ac quam tincidunt ullamcorper. Nullam eget quam quis elit tincidunt tincidunt. Vivamus non enim eget orci tincidunt condimentum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec vel magna eget dui gravida congue. Sed ac felis sit amet ligula semper lacinia. Proin vitae mi eget quam ullamcorper semper. Sed ac quam ac quam tincidunt ullamcorper. Nullam eget quam quis elit tincidunt tincidunt. Vivamus non enim eget orci tincidunt condimentum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec vel magna eget dui gravida congue. Sed ac felis sit amet ligula semper lacinia. Proin vitae mi eget quam ullamcorper semper. Sed ac quam ac quam tincidunt ullamcorper. Nullam eget quam quis elit tincidunt tincidunt. Vivamus non enim eget orci tincidunt condimentum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec vel magna eget dui gravida congue. Sed ac felis sit amet ligula semper lacinia. Proin vitae mi eget quam ullamcorper semper. Sed ac quam ac quam tincidunt ullamcorper. Nullam eget quam quis elit tincidunt tincidunt. Vivamus non enim eget orci tincidunt condimentum. ";
    public Message Text_메세지(MockMvc mockMvc, Member sender, Member receiver, Boolean isReported){
        return Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content("test")
                .createDate(LocalDateTime.now())
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
            .createDate(LocalDateTime.now())
            .isResponse(false)
            .responseStatus(false)
            .isReported(isReported)
            .parent(null)
            .readStatus(false)
            .build();
    }

    public TextSend 길이초과_text_생성(){
        return TextSend.builder()
            .content(EXCEED_TEXT)
            .build();
    }

    public TextSend 너무짧은_text_생성(){
        return TextSend.builder()
            .content("a")
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

    public List<Message> 정상_메세지_n개_생성(MockMvc mockMvc, int size, Member sender, Member receiver){
        List<Message> messages = new ArrayList<>();
        for(int i= 0;i<size;i++){
            messages.add(Text_메세지(mockMvc, sender, receiver, false));
        }
        return messages;
    }

}

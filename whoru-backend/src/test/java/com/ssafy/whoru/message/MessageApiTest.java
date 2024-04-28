package com.ssafy.whoru.message;

import com.ssafy.whoru.TestPrepare;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.dto.request.Info;
import com.ssafy.whoru.domain.message.dto.request.TextResponseSend;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.global.common.application.S3Service;
import com.ssafy.whoru.global.common.dto.S3PathType;
import com.ssafy.whoru.global.common.exception.InvalidFileStreamException;
import java.io.IOException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;




@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
public class MessageApiTest extends TestPrepare {

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
    void 빈_토큰_FCM_전송_실패_422() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member errorMember = memberTestUtil.Member_Error_Fcm_token멤버추가(icon, mockMvc);
        memberRepository.save(member);
        memberRepository.save(errorMember);

        TextSend textSend = TextSend.builder()
            .content("fcm error")
            .senderId(member.getId())
            .build();

        mockMvc.perform(
            post("/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(textSend))
        )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.UNPROCESSABLE_ENTITY.value()));
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

    @Test
    public  void 미디어_메세지_전송_성공_201() throws Exception {
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member3001 = memberTestUtil.Member3001_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberRepository.save(member3001);



        MockMultipartFile file = messageTestUtil.이미지_생성();
        MockMultipartFile json = messageTestUtil.JSON_생성(objectMapper, member3000.getId());

        mockMvc.perform(
            multipart("/message/file")
                .file(file)
                .file(json)
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));

        Mockito.verify(s3Client, Mockito.times(1))
            .putObject(any(PutObjectRequest.class), any(RequestBody.class));

    }


    @Test
    public  void 허용되지_되지_않은_확장자_전송_실패_500() throws Exception {
        Icon icon = memberTestUtil.아이콘_추가(mockMvc);
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon, mockMvc);
        Member member3001 = memberTestUtil.Member3001_멤버추가(icon, mockMvc);
        memberRepository.save(member3000);
        memberRepository.save(member3001);

        MockMultipartFile file = messageTestUtil.Error_File_생성();
        MockMultipartFile json = messageTestUtil.JSON_생성(objectMapper, member3000.getId());

        mockMvc.perform(
                multipart("/message/file")
                    .file(file)
                    .file(json)
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));

        Mockito.verify(s3Client, Mockito.times(0))
            .putObject(any(PutObjectRequest.class), any(RequestBody.class));

    }
}

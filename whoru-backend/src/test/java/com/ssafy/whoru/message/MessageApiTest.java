package com.ssafy.whoru.message;

import com.ssafy.whoru.TestPrepare;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.global.common.dto.FcmType;
import com.ssafy.whoru.util.MemberTestUtil;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
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

        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        TextSend textSend = TextSend.builder()
                .content("반갑습니다.")
                .build();

        mockMvc.perform(
            post("/message")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(textSend))
                .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));

    }

    @Test
    void Text_메세지_전송_성공_수신측_FCM_disable_201() throws Exception{

        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);
        member3001.updateNotificationsEnabled(false);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        TextSend textSend = TextSend.builder()
            .content("반갑습니다.")
            .build();

        mockMvc.perform(
                post("/message")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(textSend))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));

    }

    @Test
    void 메세지_길이_초과_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        TextSend textSend = messageTestUtil.길이초과_text_생성();

        mockMvc.perform(
                post("/message")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(textSend))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 메세지_길이_너무짧음_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        TextSend textSend = messageTestUtil.너무짧은_text_생성();

        mockMvc.perform(
                post("/message")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(textSend))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    void 정지된_유저_매세지_전송_실패_403() throws Exception {
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);
        Member member3000 = memberTestUtil.Member3000_멤버추가(icon);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        memberTestUtil.유저_정지_먹이기(member3000);
        TextSend textSend = TextSend.builder()
                .content("정지된 유저이지롱")
                .build();

        mockMvc.perform(
                post("/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(textSend))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
        )
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.FORBIDDEN.value()));

    }
    @Test
    void Text_답장_메시지_전송_성공_201() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        Message message = messageTestUtil.Text_메세지(mockMvc, member3000, member3001, false);
        messageRepository.save(message);

        TextSend textSend = TextSend.builder()
            .content("답장메시지")
            .build();

        StringBuilder sb = new StringBuilder();
        sb.append("/message/").append(message.getId()).append("/text");

        mockMvc.perform(
                post(sb.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(textSend))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
        )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));

        Mockito.verify(fcmUtil, Mockito.times(1))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));

    }

    @Test
    void 잘못된_Message_id_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        Message message = messageTestUtil.Text_메세지(mockMvc, member3000, member3001, false);
        messageRepository.save(message);

        TextSend textSend = TextSend.builder()
            .content("답장메시지")
            .build();

        StringBuilder sb = new StringBuilder();
        sb.append("/message/").append(0).append("/text");

        mockMvc.perform(
                post(sb.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(textSend))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));

        Mockito.verify(fcmUtil, Mockito.times(0))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));
    }

    @Test
    void 정지된_유저_답장_전송_실패_403() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        Message message = messageTestUtil.Text_메세지(mockMvc, member3000, member3001, false);
        messageRepository.save(message);
        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);
        memberTestUtil.유저_정지_먹이기(member3001);

        TextSend textSend = TextSend.builder()
            .content("답장메시지")
            .build();
        StringBuilder sb = new StringBuilder();
        sb.append("/message/").append(message.getId()).append("/text");

        mockMvc.perform(
                        post(sb.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(textSend))
                            .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
                )
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.FORBIDDEN.value()));

        Mockito.verify(fcmUtil, Mockito.times(0))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));
    }

    @Test
    void 신고된_Text_메세지에_답장_전송_실패_451() throws Exception {
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);
        Message message = messageTestUtil.Text_메세지(mockMvc, member3000, member3001, true);
        messageRepository.save(message);

        TextSend textSend = TextSend.builder()
            .content("답장메시지")
            .build();
        StringBuilder sb = new StringBuilder();
        sb.append("/message/").append(message.getId()).append("/text");

        mockMvc.perform(
          post(sb.toString())
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(textSend))
              .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.errorCode").value(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value()));

        Mockito.verify(fcmUtil, Mockito.times(0))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));
    }

    @Test
    public void 미디어_메세지_전송_성공_201() throws Exception {
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        MockMultipartFile file = messageTestUtil.이미지_생성();
        MockMultipartFile json = messageTestUtil.JSON_생성(objectMapper, member3000.getId());

        mockMvc.perform(
            multipart("/message/file")
                .file(file)
                .file(json)
                .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));

        Mockito.verify(s3Client, Mockito.times(1))
            .putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }


    @Test
    public  void 허용되지_되지_않은_확장자_전송_실패_415() throws Exception {
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        MockMultipartFile file = messageTestUtil.Error_File_생성();
        MockMultipartFile json = messageTestUtil.JSON_생성(objectMapper, member3000.getId());

        mockMvc.perform(
                multipart("/message/file")
                    .file(file)
                    .file(json)
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));

        Mockito.verify(s3Client, Mockito.times(0))
            .putObject(any(PutObjectRequest.class), any(RequestBody.class));

        Mockito.verify(fcmUtil, Mockito.times(0))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));

    }

    @Test
    void 정지된_유저_미디어_메세지_전송_실패_403() throws Exception {
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        MockMultipartFile file = messageTestUtil.이미지_생성();
        MockMultipartFile json = messageTestUtil.JSON_생성(objectMapper, member3000.getId());

        memberTestUtil.유저_정지_먹이기(member3000);

        mockMvc.perform(
            multipart("/message/file")
                .file(file)
                .file(json)
                .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
        )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.FORBIDDEN.value()));

        Mockito.verify(s3Client, Mockito.times(0))
            .putObject(any(PutObjectRequest.class), any(RequestBody.class));

        Mockito.verify(fcmUtil, Mockito.times(0))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));
    }


    @Test
    void 미디어_답장_메세지_전송_성공_201() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        Message message = messageTestUtil.미디어_메세지(mockMvc, member3000, member3001, false);
        messageRepository.save(message);

        MockMultipartFile file = messageTestUtil.이미지_생성();

        StringBuilder sb = new StringBuilder();
        sb.append("/message/").append(message.getId()).append("/file");

        mockMvc.perform(
            multipart(sb.toString())
                .file(file)
                .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
        )   .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));

        Mockito.verify(s3Client, Mockito.times(1))
            .putObject(any(PutObjectRequest.class), any(RequestBody.class));

        Mockito.verify(fcmUtil, Mockito.times(1))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));
    }

    @Test
    void 잘못된_미디어_message_id_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        Message message = messageTestUtil.미디어_메세지(mockMvc, member3000, member3001, false);
        messageRepository.save(message);

        MockMultipartFile file = messageTestUtil.이미지_생성();

        StringBuilder sb = new StringBuilder();
        sb.append("/message/").append(0).append("/file");

        mockMvc.perform(
                multipart(sb.toString())
                    .file(file)
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )   .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));

        Mockito.verify(s3Client, Mockito.times(0))
            .putObject(any(PutObjectRequest.class), any(RequestBody.class));

        Mockito.verify(fcmUtil, Mockito.times(0))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));
    }

    @Test
    public  void 허용되지_되지_않은_확장자_답장전송_실패_415() throws Exception {
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        Message message = messageTestUtil.미디어_메세지(mockMvc, member3000, member3001, false);
        messageRepository.save(message);

        MockMultipartFile file = messageTestUtil.Error_File_생성();

        StringBuilder sb = new StringBuilder();
        sb.append("/message/").append(message.getId()).append("/file");

        mockMvc.perform(
                multipart(sb.toString())
                    .file(file)
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()));

        Mockito.verify(s3Client, Mockito.times(0))
            .putObject(any(PutObjectRequest.class), any(RequestBody.class));

        Mockito.verify(fcmUtil, Mockito.times(0))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));

    }

    @Test
    void 정지된_유저_미디어_답장_전송_실패_403() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        Message message = messageTestUtil.미디어_메세지(mockMvc, member3000, member3001, false);
        messageRepository.save(message);


        memberTestUtil.유저_정지_먹이기(member3001);
        MockMultipartFile file = messageTestUtil.이미지_생성();

        StringBuilder sb = new StringBuilder();
        sb.append("/message/").append(message.getId()).append("/file");

        mockMvc.perform(
                multipart(sb.toString())
                    .file(file)
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.FORBIDDEN.value()));

        Mockito.verify(s3Client, Mockito.times(0))
            .putObject(any(PutObjectRequest.class), any(RequestBody.class));

        Mockito.verify(fcmUtil, Mockito.times(0))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));
    }

    @Test
    void 신고된_미디어_메세지에_답장_전송_실패_451() throws Exception {
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        Message message = messageTestUtil.미디어_메세지(mockMvc, member3000, member3001, true);
        messageRepository.save(message);


        MockMultipartFile file = messageTestUtil.이미지_생성();

        StringBuilder sb = new StringBuilder();
        sb.append("/message/").append(message.getId()).append("/file");

        mockMvc.perform(
                multipart(sb.toString())
                    .file(file)
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value()));

        Mockito.verify(s3Client, Mockito.times(0))
            .putObject(any(PutObjectRequest.class), any(RequestBody.class));

        Mockito.verify(fcmUtil, Mockito.times(0))
            .sendMessage(any(String.class), any(Long.class), any(
                FcmType.class));
    }

    @Test
    void 최근_메세지_목록조회_성공_200() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.정상_메세지_n개_생성(mockMvc, 20, member3000, member3001);
        messageRepository.saveAll(messages);
        Message top = messageRepository.findTopByOrderByIdDesc();
        StringBuilder sb = new StringBuilder();
        sb.append("/message/recent?firstid=").append(top.getId()/2);

        mockMvc.perform(
            get(sb.toString())
                .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.data.length()").value(messages.size()));
    }

    @Test
    void 최근_메세지_목록조회_성공_하지만_비었음_204() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.정상_메세지_n개_생성(mockMvc, 20, member3000, member3001);
        messageRepository.saveAll(messages);
        Message top = messageRepository.findTopByOrderByIdDesc();
        StringBuilder sb = new StringBuilder();
        sb.append("/message/recent?firstid=").append(top.getId());

        mockMvc.perform(
                get(sb.toString())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void 최근_메세지_목록조회_firstid_누락_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.정상_메세지_n개_생성(mockMvc, 20, member3000, member3001);
        messageRepository.saveAll(messages);

        mockMvc.perform(
                get("/message/recent")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 최근_메세지_목록조회_firstid_가_1보다_작음_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.정상_메세지_n개_생성(mockMvc, 20, member3000, member3001);
        messageRepository.saveAll(messages);
        StringBuilder sb = new StringBuilder();
        sb.append("/message/recent?firstid=").append(0);

        mockMvc.perform(
                get(sb.toString())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 이전_메세지_목록조회_성공_200() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.정상_메세지_n개_생성(mockMvc, 20, member3000, member3001);
        messageRepository.saveAll(messages);

        mockMvc.perform(
                get("/message?size=20")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.data.content.length()").value(messages.size()));
    }

    @Test
    void 이전_메세지_목록조회_성공_하지만_비었음_204() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        Message first = messageTestUtil.Text_메세지(mockMvc, member3000, member3001, false);
        List<Message> messages = messageTestUtil.정상_메세지_n개_생성(mockMvc, 19, member3000, member3001);

        messageRepository.save(first);
        messageRepository.saveAll(messages);

        StringBuilder sb = new StringBuilder();
        sb.append("/message?size=20&lastid=").append(first.getId());

        mockMvc.perform(
                get(sb.toString())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void 이전_메세지_목록조회_size_누락_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        mockMvc.perform(
                get("/message")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 이전_메세지_목록조회_size_가_20보다_작음_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        mockMvc.perform(
                get("/message?size=19")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 이전_메세지_목록조회_lastid_가_1보다_작음_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        mockMvc.perform(
                get("/message?size=20&lastid=0")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }


    @Test
    void 최근_데일리메세지_목록조회_성공_200() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.정상_메세지_n개_생성(mockMvc, 20, member3000, member3001);
        messageRepository.saveAll(messages);
        Message top = messageRepository.findTopByOrderByIdDesc();
        StringBuilder sb = new StringBuilder();
        sb.append("/message/daily/recent?firstid=").append(top.getId()/2);

        mockMvc.perform(
                get(sb.toString())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.data.length()").value(messages.size()));
    }

    @Test
    void 최근_데일리메세지_목록조회_성공_하지만_비었음_204() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.이전날짜_메세지_n개_생성(mockMvc, 20, member3000, member3001, false);
        messageRepository.saveAll(messages);
        Message top = messageRepository.findTopByOrderByIdDesc();
        StringBuilder sb = new StringBuilder();
        sb.append("/message/daily/recent?firstid=").append(top.getId());

        mockMvc.perform(
                get(sb.toString())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void 최근_데일리메세지_목록조회_firstid_누락_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.정상_메세지_n개_생성(mockMvc, 20, member3000, member3001);
        messageRepository.saveAll(messages);

        mockMvc.perform(
                get("/message/daily/recent")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 최근_데일리메세지_목록조회_firstid_가_1보다_작음_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.정상_메세지_n개_생성(mockMvc, 20, member3000, member3001);
        messageRepository.saveAll(messages);
        StringBuilder sb = new StringBuilder();
        sb.append("/message/daily/recent?firstid=").append(0);

        mockMvc.perform(
                get(sb.toString())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 이전_데일리메세지_목록조회_성공_200() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        List<Message> messages = messageTestUtil.정상_메세지_n개_생성(mockMvc, 20, member3000, member3001);
        messageRepository.saveAll(messages);

        mockMvc.perform(
                get("/message/daily/old?size=20")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.data.content.length()").value(messages.size()));
    }

    @Test
    void 이전_데일리메세지_목록조회_성공_하지만_비었음_204() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        Message first = messageTestUtil.이전날_Text_메세지(mockMvc, member3000, member3001, false);
        List<Message> messages = messageTestUtil.이전날짜_메세지_n개_생성(mockMvc, 19, member3000, member3001, false);

        messageRepository.save(first);
        messageRepository.saveAll(messages);

        StringBuilder sb = new StringBuilder();
        sb.append("/message/daily/old?size=20&lastid=").append(first.getId());

        mockMvc.perform(
                get(sb.toString())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void 이전_데일리메세지_목록조회_size_누락_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        mockMvc.perform(
                get("/message/daily/old")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 이전_데일리메세지_목록조회_size_가_20보다_작음_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        mockMvc.perform(
                get("/message/daily/old?size=19")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 이전_데일리메세지_목록조회_lastid_가_1보다_작음_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        mockMvc.perform(
                get("/message/daily/old??size=20&lastid=0")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }


    @Test
    void 최근_우편함_목록조회_성공_200() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.우편함_메세지_n개_생성(mockMvc, member3000, 20);
        messageRepository.saveAll(messages);
        StringBuilder sb = new StringBuilder();
        sb.append("/message/undelivered-message/recent?firstid=").append(messages.getLast().getId()/2);

        mockMvc.perform(
                get(sb.toString())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.data.length()").value(messages.size()));
    }

    @Test
    void 최근_우편함_목록조회_성공_하지만_비었음_204() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        List<Message> messages = messageTestUtil.우편함_메세지_n개_생성(mockMvc, member3000, 20);
        messageRepository.saveAll(messages);
        Message top = messageRepository.findTopByOrderByIdDesc();
        StringBuilder sb = new StringBuilder();
        sb.append("/message/undelivered-message/recent?firstid=").append(top.getId());

        mockMvc.perform(
                get(sb.toString())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void 최근_우편함_목록조회_firstid_누락_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.우편함_메세지_n개_생성(mockMvc,  member3000, 20);
        messageRepository.saveAll(messages);

        mockMvc.perform(
                get("/message/undelivered-message/recent")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 최근_우편함_목록조회_firstid_가_1보다_작음_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.우편함_메세지_n개_생성(mockMvc,  member3000, 20);
        messageRepository.saveAll(messages);
        StringBuilder sb = new StringBuilder();
        sb.append("/message/undelivered-message/recent?firstid=").append(0);

        mockMvc.perform(
                get(sb.toString())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 이전_우편함_목록조회_성공_200() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        List<Message> messages = messageTestUtil.우편함_메세지_n개_생성(mockMvc,  member3000, 20);
        messageRepository.saveAll(messages);

        mockMvc.perform(
                get("/message/undelivered-message/old?size=20")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.data.content.length()").value(messages.size()));
    }

    @Test
    void 이전_우편함_목록조회_성공_하지만_비었음_204() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3000 = memberList.get(0);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        List<Message> messages = messageTestUtil.우편함_메세지_n개_생성(mockMvc,  member3000, 20);
        messageRepository.saveAll(messages);

        StringBuilder sb = new StringBuilder();
        sb.append("");

        mockMvc.perform(
                get("/message/undelivered-message/old?size=20")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void 이전_우편함_목록조회_size_누락_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        mockMvc.perform(
                get("/message/undelivered-message/old")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 이전_우편함_목록조회_size_가_20보다_작음_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        mockMvc.perform(
                get("/message/undelivered-message/old?size=19")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 이전_우편함_목록조회_lastid_가_1보다_작음_400() throws Exception{
        List<Member> memberList = memberTestUtil.기본_멤버_세팅();
        Member member3001 = memberList.get(1);

        String header3001 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3001);

        mockMvc.perform(
                get("/message/undelivered-message/old??size=20&lastid=0")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3001)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }
}

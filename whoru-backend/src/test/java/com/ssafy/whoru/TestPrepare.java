package com.ssafy.whoru;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.whoru.domain.collect.dao.IconRepository;
import com.ssafy.whoru.domain.member.dao.MemberRepository;
import com.ssafy.whoru.domain.message.dao.MessageRepository;
import com.ssafy.whoru.global.common.application.S3Service;
import com.ssafy.whoru.global.util.RedisUtil;
import com.ssafy.whoru.util.MemberTestUtil;
import com.ssafy.whoru.util.MessageTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import software.amazon.awssdk.services.s3.S3Client;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class TestPrepare {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected RedisUtil redisUtil;

    @Autowired
    protected MessageRepository messageRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected IconRepository collectRepository;

    @Autowired
    protected MemberTestUtil memberTestUtil;

    @Autowired
    protected MessageTestUtil messageTestUtil;

    @MockBean
    protected S3Client s3Client;

    @BeforeEach
    public void mockBeanSetup(){
        reset(s3Client);
    }


}

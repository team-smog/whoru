package com.ssafy.whoru.domain.message.application;

import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.response.MemberResponse;
import com.ssafy.whoru.domain.member.service.MemberService;
import com.ssafy.whoru.domain.message.dao.MessageRepository;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.dto.ContentType;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.domain.message.dto.response.MessageResponse;
import com.ssafy.whoru.domain.message.exception.BannedSenderException;
import com.ssafy.whoru.global.common.domain.RedisKeyType;
import com.ssafy.whoru.global.util.FCMUtil;
import com.ssafy.whoru.global.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService{

    MessageRepository messageRepository;

    MemberService memberService;

    RedisUtil redisUtil;

    FCMUtil fcmUtil;

    static final Integer BOX_COUNT_INIT = 0;
    static final Integer BOX_LIMIT = 3;

    @Override
    public void sendTextMessageToRandomMember(TextSend textSend) {

        String key = RedisKeyType.BAN.makeKey(String.valueOf(textSend.getSenderId()));
        // 정지여부 체크
        Optional<String> banResult = redisUtil.findValueByKey(key);
        if(banResult.isPresent()){
            throw new BannedSenderException();
        }
        // randombox 카운트
        key = RedisKeyType.TODAY_BOX.makeKey(String.valueOf(textSend.getSenderId()));
        Optional<String> boxCount = redisUtil.findValueByKey(key);

        // 현재 redis에 남아있는 오늘의 박스 얻은 횟수 가져오기
        Integer presentBoxCount = BOX_COUNT_INIT;
        if(boxCount.isPresent()){
            presentBoxCount = Integer.parseInt(boxCount.get());
        }

        if(presentBoxCount < BOX_LIMIT){
            // db상에 boxcount도 증가시켜야 하고
            memberService.updateBoxCount(textSend.getSenderId());
            // redis에서도 새롭게 +1 된 값을 반영해야 함

            // 날짜 바뀌는 시간 구하기
            LocalDateTime next = LocalDate.now().plusDays(1).atStartOfDay();

            redisUtil.insert(key, String.valueOf(presentBoxCount+1), Duration.between(LocalDateTime.now(), next).getSeconds());
        }

        MemberResponse sender = memberService.findOne(textSend.getSenderId());
        MemberResponse receiver = memberService.findRandom(textSend.getSenderId());

        // fcm 발송
        fcmUtil.sendMessage(receiver.getFcmToken());

        // message 전송
        messageRepository.save(
                Message.builder()
                        .content(textSend.getContent())
                        .contentType(ContentType.text)
                        .senderId(sender.toEntity())
                        .receiverId(receiver.toEntity())
                        .isReported(false)
                        .readStatus(false)
                        .parent(null)
                        .isResponse(false)
                        .responseStatus(false)
                        .build()
        );
    }
}

package com.ssafy.whoru.domain.message.application;

import com.ssafy.whoru.domain.member.application.CrossMemberService;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.member.dto.response.MemberResponse;
import com.ssafy.whoru.domain.member.application.MemberService;
import com.ssafy.whoru.domain.message.dao.MessageRepository;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.dto.ContentType;
import com.ssafy.whoru.domain.message.dto.request.TextResponseSend;
import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.domain.message.exception.BannedSenderException;
import com.ssafy.whoru.domain.message.exception.MessageNotFoundException;
import com.ssafy.whoru.domain.message.exception.ReportedMessageException;
import com.ssafy.whoru.domain.message.util.MessageUtil;
import com.ssafy.whoru.global.common.domain.RedisKeyType;
import com.ssafy.whoru.global.util.FCMUtil;
import com.ssafy.whoru.global.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService{

    MessageRepository messageRepository;

    CrossMemberService memberService;

    RedisUtil redisUtil;

    FCMUtil fcmUtil;

    MessageUtil messageUtil;

    static final Integer BOX_COUNT_INIT = 0;
    static final Integer BOX_LIMIT = 3;

    @Override
    public void sendTextMessageToRandomMember(TextSend textSend) {

        // 정지여부 체크
        if(messageUtil.isBanned(textSend.getSenderId())){
            throw new BannedSenderException();
        }
        // randombox 카운트
        String key = RedisKeyType.TODAY_BOX.makeKey(String.valueOf(textSend.getSenderId()));
        Optional<String> boxCount = redisUtil.findValueByKey(key);

        // db에서 두사람 정보 들고오기
        Member sender = memberService.findByIdToEntity(textSend.getSenderId());
        Member receiver = memberService.findRandomToEntity(textSend.getSenderId());

        // 현재 redis에 남아있는 오늘의 박스 얻은 횟수 가져오기
        Integer presentBoxCount = BOX_COUNT_INIT;
        if(boxCount.isPresent()){
            presentBoxCount = Integer.parseInt(boxCount.get());
        }

        if(presentBoxCount < BOX_LIMIT){
            // db상에 boxcount도 증가시켜야 하고
            sender.updateBoxIncrease();

            // redis에서도 새롭게 +1 된 값을 반영해야 함
            // 날짜 바뀌는 시간 구하기
            LocalDateTime next = LocalDate.now().plusDays(1).atStartOfDay();

            redisUtil.insert(key, String.valueOf(presentBoxCount+1), Duration.between(LocalDateTime.now(), next).getSeconds());
        }



        // fcm 발송
        fcmUtil.sendMessage(receiver.getFcmToken());

        // message 전송
        messageRepository.save(
                Message.builder()
                        .content(textSend.getContent())
                        .contentType(ContentType.text)
                        .sender(sender)
                        .receiver(receiver)
                        .isReported(false)
                        .readStatus(false)
                        .parent(null)
                        .isResponse(false)
                        .responseStatus(false)
                        .build()
        );
    }

    @Override
    public void responseTextMessage(TextResponseSend responseSend) {
        if(messageUtil.isBanned(responseSend.getSenderId())){
            throw new BannedSenderException();
        }

        Optional<Message> result = messageRepository.findById(responseSend.getMessageId());
        Message targetMessage = result.orElseThrow(MessageNotFoundException::new);
        if(targetMessage.getIsReported()){
            throw new ReportedMessageException();
        }

        Message responseMessage = Message.builder()
                .contentType(ContentType.text)
                .content(responseSend.getContent())
                .sender(targetMessage.getReceiver())
                .receiver(targetMessage.getSender())
                .responseStatus(true)
                .parent(targetMessage)
                .readStatus(false)
                .isReported(false)
                .isResponse(true)
                .build();

        fcmUtil.sendMessage(targetMessage.getSender().getFcmToken());

        messageRepository.save(responseMessage);
    }
}

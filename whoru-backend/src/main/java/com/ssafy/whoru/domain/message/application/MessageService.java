package com.ssafy.whoru.domain.message.application;

import com.ssafy.whoru.domain.message.dto.request.TextSend;
import com.ssafy.whoru.domain.message.dto.response.MessageResponse;
import com.ssafy.whoru.domain.message.dto.response.SendResponse;
import com.ssafy.whoru.global.common.dto.response.ResponseWithSuccess;
import com.ssafy.whoru.domain.message.dto.response.SliceMessageResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


public interface MessageService {
    SendResponse sendTextMessage(TextSend textSend, Long senderId);

    void responseTextMessage(TextSend textSend, Long senderId, Long messageId);

    SendResponse sendMediaMessage(MultipartFile file, Long senderId);

    void responseFileMessage(MultipartFile file, Long senderId, Long messageId);

    ResponseWithSuccess<SliceMessageResponse> getOldMessages(Long lastId, Integer size, Long receiverId);

    ResponseWithSuccess<List<MessageResponse>> getRecentMessages(Long firstId, Integer size, Long receiverId);

    MessageResponse findMessage(Long messageId);

    ResponseWithSuccess<SliceMessageResponse> getDailyOldMessages(Long lastId, Integer size);

    ResponseWithSuccess<List<MessageResponse>> getDailyRecentMessages(Long firstId, Integer size);

    void updateReceiver(Long messageId, Long memberId);

    ResponseWithSuccess<List<MessageResponse>> getNotReceivedRecentMessages(Long firstId, Integer size, Long memberId);

    ResponseWithSuccess<SliceMessageResponse> getNotReceivedOldMessages(Long lastId, Integer size, Long memberId);
}

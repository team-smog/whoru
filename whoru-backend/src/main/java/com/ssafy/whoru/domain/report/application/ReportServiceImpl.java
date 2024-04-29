package com.ssafy.whoru.domain.report.application;

import com.ssafy.whoru.domain.member.application.CrossMemberService;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.application.CrossMessageService;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.message.exception.BannedSenderException;
import com.ssafy.whoru.domain.report.dao.ReportRepository;
import com.ssafy.whoru.domain.report.domain.Report;
import com.ssafy.whoru.domain.report.dto.ReportType;
import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;
import com.ssafy.whoru.domain.report.exception.DuplicatedReportException;
import com.ssafy.whoru.domain.report.util.ReportUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final ReportUtil reportUtil;

    private final ReportRepository reportRepository;

    private final CrossMemberService crossMemberService;

    private final CrossMessageService crossMessageService;

    @Override
    @Transactional
    public void reportMember(PostReportRequest request) {

        // 정지여부 체크
        if(reportUtil.isBanned(request.getSenderId())){
            throw new BannedSenderException();
        }

        //메시지 조회 후 신고당했는 메시지인지 여부 체크
        Message message = crossMessageService.findByIdToEntity(request.getMessageId());

        //기본값이 false인지 null인지 체크
        Boolean isReported = message.getIsReported();
        if(isReported.equals(true)) {
            throw new DuplicatedReportException();
        }


        //안당했다면
        //신고 테이블 로우 추가
        Member sender = crossMemberService.findByIdToEntity(request.getSenderId());
        reportRepository.save(Report.builder()
            .member(sender)
            .message(message)
            .reportType(ReportType.SPAM)
            .build());

        //메시지 로우 value 수정
        message.updateIsReported(true);

        return;

    }

}

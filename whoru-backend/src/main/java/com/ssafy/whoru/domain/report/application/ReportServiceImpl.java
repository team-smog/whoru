package com.ssafy.whoru.domain.report.application;

import com.ssafy.whoru.domain.member.application.CrossMemberService;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.application.CrossMessageService;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.report.dao.BanRepository;
import com.ssafy.whoru.domain.report.dao.ReportRepository;
import com.ssafy.whoru.domain.report.domain.Ban;
import com.ssafy.whoru.domain.report.domain.Report;
import com.ssafy.whoru.domain.report.dto.ReportType;
import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;
import com.ssafy.whoru.domain.report.dto.response.ReportRecordResponse;
import com.ssafy.whoru.domain.report.dto.response.SliceResponse;
import com.ssafy.whoru.domain.report.exception.AlreadyBanException;
import com.ssafy.whoru.domain.report.exception.DuplicatedReportException;
import com.ssafy.whoru.domain.report.util.ReportUtil;
import com.ssafy.whoru.global.util.RedisUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final RedisUtil redisUtil;

    private final ReportUtil reportUtil;

    private final ReportRepository reportRepository;

    private final BanRepository banRepository;

    private final CrossMemberService crossMemberService;

    private final CrossMessageService crossMessageService;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void reportMember(PostReportRequest request) {

        // 정지여부 체크
        if(reportUtil.isBanned(request.getSenderId())){
            throw new DuplicatedReportException();
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

    }

    @Override
    @Transactional
    public void banMember(Long memberId) {

        // 정지여부 체크
        if(reportUtil.isBanned(memberId)){
            throw new AlreadyBanException();
        }

        // DB IO
        Member member = crossMemberService.findByIdToEntity(memberId);

        Ban ban = banRepository.save(Ban.builder()
            .member(member)
            .endDate(LocalDateTime.now().plusDays(3))
            .build());

        // Redis 갱신
        // DB IO Time과의 차이가 있을 수 있기 때문에 Duration으로 현재 값 비교.
        redisUtil.insert(memberId.toString(), ban.getId().toString(), Duration.between(LocalDateTime.now(), ban.getEndDate()).getSeconds());

        // reportCount zero init
        member.updateReportcountZeroInit();

    }

    @Override
    public SliceResponse<ReportRecordResponse> getReportRecord(int page, int size,
        ReportType reportType) {

        // 페이징 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "reportDate"));

        Slice<Report> result = null;

        if(reportType == null) {    //페이징 조건이 없을 경우
            result = reportRepository.findByDefaultCondition(pageable);
        }
        else {
            result = reportRepository.findByCondition(reportType, pageable);
        }

        // Entity to DTO
        Slice<ReportRecordResponse> dtoSlice = result.map(report -> modelMapper.map(report, ReportRecordResponse.class));

        return new SliceResponse<>(dtoSlice);
    }

}

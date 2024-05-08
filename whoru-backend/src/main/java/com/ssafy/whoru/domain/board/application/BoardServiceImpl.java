package com.ssafy.whoru.domain.board.application;


import com.ssafy.whoru.domain.board.dao.BoardRepository;
import com.ssafy.whoru.domain.board.dao.CommentRepository;
import com.ssafy.whoru.domain.board.domain.Board;
import com.ssafy.whoru.domain.board.domain.Comment;
import com.ssafy.whoru.domain.board.dto.BoardType;
import com.ssafy.whoru.domain.board.dto.request.PatchInquiryCommentRequest;
import com.ssafy.whoru.domain.board.dto.request.PatchNotificationRequest;
import com.ssafy.whoru.domain.board.dto.request.PostInquiryBoardRequest;
import com.ssafy.whoru.domain.board.dto.request.PostInquiryCommentRequest;
import com.ssafy.whoru.domain.board.dto.request.PostNotificationRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryRecordResponse;
import com.ssafy.whoru.domain.board.dto.response.NotificationResponse;
import com.ssafy.whoru.domain.board.exception.BoardNotFoundException;
import com.ssafy.whoru.domain.board.exception.CommentNotFoundException;
import com.ssafy.whoru.domain.board.exception.NotSameWriterException;
import com.ssafy.whoru.domain.member.application.CrossMemberService;
import com.ssafy.whoru.domain.member.domain.FcmNotification;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.global.common.dto.SliceResponse;
import com.ssafy.whoru.global.common.dto.SuccessType;
import com.ssafy.whoru.global.common.dto.response.ResponseWithSuccess;
import com.ssafy.whoru.global.util.FCMUtil;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final CrossMemberService crossMemberService;

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    private final ModelMapper modelMapper;

    private final FCMUtil fcmUtil;

    private static final String NOTIFICATION_TITLE = "공지사항입니다.";

    private static final String NOTIFICATION_CONTENT = "새로운 공지사항이 올라왔어요";

    @Override
    public void postInquiryBoard(Long memberId, PostInquiryBoardRequest request) {

        Member member = crossMemberService.findByIdToEntity(memberId);

        boardRepository.save(Board.builder()
                .boardType(BoardType.INQUIRY)
                .writer(member)
                .subject(request.getSubject())
                .content(request.getContent())
                .build());

    }

    /**
     * Comment가 달린 게시글인지 확인
     * **/
    protected static void isCommentExist(Slice<Board> result) {
        for(Board board : result) {
            if(board.getComment() != null) {
                board.updateIsCommented(true);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SliceResponse<InquiryRecordResponse> getInquiryBoard(Long memberId, int page, int size) {

        Member member = crossMemberService.findByIdToEntity(memberId);

        // 페이징 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));

        Slice<Board> result = boardRepository.findByMember(member, pageable);


        isCommentExist(result);

        // Entity to DTO
        Slice<InquiryRecordResponse> response = result.map(board -> modelMapper.map(board, InquiryRecordResponse.class));

        return new SliceResponse<>(response);

    }



    @Override
    @Transactional(readOnly = true)
    public SliceResponse<InquiryRecordResponse> getTotalInquiry(int page, int size,
        int condition) {

        // 페이징 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));

        Slice<Board> result = null;

        if(condition == 0) {
            //답글 여부와 상관없이 전체 조회 쿼리 실행
            result = boardRepository.findAllByType(pageable, BoardType.INQUIRY);
        }

        else {
            //답글이 달리지 않은 문의사항에 한하여 조회 쿼리 실행
            result = boardRepository.findAllByCommentAndType(pageable, BoardType.INQUIRY);
        }


        isCommentExist(result);

        // Entity to DTO
        Slice<InquiryRecordResponse> response = result.map(board -> modelMapper.map(board, InquiryRecordResponse.class));

        return new SliceResponse<>(response);
    }

    @Override
    @Transactional
    public void postComment(PostInquiryCommentRequest request) {

        // 관리자 조회
        Member member = crossMemberService.findByIdToEntity(request.getCommenterId());

        // 게시글 조회
        Optional<Board> board = Optional.of(boardRepository.findById(request.getBoardId())
            .orElseThrow(BoardNotFoundException::new));

        // 답글 저장
        commentRepository.save(Comment.builder()
            .board(board.get())
            .commenter(member)
            .content(request.getContent())
            .build());

    }

    @Override
    @Transactional
    public void deleteInquiryBoard(Long memberId, Long boardId) {

        Member member = crossMemberService.findByIdToEntity(memberId);

        Optional<Board> board = Optional.of(boardRepository.findById(boardId)
            .orElseThrow(BoardNotFoundException::new));

        if(member.equals(board.get().getWriter())) {
            boardRepository.deleteById(boardId);
        }
        else {
            throw new NotSameWriterException();
        }

    }

    @Override
    @Transactional
    public void patchComment(Long commentId, PatchInquiryCommentRequest request) {

        //관리자 답글 수정의 경우 어떤 관리자가 수정해도 상관없도록 구현
        Optional<Comment> comment = Optional.ofNullable(commentRepository.findById(commentId)
            .orElseThrow(CommentNotFoundException::new));

        comment.get().patchContent(request.getContent());

    }

    @Override
    public void postNotification(Long adminId, PostNotificationRequest postNotificationRequest) {
        Member admin = crossMemberService.findByIdToEntity(adminId);
        Board noti = Board.builder()
            .boardType(BoardType.NOTIFICATION)
            .subject(postNotificationRequest.getSubject())
            .content(postNotificationRequest.getContent())
            .writer(admin)
            .isCommented(false)
            .build();

        boardRepository.save(noti);
        List<Member> allUsers = crossMemberService.findAllMemberEntities();
        String [] tokens = allUsers.stream()
            .filter( member ->member.getFcmNotification()!= null && member.getFcmNotification().getIsEnabled())
            .map( member -> member.getFcmNotification().getFcmToken())
            .toArray(String []::new);
        LocalDateTime createDate = noti.getCreateDate();
        final String dateTitle = fcmUtil.makeDateTitle(NOTIFICATION_TITLE, createDate);
        for(String token: tokens){
            fcmUtil.sendMessage(token, dateTitle, NOTIFICATION_CONTENT);
        }
    }

    @Override
    @Transactional
    public void patchNotification(Long adminId, PatchNotificationRequest patchNotificationRequest, Long boardId) {
        Optional<Board> result = boardRepository.findById(boardId);
        Board board = result.orElseThrow(BoardNotFoundException::new);
        String willChangeSubject = patchNotificationRequest.getSubject();
        String willChangeContent = patchNotificationRequest.getContent();
        if(willChangeSubject != null){
            board.updateSubject(willChangeSubject);
        }
        if(willChangeContent != null){
            board.updateContent(willChangeContent);
        }
    }

    @Override
    public ResponseWithSuccess<SliceResponse<NotificationResponse>> findNotifications(int page, int size) {

        // 페이징 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        Slice<Board> result = boardRepository.findAllByCommentAndType(pageable, BoardType.NOTIFICATION);
        Slice<NotificationResponse> body = result.map(board -> modelMapper.map(board, NotificationResponse.class));
        SliceResponse<NotificationResponse> responseBody = new SliceResponse<>(body);
        ResponseWithSuccess<SliceResponse<NotificationResponse>> response = new ResponseWithSuccess<>(responseBody);
        if (result.getContent().isEmpty()){
            response = new ResponseWithSuccess<>(responseBody, SuccessType.STATUS_204);
        }
        return response;
    }

    /**
     * ModelMapper 설정 최초 1회 진행
     * Member Entity to UserName Dto Setting
     * **/
    @PostConstruct
    void init() {
        modelMapper.addMappings(new PropertyMap<Board, InquiryRecordResponse>() {
            protected void configure() {
                map().setWriterName(source.getWriter().getUserName());
            }
        });
    }

}

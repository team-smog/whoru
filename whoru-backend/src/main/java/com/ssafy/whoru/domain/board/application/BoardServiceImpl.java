package com.ssafy.whoru.domain.board.application;


import com.ssafy.whoru.domain.board.dao.BoardRepository;
import com.ssafy.whoru.domain.board.dao.CommentRepository;
import com.ssafy.whoru.domain.board.domain.Board;
import com.ssafy.whoru.domain.board.domain.Comment;
import com.ssafy.whoru.domain.board.dto.BoardType;
import com.ssafy.whoru.domain.board.dto.request.PostBoardRequest;
import com.ssafy.whoru.domain.board.dto.request.PostCommentRequest;
import com.ssafy.whoru.domain.board.dto.response.InquiryRecordResponse;
import com.ssafy.whoru.domain.board.exception.BoardNotFoundException;
import com.ssafy.whoru.domain.board.exception.NotSameWriterException;
import com.ssafy.whoru.domain.collect.exception.IconNotFoundException;
import com.ssafy.whoru.domain.member.application.CrossMemberService;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.global.common.dto.SliceResponse;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void postInquiryBoard(PostBoardRequest request) {

        Member member = crossMemberService.findByIdToEntity(request.getMemberId());

        boardRepository.save(Board.builder()
                .boardType(BoardType.INQUIRY)
                .writer(member)
                .subject(request.getSubject())
                .content(request.getContent())
                .build());

    }

    @Override
    public SliceResponse<InquiryRecordResponse> getInquiryBoard(Long memberId, int page, int size) {

        Member member = crossMemberService.findByIdToEntity(memberId);

        // 페이징 객체 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));

        Slice<Board> result = boardRepository.findByMember(member, pageable);

        /**
         * Comment가 달린 게시글인지 확인
         * **/
        for(Board board : result) {
            if(board.getComment() != null) {
                board.updateIsCommented(true);
            }
        }
        // Entity to DTO
        Slice<InquiryRecordResponse> response = result.map(board -> modelMapper.map(board, InquiryRecordResponse.class));

        return new SliceResponse<>(response);

    }

    @Override
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
            result = boardRepository.findByComment(pageable, BoardType.INQUIRY);
        }

        /**
         * Comment가 달린 게시글인지 확인
         * **/
        for(Board board : result) {
            if(board.getComment() != null) {
                board.updateIsCommented(true);
            }
        }

        // Entity to DTO
        Slice<InquiryRecordResponse> response = result.map(board -> modelMapper.map(board, InquiryRecordResponse.class));

        return new SliceResponse<>(response);
    }

    @Override
    @Transactional
    public void postComment(PostCommentRequest request) {

        Member member = crossMemberService.findByIdToEntity(request.getCommenterId());

        Optional<Board> board = Optional.of(boardRepository.findById(request.getBoardId())
            .orElseThrow(BoardNotFoundException::new));

        board.get().setComment(Comment.builder()
            .board(board.get())
            .commenter(member)
            .content(request.getContent())
            .build());

    }


    /**
     * ModelMapper 설정 최초 1회 진행
     * **/
    @PostConstruct
    void init() {
        modelMapper.addMappings(new PropertyMap<Board, InquiryRecordResponse>() {
            protected void configure() {
                map().setWriterName(source.getWriter().getUserName());
            }
        });
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

}

package com.ssafy.whoru.board.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.whoru.TestPrepare;
import com.ssafy.whoru.domain.board.domain.Board;
import com.ssafy.whoru.domain.board.domain.Comment;
import com.ssafy.whoru.domain.board.dto.request.PatchInquiryCommentRequest;
import com.ssafy.whoru.domain.board.dto.request.PatchNotificationRequest;
import com.ssafy.whoru.domain.board.dto.request.PostInquiryCommentRequest;
import com.ssafy.whoru.domain.board.dto.request.PostNotificationRequest;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.util.BoardTestUtil;
import com.ssafy.whoru.util.MemberTestUtil;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
public class AdminApiTest extends TestPrepare {

    @Test
    @Transactional
    void 관리자_문의사항_답글_성공_201() throws Exception {
        //테스트 코드 작성
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header3000 = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        Board board = boardTestUtil.문의사항_생성(mockMvc, admin);
        boardRepository.save(board);

        /**
         * 문의사항 답글 작성 API 호출
         * **/
        PostInquiryCommentRequest request = PostInquiryCommentRequest.builder()
            .commenterId(admin.getId())
            .boardId(board.getId())
            .content("문의사항에 대한 테스트 답글입니다.")
            .build();

        mockMvc.perform(
                post("/admin/board/comment")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));;

    }

    @Test
    @Transactional
    void 관리자_답글달린_문의사항_조회_성공200() throws Exception {
        //테스트 코드 작성
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header3000 = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        Board board1 = boardTestUtil.문의사항_생성(mockMvc, admin);
        Board board2 = boardTestUtil.문의사항_생성(mockMvc, admin);
        boardRepository.save(board1);
        boardRepository.save(board2);

        Comment comment = commentTestUtil.답글_생성(mockMvc, admin, board1);
        commentRepository.save(comment);

        /**
         * 사용자 문의사항 조회 API 호출
         * **/

        mockMvc.perform(
                get("/admin/board/inquiry?page=0&size=10&condition=1")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));;

    }

    @Test
    @Transactional
    void 관리자_전체_문의사항_조회_성공200() throws Exception {
        //테스트 코드 작성
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header3000 = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        Board board1 = boardTestUtil.문의사항_생성(mockMvc, admin);
        Board board2 = boardTestUtil.문의사항_생성(mockMvc, admin);
        boardRepository.save(board1);
        boardRepository.save(board2);

        Comment comment = commentTestUtil.답글_생성(mockMvc, admin, board1);
        commentRepository.save(comment);

        /**
         * 사용자 문의사항 조회 API 호출
         * **/

        mockMvc.perform(
                get("/admin/board/inquiry?page=0&size=10&condition=0")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));;

    }

    @Test
    @Transactional
    void 관리자_문의사항_답글_수정_성공_204() throws Exception {
        //테스트 코드 작성
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header3000 = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        Board board1 = boardTestUtil.문의사항_생성(mockMvc, admin);
        boardRepository.save(board1);

        Comment comment = commentTestUtil.답글_생성(mockMvc, admin, board1);
        commentRepository.save(comment);

        PatchInquiryCommentRequest request = PatchInquiryCommentRequest.builder()
            .content("수정된 테스트 답글입니다.")
            .build();

        mockMvc.perform(
                patch("/admin/board/" + comment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void 공지사항_작성_성공_201() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        PostNotificationRequest request = PostNotificationRequest.builder()
            .subject(BoardTestUtil.정상_문자열)
            .content(BoardTestUtil.정상_문자열)
            .build();

        mockMvc.perform(
            post("/admin/board/noti")
                .header(MemberTestUtil.MEMBER_HEADER_AUTH, header)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));
    }

    @Test
    void 공지사항_제목_글자수_최소제한_400() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        PostNotificationRequest request = PostNotificationRequest.builder()
            .subject(BoardTestUtil.짧은_문자열)
            .content(BoardTestUtil.정상_문자열)
            .build();

        mockMvc.perform(
                post("/admin/board/noti")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }


    @Test
    void 공지사항_제목_글자수_최대제한_400() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        PostNotificationRequest request = PostNotificationRequest.builder()
            .subject(BoardTestUtil.긴_문자열)
            .content(BoardTestUtil.정상_문자열)
            .build();

        mockMvc.perform(
                post("/admin/board/noti")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 공지사항_내용_글자수_최소제한_400() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        PostNotificationRequest request = PostNotificationRequest.builder()
            .subject(BoardTestUtil.정상_문자열)
            .content(BoardTestUtil.짧은_문자열)
            .build();

        mockMvc.perform(
                post("/admin/board/noti")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 공지사항_내용_글자_최대제한_400() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        PostNotificationRequest request = PostNotificationRequest.builder()
            .subject(BoardTestUtil.정상_문자열)
            .content(BoardTestUtil.긴_문자열)
            .build();

        mockMvc.perform(
                post("/admin/board/noti")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 권한없는_일반유저_공지사항_글쓰기접근_403() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.Member3000_멤버추가(icon);
        memberRepository.save(admin);

        String header = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        PostNotificationRequest request = PostNotificationRequest.builder()
            .subject(BoardTestUtil.정상_문자열)
            .content(BoardTestUtil.정상_문자열)
            .build();

        mockMvc.perform(
                post("/admin/board/noti")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.FORBIDDEN.value()));
    }


    @Test
    void 공지사항_글_수정_성공_204() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        Board notification = boardTestUtil.공지사항_생성(admin, "공지사항 내용", "공지사항 제목");
        boardRepository.save(notification);

        PatchNotificationRequest request = PatchNotificationRequest.builder()
            .subject("공지사항 제목 수정")
            .content("공지사항 내용 수정")
            .build();

        StringBuilder sb = new StringBuilder();
        sb.append("/admin/board/noti/").append(notification.getId());

        mockMvc.perform(
            patch(sb.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header(MemberTestUtil.MEMBER_HEADER_AUTH, header)
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void 공지사항_없는_글_수정시도_404() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        Board notification = boardTestUtil.공지사항_생성(admin, "공지사항 내용", "공지사항 제목");
        boardRepository.save(notification);

        PatchNotificationRequest request = PatchNotificationRequest.builder()
            .content("공지사항 내용 수정")
            .build();

        StringBuilder sb = new StringBuilder();
        sb.append("/admin/board/noti/").append(9999);

        mockMvc.perform(
                patch(sb.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header)
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void 공지사항_고유번호_음수값_수정시도_400() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        String header = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);

        Board notification = boardTestUtil.공지사항_생성(admin, "공지사항 내용", "공지사항 제목");
        boardRepository.save(notification);

        PatchNotificationRequest request = PatchNotificationRequest.builder()
            .content("공지사항 내용 수정")
            .build();

        StringBuilder sb = new StringBuilder();
        sb.append("/admin/board/noti/").append(-1);

        mockMvc.perform(
                patch(sb.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header)
            )
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void 공지사항_글_수정_권한없음_403() throws Exception{
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member admin = memberTestUtil.관리자_멤버_추가(icon);
        memberRepository.save(admin);

        Member member3000 = memberTestUtil.Member3000_멤버추가(icon);

        String header = memberTestUtil.관리자_AccessToken_만들고_헤더값_리턴(admin);
        String member3000Header = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        memberTestUtil.멤버_보유_아이콘_추가(admin, icon);
        memberTestUtil.멤버_보유_아이콘_추가(member3000, icon);

        Board notification = boardTestUtil.공지사항_생성(admin, "공지사항 내용", "공지사항 제목");
        boardRepository.save(notification);

        PatchNotificationRequest request = PatchNotificationRequest.builder()
            .content("공지사항 내용 수정")
            .build();

        StringBuilder sb = new StringBuilder();
        sb.append("/admin/board/noti/").append(notification.getId());

        mockMvc.perform(
                patch(sb.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, member3000Header)
            )
            .andExpect(status().is5xxServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.errorCode").value(HttpStatus.FORBIDDEN.value()));
    }


}

package com.ssafy.whoru.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.whoru.TestPrepare;
import com.ssafy.whoru.domain.board.domain.Board;
import com.ssafy.whoru.domain.board.dto.request.PostInquiryBoardRequest;
import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.member.domain.Member;
import com.ssafy.whoru.domain.message.domain.Message;
import com.ssafy.whoru.domain.report.dto.request.PostReportRequest;
import com.ssafy.whoru.util.BoardTestUtil;
import com.ssafy.whoru.util.MemberTestUtil;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
public class BoardApiTest extends TestPrepare {

    @Test
    @Transactional
    void 문의사항_작성_성공_201() throws Exception {
        //테스트 코드 작성
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member member3000 = memberTestUtil.Member3000_멤버추가(icon);
        memberRepository.save(member3000);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        memberTestUtil.멤버_보유_아이콘_추가(member3000, icon);

        /**
         * 문의사항 작성 API 호출
         * **/
        PostInquiryBoardRequest request = PostInquiryBoardRequest.builder()
            .subject("테스트")
            .content("테스트 문의사항입니다.")
            .memberId(member3000.getId())
            .build();

        mockMvc.perform(
                post("/board/inquiry")
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
    void 사용자_작성_문의사항_조회_성공200() throws Exception {
        //테스트 코드 작성
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member member3000 = memberTestUtil.Member3000_멤버추가(icon);
        memberRepository.save(member3000);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        memberTestUtil.멤버_보유_아이콘_추가(member3000, icon);

        Board board = boardTestUtil.문의사항_생성(mockMvc, member3000);
        boardRepository.save(board);

        /**
         * 사용자 문의사항 조회 API 호출
         * **/

        mockMvc.perform(
                get("/board?page=0&size=10")
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));;

    }

    @Test
    @Transactional
    void 사용자_작성_문의사항_삭제_성공204() throws Exception {
        //테스트 코드 작성
        Icon icon = memberTestUtil.아이콘_추가();
        collectRepository.save(icon);

        Member member3000 = memberTestUtil.Member3000_멤버추가(icon);
        memberRepository.save(member3000);

        String header3000 = memberTestUtil.유저_AccessToken_만들고_헤더값_리턴(member3000);

        memberTestUtil.멤버_보유_아이콘_추가(member3000, icon);

        Board board = boardTestUtil.문의사항_생성(mockMvc, member3000);
        boardRepository.save(board);

        /**
         * 사용자 문의사항 조회 API 호출
         * **/

        mockMvc.perform(
                delete("/board/" + member3000.getId())
                    .header(MemberTestUtil.MEMBER_HEADER_AUTH, header3000)
            )
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.NO_CONTENT.value()));;

    }

}

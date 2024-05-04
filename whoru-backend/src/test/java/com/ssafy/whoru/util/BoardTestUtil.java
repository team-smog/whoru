package com.ssafy.whoru.util;

import com.ssafy.whoru.domain.board.dao.BoardRepository;
import com.ssafy.whoru.domain.board.domain.Board;
import com.ssafy.whoru.domain.board.dto.BoardType;
import com.ssafy.whoru.domain.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

@Component
public class BoardTestUtil {

    @Autowired
    BoardRepository boardRepository;

    public Board 문의사항_생성(MockMvc mockMvc, Member member) {

        return Board.builder()
            .writer(member)
            .boardType(BoardType.INQUIRY)
            .content("테스트 문의사항")
            .subject("테스트")
            .build();
    }

}

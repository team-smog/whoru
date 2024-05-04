package com.ssafy.whoru.util;

import com.ssafy.whoru.domain.board.dao.CommentRepository;
import com.ssafy.whoru.domain.board.domain.Board;
import com.ssafy.whoru.domain.board.domain.Comment;
import com.ssafy.whoru.domain.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

@Component
public class CommentTestUtil {

    @Autowired
    CommentRepository commentRepository;

    public Comment 답글_생성(MockMvc mockMvc, Member member, Board board) {

        return Comment.builder()
            .commenter(member)
            .board(board)
            .content("테스트 답글입니다.")
            .build();
    }
}

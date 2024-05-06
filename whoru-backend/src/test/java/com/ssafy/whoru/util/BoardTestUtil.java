package com.ssafy.whoru.util;

import com.ssafy.whoru.domain.board.dao.BoardRepository;
import com.ssafy.whoru.domain.board.domain.Board;
import com.ssafy.whoru.domain.board.dto.BoardType;
import com.ssafy.whoru.domain.board.dto.request.PostNotificationRequest;
import com.ssafy.whoru.domain.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

@Component
public class BoardTestUtil {

    @Autowired
    BoardRepository boardRepository;

    public static final String 정상_문자열 = "abcd";

    public static final String 짧은_문자열 = "아";

    public static final String 긴_문자열 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod leo ac quam aliquam semper. Nulla facilisi. Sed ac quam ac quam tincidunt ullamcorper. Nullam eget quam quis elit tincidunt tincidunt. Vivamus non enim eget orci tincidunt condimentum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec vel magna eget dui gravida congue. Sed ac felis sit amet ligula semper lacinia. Proin vitae mi eget quam ullamcorper semper. Sed ac quam ac quam tincidunt ullamcorper. Nullam eget quam quis elit tincidunt tincidunt. Vivamus non enim eget orci tincidunt condimentum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec vel magna eget dui gravida congue. Sed ac felis sit amet ligula semper lacinia. Proin vitae mi eget quam ullamcorper semper. Sed ac quam ac quam tincidunt ullamcorper. Nullam eget quam quis elit tincidunt tincidunt. Vivamus non enim eget orci tincidunt condimentum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec vel magna eget dui gravida congue. Sed ac felis sit amet ligula semper lacinia. Proin vitae mi eget quam ullamcorper semper. Sed ac quam ac quam tincidunt ullamcorper. Nullam eget quam quis elit tincidunt tincidunt. Vivamus non enim eget orci tincidunt condimentum. ";

    public Board 문의사항_생성(MockMvc mockMvc, Member member) {

        return Board.builder()
            .writer(member)
            .boardType(BoardType.INQUIRY)
            .content("테스트 문의사항")
            .subject("테스트")
            .build();
    }

    public PostNotificationRequest 공지사항_생성(String content, String subject){
        return PostNotificationRequest.builder()
            .content(content)
            .subject(subject)
            .build();
    }
}

package com.ssafy.whoru.domain.board.dao;

import com.ssafy.whoru.domain.board.domain.Board;
import com.ssafy.whoru.domain.member.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.writer = :member")
    public Slice<Board> findByMember(Member member, Pageable pageable);
}

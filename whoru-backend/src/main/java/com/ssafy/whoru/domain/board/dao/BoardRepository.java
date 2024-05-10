package com.ssafy.whoru.domain.board.dao;

import com.ssafy.whoru.domain.board.domain.Board;
import com.ssafy.whoru.domain.board.dto.BoardType;
import com.ssafy.whoru.domain.member.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE b.writer = :member AND b.boardType = :type")
    public Slice<Board> findByMember(Member member, Pageable pageable, BoardType type);

    @Query("SELECT b FROM Board b WHERE b.boardType = :type")
    public Slice<Board> findAllByType(Pageable pageable, BoardType type);

    @Query("SELECT b FROM Board b LEFT JOIN b.comment c WHERE c IS NULL AND b.boardType = :type")
    public Slice<Board> findAllByCommentAndType(Pageable pageable, BoardType type);
}

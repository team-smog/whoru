package com.ssafy.whoru.domain.board.dao;

import com.ssafy.whoru.domain.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}

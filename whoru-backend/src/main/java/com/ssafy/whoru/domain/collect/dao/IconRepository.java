package com.ssafy.whoru.domain.collect.dao;

import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.collect.dto.IconGradeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IconRepository extends JpaRepository<Icon, Integer> {

    @Query("SELECT i FROM Icon i WHERE i.iconGrade = :grade ORDER BY RAND() LIMIT 1")
    Optional<Icon> findByRandomIcon(@Param("grade")IconGradeType grade);

    @Query("SELECT i FROM Icon i ORDER BY i.iconGrade DESC")
    List<Icon> findAllIconOrderByGrade();
}

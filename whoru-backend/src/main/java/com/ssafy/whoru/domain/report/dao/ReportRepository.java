package com.ssafy.whoru.domain.report.dao;

import com.ssafy.whoru.domain.report.domain.Report;
import com.ssafy.whoru.domain.report.dto.ReportType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r FROM Report r WHERE r.member.reportCount > 0 AND r.isReviewed = false")
    Slice<Report> findByDefaultCondition(Pageable pageable);

    @Query("SELECT r FROM Report r WHERE r.member.reportCount > 0 AND r.isReviewed = false AND r.reportType = :reportType")
    Slice<Report> findByCondition(ReportType reportType, Pageable pageable);
}

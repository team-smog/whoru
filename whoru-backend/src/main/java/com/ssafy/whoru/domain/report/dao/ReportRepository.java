package com.ssafy.whoru.domain.report.dao;

import com.ssafy.whoru.domain.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

}

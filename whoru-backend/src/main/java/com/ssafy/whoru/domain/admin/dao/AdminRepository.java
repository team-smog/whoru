package com.ssafy.whoru.domain.admin.dao;

import com.ssafy.whoru.domain.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Admin findByUserId(String username);
}

package com.ssafy.whoru.domain.admin.dao;

import com.google.common.base.Optional;
import com.ssafy.whoru.domain.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUserId(String username);
}

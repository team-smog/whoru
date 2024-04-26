package com.ssafy.whoru.domain.member.dao;

import com.ssafy.whoru.domain.member.domain.FcmNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmRepository extends JpaRepository<FcmNotification, Integer> {

}

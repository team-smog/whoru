package com.ssafy.whoru.domain.collect.dao;

import com.ssafy.whoru.domain.collect.domain.Icon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectRepository extends JpaRepository<Icon, Integer> {

}

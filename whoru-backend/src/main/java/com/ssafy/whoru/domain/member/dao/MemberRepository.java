package com.ssafy.whoru.domain.member.dao;

import com.ssafy.whoru.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query(value = "SELECT m FROM Member m WHERE m.id != :senderId ORDER BY RAND() LIMIT 1 ")
    Optional<Member> findRandom(Long senderId);

    Optional<Member> findByUserName(String userName);
}

package com.ssafy.whoru.domain.member.dao;

import com.ssafy.whoru.domain.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query(value = "SELECT m FROM Member m WHERE m.id != :senderId and m.role = 'ROLE_USER' ORDER BY RAND() LIMIT 1 ")
    Optional<Member> findRandom(Long senderId);

    Optional<Member> findByUserName(String userName);

    Optional<Member> findByMemberIdentifier(String memberIdentifier);

    @Query(value = "SELECT m From Member m WHERE m.role = 'ROLE_USER'")
    Optional<List<Member>> findAllByRoleUser();
}

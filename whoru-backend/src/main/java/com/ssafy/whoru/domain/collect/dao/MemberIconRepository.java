package com.ssafy.whoru.domain.collect.dao;

import com.ssafy.whoru.domain.collect.domain.Icon;
import com.ssafy.whoru.domain.collect.domain.MemberIcon;
import com.ssafy.whoru.domain.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberIconRepository extends JpaRepository<MemberIcon, Long> {

    @Query("SELECT mi FROM MemberIcon mi WHERE mi.member = :member AND mi.icon = :icon")
    Optional<MemberIcon> findByMemberANDIcon(@Param("member") Member member, @Param("icon") Icon icon);

    @Query("SELECT mi FROM MemberIcon mi WHERE mi.member = :member")
    List<MemberIcon> findByMember(@Param("member") Member member);
}

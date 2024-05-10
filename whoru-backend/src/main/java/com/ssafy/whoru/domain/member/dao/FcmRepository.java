package com.ssafy.whoru.domain.member.dao;

import com.ssafy.whoru.domain.member.domain.FcmNotification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FcmRepository extends JpaRepository<FcmNotification, Long> {
    void deleteById(Integer FcmId);

    @Query("SELECT f FROM fcm_notification f where f.member.id = :memberId AND f.fcmToken = :fcmToken")
    Optional<FcmNotification> findFcmNotificationByMemberAndFcmToken(Long memberId, String fcmToken);

    @Query("SELECT f FROM fcm_notification f where f.mark = true")
    List<FcmNotification> findAllMarked();
}

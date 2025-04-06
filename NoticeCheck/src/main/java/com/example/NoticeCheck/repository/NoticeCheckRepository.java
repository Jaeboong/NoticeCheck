package com.example.NoticeCheck.repository;

import com.example.NoticeCheck.entity.NoticeCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NoticeCheckRepository extends JpaRepository<NoticeCheck, Long> {
    Optional<NoticeCheck> findByNoticeId(String noticeId);
}
package com.career.assessment.repository;

import com.career.assessment.entity.MbtiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MbtiResultRepository extends JpaRepository<MbtiResult, Long> {
    Optional<MbtiResult> findTopBySessionIdOrderByCreatedAtDesc(Long sessionId);
    Optional<MbtiResult> findTopByUserIdOrderByCreatedAtDesc(Long userId);
    List<MbtiResult> findAllByOrderByCreatedAtDesc();
    List<MbtiResult> findByUserIdOrderByCreatedAtDesc(Long userId);
}

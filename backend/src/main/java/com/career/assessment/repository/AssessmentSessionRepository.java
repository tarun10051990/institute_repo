package com.career.assessment.repository;

import com.career.assessment.entity.AssessmentSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentSessionRepository extends JpaRepository<AssessmentSession, Long> {
    Optional<AssessmentSession> findBySessionCode(String sessionCode);
    List<AssessmentSession> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<AssessmentSession> findByUserIdAndStatus(Long userId, AssessmentSession.SessionStatus status);
}

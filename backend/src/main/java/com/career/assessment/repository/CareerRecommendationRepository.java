package com.career.assessment.repository;

import com.career.assessment.entity.CareerRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CareerRecommendationRepository extends JpaRepository<CareerRecommendation, Long> {
    List<CareerRecommendation> findBySessionIdOrderByRankOrderAsc(Long sessionId);
    List<CareerRecommendation> findByUserIdOrderByCreatedAtDesc(Long userId);
}

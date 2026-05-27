package com.career.assessment.repository;

import com.career.assessment.entity.CategoryScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryScoreRepository extends JpaRepository<CategoryScore, Long> {
    List<CategoryScore> findBySessionId(Long sessionId);
    List<CategoryScore> findBySessionIdOrderByCategoryDisplayOrderAsc(Long sessionId);
}

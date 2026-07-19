package com.career.assessment.repository;

import com.career.assessment.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategoryIdAndIsActiveTrueOrderByDisplayOrderAsc(Long categoryId);
    long countByCategoryIdAndIsActiveTrue(Long categoryId);
}

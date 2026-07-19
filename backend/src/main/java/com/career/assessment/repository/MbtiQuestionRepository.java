package com.career.assessment.repository;

import com.career.assessment.entity.MbtiQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MbtiQuestionRepository extends JpaRepository<MbtiQuestion, Long> {
    List<MbtiQuestion> findAllByOrderByDisplayOrderAsc();
    List<MbtiQuestion> findByActiveTrueOrderByDisplayOrderAsc();
    long count();
}

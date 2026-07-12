package com.career.assessment.repository;

import com.career.assessment.entity.MbtiOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbtiOptionRepository extends JpaRepository<MbtiOption, Long> {
}

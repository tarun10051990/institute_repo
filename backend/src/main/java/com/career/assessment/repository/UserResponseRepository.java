package com.career.assessment.repository;

import com.career.assessment.entity.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserResponseRepository extends JpaRepository<UserResponse, Long> {
    List<UserResponse> findBySessionId(Long sessionId);
    Optional<UserResponse> findBySessionIdAndQuestionId(Long sessionId, Long questionId);

    @Query("SELECT ur FROM UserResponse ur JOIN ur.question q WHERE ur.session.id = :sessionId AND q.category.id = :categoryId")
    List<UserResponse> findBySessionIdAndCategoryId(@Param("sessionId") Long sessionId, @Param("categoryId") Long categoryId);

    long countBySessionId(Long sessionId);
}

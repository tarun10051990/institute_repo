package com.career.assessment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mbti_results")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MbtiResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private AssessmentSession session;

    @Column(name = "type_code", nullable = false, length = 4)
    private String typeCode;

    @Column(length = 100)
    private String nickname;

    /** Full MbtiResultDTO serialized as JSON so the report can be rebuilt as answered. */
    @Column(name = "dimensions_json", columnDefinition = "TEXT")
    private String dimensionsJson;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

package com.career.assessment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "career_recommendations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CareerRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private AssessmentSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "career_title", nullable = false, length = 255)
    private String careerTitle;

    @Column(name = "career_description", columnDefinition = "TEXT")
    private String careerDescription;

    @Column(name = "match_percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal matchPercentage;

    @Column(name = "career_field", length = 100)
    private String careerField;

    @Column(name = "required_education", columnDefinition = "TEXT")
    private String requiredEducation;

    @Column(name = "salary_range", length = 100)
    private String salaryRange;

    @Column(name = "growth_outlook", length = 50)
    private String growthOutlook;

    @Column(name = "rank_order")
    private Integer rankOrder;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

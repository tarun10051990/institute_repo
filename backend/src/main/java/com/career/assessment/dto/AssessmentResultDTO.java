package com.career.assessment.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssessmentResultDTO {
    private Long sessionId;
    private String sessionCode;
    private String userName;
    private String email;
    private LocalDateTime completedAt;
    private List<CategoryScoreDTO> categoryScores;
    private List<CareerRecommendationDTO> careerRecommendations;
    private String overallSummary;
}

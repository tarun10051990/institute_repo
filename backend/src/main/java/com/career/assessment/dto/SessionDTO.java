package com.career.assessment.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SessionDTO {
    private Long id;
    private String sessionCode;
    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private Long totalQuestions;
    private Long answeredQuestions;
}

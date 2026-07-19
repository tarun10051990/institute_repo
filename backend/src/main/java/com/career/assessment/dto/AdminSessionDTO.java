package com.career.assessment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AdminSessionDTO {
    private Long sessionId;
    private String sessionCode;
    private String status;
    private Long userId;
    private String userName;
    private String email;
    private String mbtiType;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
}

package com.career.assessment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AdminUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String city;
    private String educationLevel;
    private LocalDateTime createdAt;
    private long sessionCount;
    private long completedCount;
}

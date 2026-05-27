package com.career.assessment.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private Long userId;
}

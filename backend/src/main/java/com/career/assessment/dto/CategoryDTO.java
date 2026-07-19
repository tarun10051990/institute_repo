package com.career.assessment.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private String icon;
    private Integer displayOrder;
    private Integer timeLimitMinutes;
    private Integer totalQuestions;
    private Long questionCount;
}

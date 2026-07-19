package com.career.assessment.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AnswerOptionDTO {
    private Long id;
    private String optionText;
    private String optionLabel;
    private Integer displayOrder;
}

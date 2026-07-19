package com.career.assessment.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TraitScoreDTO {
    private String code;
    private String name;
    private int percentage;
}

package com.career.assessment.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuestionAdminDTO {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String questionText;
    private String questionType;
    private String difficultyLevel;
    private Integer displayOrder;
    private Boolean active;
    @Builder.Default
    private List<AnswerOptionAdminDTO> options = new ArrayList<>();

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AnswerOptionAdminDTO {
        private Long id;
        private String optionText;
        private String optionLabel;
        private Integer scoreValue;
        private String traitCode;
        private Integer displayOrder;
    }
}

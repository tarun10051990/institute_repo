package com.career.assessment.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class QuestionDTO {
    private Long id;
    private String questionText;
    private String questionType;
    private String difficultyLevel;
    private Integer displayOrder;
    private Long categoryId;
    private String categoryName;
    private List<AnswerOptionDTO> options;
}

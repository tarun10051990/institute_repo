package com.career.assessment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SubmitAnswerRequest {
    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotNull(message = "Selected option ID is required")
    private Long selectedOptionId;
}

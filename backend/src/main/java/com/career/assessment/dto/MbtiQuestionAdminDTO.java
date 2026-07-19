package com.career.assessment.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MbtiQuestionAdminDTO {
    private Long id;
    private String dimension;
    private String questionText;
    private Integer displayOrder;
    private Boolean active;
    @Builder.Default
    private List<MbtiOptionAdminDTO> options = new ArrayList<>();

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MbtiOptionAdminDTO {
        private Long id;
        private String label;
        private String text;
        private String letter;
        private Integer displayOrder;
    }
}

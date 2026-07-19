package com.career.assessment.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryScoreDTO {
    private Long categoryId;
    private String categoryName;
    private String categoryCode;
    private String icon;
    private Integer rawScore;
    private Integer maxScore;
    private BigDecimal percentage;
    private String traitSummary;
}

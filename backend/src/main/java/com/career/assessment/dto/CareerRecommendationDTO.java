package com.career.assessment.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CareerRecommendationDTO {
    private Long id;
    private String careerTitle;
    private String careerDescription;
    private BigDecimal matchPercentage;
    private String careerField;
    private String requiredEducation;
    private String salaryRange;
    private String growthOutlook;
    private Integer rankOrder;
}

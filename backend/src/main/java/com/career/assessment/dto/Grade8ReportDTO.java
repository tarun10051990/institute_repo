package com.career.assessment.dto;

import lombok.*;
import java.util.List;

/**
 * The expanded, Grade-8 oriented psychometric report. Bundles the derived
 * sections that sit on top of the raw category scores and MBTI result so the
 * results page and PDF can render a student- and parent-friendly report.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Grade8ReportDTO {

    private String studentProfile;

    private List<TraitScoreDTO> multipleIntelligence;
    private String multipleIntelligenceSummary;

    private List<TraitScoreDTO> learningStyle;
    private String learningStyleName;
    private String learningStyleSummary;

    private List<String> topStrengths;
    private List<String> developmentAreas;
    private List<String> recommendedSubjects;
    private List<String> competitions;
    private List<String> skillDevelopmentPlan;
    private List<String> careerClusters;
    private List<String> parentGuidance;
    private List<String> counsellorRecommendations;

    private List<ActionPlanPhaseDTO> actionPlan;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ActionPlanPhaseDTO {
        private String period;
        private List<String> items;
    }
}

package com.career.assessment.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MbtiProfileDTO {
    private Long id;
    private String typeCode;
    private String nickname;
    private String summary;
    private String overview;
    @Builder.Default
    private List<String> strengths = new ArrayList<>();
    @Builder.Default
    private List<String> weaknesses = new ArrayList<>();
    @Builder.Default
    private List<String> careers = new ArrayList<>();
    private String relationships;
    private String growthTips;
}

package com.career.assessment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mbti_type_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MbtiTypeProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_code", nullable = false, unique = true, length = 4)
    private String typeCode;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String overview;

    /** Newline-separated list. */
    @Column(columnDefinition = "TEXT")
    private String strengths;

    /** Newline-separated list. */
    @Column(columnDefinition = "TEXT")
    private String weaknesses;

    /** Newline-separated list. */
    @Column(columnDefinition = "TEXT")
    private String careers;

    @Column(columnDefinition = "TEXT")
    private String relationships;

    @Column(name = "growth_tips", columnDefinition = "TEXT")
    private String growthTips;
}

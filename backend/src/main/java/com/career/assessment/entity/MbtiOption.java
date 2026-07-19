package com.career.assessment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mbti_options")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MbtiOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private MbtiQuestion question;

    @Column(name = "option_label", nullable = false, length = 1)
    private String label;

    @Column(name = "option_text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false, length = 1)
    private String letter;

    @Column(name = "display_order", nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;
}

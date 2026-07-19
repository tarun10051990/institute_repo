package com.career.assessment.service;

import java.util.List;

/**
 * Seed content for the two Grade-8 assessment sections that the original
 * platform did not measure: Multiple Intelligence (Gardner's 8 intelligences)
 * and Learning Style (VARK). Both use Likert-scale questions where every option
 * of a question shares the question's trait code, so a per-trait breakdown can
 * be computed from the summed score values.
 */
public final class Grade8SeedData {

    private Grade8SeedData() {
    }

    public record SeedTrait(String code, String name) {
    }

    public record SeedQuestion(String traitCode, String text, int order) {
    }

    /** Standard 5-point Likert options, highest agreement first. */
    public static List<int[]> likertScores() {
        // score value per label A..E
        return List.of(new int[]{5, 4, 3, 2, 1});
    }

    public static String[] likertLabels() {
        return new String[]{"A", "B", "C", "D", "E"};
    }

    public static String[] likertTexts() {
        return new String[]{
                "Strongly agree",
                "Agree",
                "Neutral",
                "Disagree",
                "Strongly disagree"
        };
    }

    // ---- Multiple Intelligence (Gardner) ----

    public static List<SeedTrait> multipleIntelligenceTraits() {
        return List.of(
                new SeedTrait("LINGUISTIC", "Linguistic (Word Smart)"),
                new SeedTrait("LOGICAL", "Logical-Mathematical (Reasoning Smart)"),
                new SeedTrait("SPATIAL", "Spatial (Picture Smart)"),
                new SeedTrait("KINESTHETIC", "Bodily-Kinesthetic (Body Smart)"),
                new SeedTrait("MUSICAL", "Musical (Music Smart)"),
                new SeedTrait("INTERPERSONAL", "Interpersonal (People Smart)"),
                new SeedTrait("INTRAPERSONAL", "Intrapersonal (Self Smart)"),
                new SeedTrait("NATURALISTIC", "Naturalistic (Nature Smart)")
        );
    }

    public static List<SeedQuestion> multipleIntelligenceQuestions() {
        return List.of(
                new SeedQuestion("LINGUISTIC", "I enjoy reading books, writing stories, or playing with words.", 1),
                new SeedQuestion("LINGUISTIC", "I can explain my ideas clearly in speech or writing.", 2),
                new SeedQuestion("LOGICAL", "I like solving puzzles, number problems, and figuring out how things work.", 3),
                new SeedQuestion("LOGICAL", "I look for patterns, reasons, and logical explanations.", 4),
                new SeedQuestion("SPATIAL", "I think in pictures and can imagine how things look from different angles.", 5),
                new SeedQuestion("SPATIAL", "I enjoy drawing, designing, maps, or building models.", 6),
                new SeedQuestion("KINESTHETIC", "I learn best by doing things with my hands or moving around.", 7),
                new SeedQuestion("KINESTHETIC", "I am good at sports, dance, or activities that need coordination.", 8),
                new SeedQuestion("MUSICAL", "I notice rhythms, tunes, and sounds easily.", 9),
                new SeedQuestion("MUSICAL", "I enjoy singing, playing an instrument, or listening to music.", 10),
                new SeedQuestion("INTERPERSONAL", "I understand how others feel and enjoy working in groups.", 11),
                new SeedQuestion("INTERPERSONAL", "Friends often come to me for help or advice.", 12),
                new SeedQuestion("INTRAPERSONAL", "I understand my own feelings, strengths, and goals well.", 13),
                new SeedQuestion("INTRAPERSONAL", "I like to reflect and prefer to work on things independently.", 14),
                new SeedQuestion("NATURALISTIC", "I am curious about plants, animals, and nature.", 15),
                new SeedQuestion("NATURALISTIC", "I enjoy being outdoors and noticing changes in the environment.", 16)
        );
    }

    // ---- Learning Style (VARK) ----

    public static List<SeedTrait> learningStyleTraits() {
        return List.of(
                new SeedTrait("VISUAL", "Visual"),
                new SeedTrait("AUDITORY", "Auditory"),
                new SeedTrait("READWRITE", "Reading/Writing"),
                new SeedTrait("KINESTHETIC_LS", "Kinesthetic")
        );
    }

    public static List<SeedQuestion> learningStyleQuestions() {
        return List.of(
                new SeedQuestion("VISUAL", "I understand new topics best when I see diagrams, charts, or videos.", 1),
                new SeedQuestion("VISUAL", "I remember information better when it is shown with pictures or colours.", 2),
                new SeedQuestion("AUDITORY", "I learn best by listening to explanations or discussing topics aloud.", 3),
                new SeedQuestion("AUDITORY", "I remember things I hear, like lectures or audio, more easily.", 4),
                new SeedQuestion("READWRITE", "I learn best by reading notes and writing things down in my own words.", 5),
                new SeedQuestion("READWRITE", "Making lists and re-reading text helps me remember.", 6),
                new SeedQuestion("KINESTHETIC_LS", "I learn best by doing experiments, activities, or hands-on practice.", 7),
                new SeedQuestion("KINESTHETIC_LS", "I understand ideas better when I can try them out physically.", 8)
        );
    }
}

package com.career.assessment.service;

import com.career.assessment.dto.CareerRecommendationDTO;
import com.career.assessment.dto.CategoryScoreDTO;
import com.career.assessment.dto.Grade8ReportDTO;
import com.career.assessment.dto.MbtiResultDTO;
import com.career.assessment.dto.TraitScoreDTO;
import com.career.assessment.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds the expanded Grade-8 psychometric report by combining the raw category
 * scores, the Multiple Intelligence / Learning Style breakdowns, the MBTI result
 * and the career recommendations into a set of student- and parent-friendly
 * sections. All derived content is age-appropriate and framed as guidance rather
 * than a fixed verdict.
 */
@Service
public class Grade8ReportService {

    public Grade8ReportDTO build(User user,
                                 List<CategoryScoreDTO> scores,
                                 List<CareerRecommendationDTO> careers,
                                 MbtiResultDTO mbti,
                                 Map<String, Integer> miBreakdown,
                                 Map<String, Integer> lsBreakdown) {

        List<TraitScoreDTO> miScores = toTraitScores(miBreakdown, miNames());
        List<TraitScoreDTO> lsScores = toTraitScores(lsBreakdown, lsNames());

        String dominantMi = topCode(miBreakdown);
        String secondMi = secondCode(miBreakdown);
        String dominantLs = topCode(lsBreakdown);

        int aptitude = pct(scores, "APTITUDE");
        int eq = pct(scores, "EQ");
        int interest = pct(scores, "INTEREST");

        return Grade8ReportDTO.builder()
                .studentProfile(buildStudentProfile(user, mbti, dominantMi, dominantLs))
                .multipleIntelligence(miScores)
                .multipleIntelligenceSummary(buildMiSummary(dominantMi, secondMi))
                .learningStyle(lsScores)
                .learningStyleName(lsNames().getOrDefault(dominantLs, "Balanced"))
                .learningStyleSummary(buildLsSummary(dominantLs))
                .topStrengths(buildStrengths(scores, dominantMi, secondMi, mbti))
                .developmentAreas(buildDevelopmentAreas(scores, mbti))
                .recommendedSubjects(subjectsFor(dominantMi, secondMi))
                .competitions(competitionsFor(dominantMi))
                .skillDevelopmentPlan(skillPlanFor(dominantLs, dominantMi, eq))
                .careerClusters(careerClusters(careers))
                .parentGuidance(parentGuidance(dominantLs, eq))
                .counsellorRecommendations(counsellorRecommendations(aptitude, interest, eq))
                .actionPlan(actionPlan())
                .build();
    }

    // ---- profile & summaries ----

    private String buildStudentProfile(User user, MbtiResultDTO mbti, String dominantMi, String dominantLs) {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getFirstName()).append(" ").append(user.getLastName());
        if (user.getSchoolName() != null && !user.getSchoolName().isBlank()) {
            sb.append(" from ").append(user.getSchoolName());
        }
        if (user.getCity() != null && !user.getCity().isBlank()) {
            sb.append(", ").append(user.getCity());
        }
        sb.append(" is a curious, developing learner. ");
        if (mbti != null && mbti.getType() != null) {
            sb.append("Their personality leans towards ").append(mbti.getType());
            if (mbti.getNickname() != null) {
                sb.append(" (").append(mbti.getNickname()).append(")");
            }
            sb.append(". ");
        }
        sb.append("They learn most comfortably through a ")
                .append(lsNames().getOrDefault(dominantLs, "balanced").toLowerCase())
                .append(" style and show a natural strength in ")
                .append(miNames().getOrDefault(dominantMi, "several areas"))
                .append(". This report is a snapshot to guide exploration \u2014 not a fixed label.");
        return sb.toString();
    }

    private String buildMiSummary(String dominant, String second) {
        String d = miNames().getOrDefault(dominant, "multiple areas");
        String s = miNames().getOrDefault(second, "other areas");
        return "Your strongest intelligences appear to be " + d + " and " + s
                + ". Everyone has all eight intelligences in different mixes; these are simply where you seem "
                + "most naturally engaged right now and can grow further with practice.";
    }

    private String buildLsSummary(String dominant) {
        return switch (dominant) {
            case "VISUAL" -> "You learn best when information is shown visually. Use mind-maps, diagrams, colour-coded "
                    + "notes, flowcharts and educational videos.";
            case "AUDITORY" -> "You learn best by listening and talking. Discuss topics aloud, record and replay "
                    + "explanations, and study with a partner.";
            case "READWRITE" -> "You learn best by reading and writing. Rewrite notes in your own words, make lists "
                    + "and summaries, and read widely.";
            case "KINESTHETIC_LS" -> "You learn best by doing. Use experiments, models, role-play and real-life "
                    + "examples, and take short active breaks while studying.";
            default -> "You learn well through a mix of channels. Combine visuals, discussion, writing and hands-on "
                    + "practice to suit each subject.";
        };
    }

    // ---- strengths & development ----

    private List<String> buildStrengths(List<CategoryScoreDTO> scores, String dominantMi, String secondMi, MbtiResultDTO mbti) {
        List<String> out = new ArrayList<>();
        out.add(miNames().getOrDefault(dominantMi, "Strong all-round ability"));
        out.add(miNames().getOrDefault(secondMi, "Balanced skills across areas"));
        scores.stream()
                .filter(s -> s.getPercentage() != null && s.getPercentage().doubleValue() >= 70)
                .filter(s -> !s.getCategoryCode().equals("MI") && !s.getCategoryCode().equals("LEARNING_STYLE"))
                .limit(2)
                .forEach(s -> out.add("Strong " + s.getCategoryName() + " (" + s.getPercentage() + "%)"));
        if (mbti != null && mbti.getStrengths() != null) {
            mbti.getStrengths().stream().limit(2).forEach(out::add);
        }
        return out;
    }

    private List<String> buildDevelopmentAreas(List<CategoryScoreDTO> scores, MbtiResultDTO mbti) {
        List<String> out = new ArrayList<>();
        scores.stream()
                .filter(s -> s.getPercentage() != null && s.getPercentage().doubleValue() < 60)
                .filter(s -> !s.getCategoryCode().equals("MI") && !s.getCategoryCode().equals("LEARNING_STYLE"))
                .limit(2)
                .forEach(s -> out.add("Build up " + s.getCategoryName() + " (currently " + s.getPercentage() + "%)"));
        if (mbti != null && mbti.getWeaknesses() != null) {
            mbti.getWeaknesses().stream().limit(2).forEach(out::add);
        }
        if (out.isEmpty()) {
            out.add("Keep challenging yourself with harder problems to stretch your strengths further.");
            out.add("Practise time management and consistent study habits.");
        }
        return out;
    }

    // ---- subjects / competitions / skills ----

    private List<String> subjectsFor(String dominant, String second) {
        List<String> out = new ArrayList<>(subjectMap().getOrDefault(dominant, defaultSubjects()));
        for (String s : subjectMap().getOrDefault(second, List.of())) {
            if (!out.contains(s)) {
                out.add(s);
            }
        }
        return out;
    }

    private Map<String, List<String>> subjectMap() {
        Map<String, List<String>> m = new LinkedHashMap<>();
        m.put("LINGUISTIC", List.of("English & Languages", "Literature", "History & Political Science", "Debate/Journalism electives"));
        m.put("LOGICAL", List.of("Mathematics", "Physics", "Computer Science", "Economics"));
        m.put("SPATIAL", List.of("Mathematics (Geometry)", "Art & Design", "Geography", "Computer Science"));
        m.put("KINESTHETIC", List.of("Physical Education", "Biology (lab work)", "Applied/Vocational subjects", "Performing Arts"));
        m.put("MUSICAL", List.of("Music", "Languages", "Mathematics (patterns)"));
        m.put("INTERPERSONAL", List.of("Social Science", "Business Studies", "Psychology", "Languages"));
        m.put("INTRAPERSONAL", List.of("Humanities", "Literature", "Computer Science"));
        m.put("NATURALISTIC", List.of("Biology", "Environmental Science", "Geography", "Chemistry"));
        return m;
    }

    private List<String> defaultSubjects() {
        return List.of("Mathematics", "Science", "English", "Social Science");
    }

    private List<String> competitionsFor(String dominant) {
        List<String> out = new ArrayList<>(competitionMap().getOrDefault(dominant, new ArrayList<>()));
        for (String s : List.of("Subject Olympiads (Maths/Science/English)", "Model United Nations (MUN)", "Hackathons & coding challenges")) {
            if (!out.contains(s)) {
                out.add(s);
            }
        }
        return out;
    }

    private Map<String, List<String>> competitionMap() {
        Map<String, List<String>> m = new LinkedHashMap<>();
        m.put("LINGUISTIC", new ArrayList<>(List.of("Debates & elocution", "Creative writing / essay contests", "Spell Bee & quizzes")));
        m.put("LOGICAL", new ArrayList<>(List.of("Mathematics Olympiad", "Science Olympiad", "Coding & logic competitions")));
        m.put("SPATIAL", new ArrayList<>(List.of("Art & design competitions", "Robotics challenges", "Astronomy Olympiad")));
        m.put("KINESTHETIC", new ArrayList<>(List.of("Sports tournaments", "Robotics & maker fairs", "Dramatics/theatre")));
        m.put("MUSICAL", new ArrayList<>(List.of("Music competitions", "Cultural festivals")));
        m.put("INTERPERSONAL", new ArrayList<>(List.of("Model United Nations (MUN)", "Group leadership events", "Debates")));
        m.put("INTRAPERSONAL", new ArrayList<>(List.of("Reflective essay contests", "Independent research projects")));
        m.put("NATURALISTIC", new ArrayList<>(List.of("Environment/Science Olympiads", "Eco-club & green projects")));
        return m;
    }

    private List<String> skillPlanFor(String dominantLs, String dominantMi, int eq) {
        List<String> out = new ArrayList<>();
        out.add("Study smart: " + buildLsSummary(dominantLs));
        out.add("Grow your top intelligence: take on one project each term that uses your "
                + miNames().getOrDefault(dominantMi, "strongest").toLowerCase() + " ability.");
        out.add("Build 21st-century skills: communication, collaboration, critical thinking and creativity.");
        out.add("Learn basic digital skills \u2014 typing, presentations, and safe internet use; try beginner coding.");
        if (eq < 65) {
            out.add("Practise emotional skills: journaling, active listening, and managing stress before exams.");
        }
        out.add("Read for 20 minutes daily and maintain a simple weekly study timetable.");
        return out;
    }

    // ---- clusters / guidance / counsellor / action plan ----

    private List<String> careerClusters(List<CareerRecommendationDTO> careers) {
        List<String> out = new ArrayList<>();
        if (careers != null) {
            careers.stream()
                    .map(CareerRecommendationDTO::getCareerField)
                    .filter(f -> f != null && !f.isBlank())
                    .distinct()
                    .limit(5)
                    .forEach(f -> out.add(f + " cluster"));
        }
        if (out.isEmpty()) {
            out.add("Explore a broad mix of Science, Humanities and Creative clusters.");
        }
        out.add("These are clusters to explore, not a single locked career. Keep options open through Grade 8-10.");
        return out;
    }

    private List<String> parentGuidance(String dominantLs, int eq) {
        List<String> out = new ArrayList<>();
        out.add("Support how your child learns best: " + buildLsSummary(dominantLs));
        out.add("Encourage exploration across many activities before narrowing choices \u2014 avoid locking a career too early.");
        out.add("Praise effort and progress, not just marks, to build a growth mindset.");
        if (eq < 65) {
            out.add("Create a calm space to talk about feelings and stress; model healthy coping.");
        }
        out.add("Expose them to role models, workplace visits and hobbies to widen their view of careers.");
        return out;
    }

    private List<String> counsellorRecommendations(int aptitude, int interest, int eq) {
        List<String> out = new ArrayList<>();
        out.add("Use this report as a starting point for a one-to-one conversation about interests and goals.");
        if (aptitude < 60) {
            out.add("Strengthen core academic skills with structured practice and periodic review.");
        } else {
            out.add("Channel strong aptitude into challenging enrichment (Olympiads, projects, advanced reading).");
        }
        if (interest >= 60) {
            out.add("Connect the student's clear interests to real-world exposure \u2014 clubs, mentors, workshops.");
        } else {
            out.add("Run interest-discovery activities and taster sessions to help interests emerge.");
        }
        if (eq < 65) {
            out.add("Introduce social-emotional learning to build confidence and peer relationships.");
        }
        out.add("Re-assess in 12 months to track growth and refine subject choices for Grades 9-12.");
        return out;
    }

    private List<Grade8ReportDTO.ActionPlanPhaseDTO> actionPlan() {
        return List.of(
                Grade8ReportDTO.ActionPlanPhaseDTO.builder()
                        .period("Now \u2013 6 months")
                        .items(List.of(
                                "Set up a weekly study timetable that suits your learning style.",
                                "Join one club or activity linked to your top intelligence.",
                                "Start reading daily and keep a simple goals journal."))
                        .build(),
                Grade8ReportDTO.ActionPlanPhaseDTO.builder()
                        .period("6 \u2013 18 months")
                        .items(List.of(
                                "Take part in at least one competition or Olympiad.",
                                "Try a beginner online course or workshop in an area of interest.",
                                "Work on a development area with a teacher or mentor."))
                        .build(),
                Grade8ReportDTO.ActionPlanPhaseDTO.builder()
                        .period("18 \u2013 36 months")
                        .items(List.of(
                                "Explore career clusters through talks, visits and job-shadowing.",
                                "Use these insights to choose subjects/stream for Grades 9-12.",
                                "Re-take the assessment to see how you have grown and update your plan."))
                        .build());
    }

    // ---- helpers ----

    private List<TraitScoreDTO> toTraitScores(Map<String, Integer> breakdown, Map<String, String> names) {
        List<TraitScoreDTO> out = new ArrayList<>();
        for (Map.Entry<String, String> e : names.entrySet()) {
            out.add(TraitScoreDTO.builder()
                    .code(e.getKey())
                    .name(e.getValue())
                    .percentage(breakdown.getOrDefault(e.getKey(), 0))
                    .build());
        }
        out.sort((a, b) -> Integer.compare(b.getPercentage(), a.getPercentage()));
        return out;
    }

    private String topCode(Map<String, Integer> breakdown) {
        return breakdown.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    private String secondCode(Map<String, Integer> breakdown) {
        return breakdown.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .skip(1)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse("");
    }

    private int pct(List<CategoryScoreDTO> scores, String code) {
        return scores.stream()
                .filter(s -> code.equals(s.getCategoryCode()))
                .findFirst()
                .map(s -> s.getPercentage() == null ? 0 : s.getPercentage().intValue())
                .orElse(0);
    }

    private Map<String, String> miNames() {
        Map<String, String> m = new LinkedHashMap<>();
        Grade8SeedData.multipleIntelligenceTraits().forEach(t -> m.put(t.code(), t.name()));
        return m;
    }

    private Map<String, String> lsNames() {
        Map<String, String> m = new LinkedHashMap<>();
        Grade8SeedData.learningStyleTraits().forEach(t -> m.put(t.code(), t.name()));
        return m;
    }
}

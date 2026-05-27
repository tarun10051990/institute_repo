package com.career.assessment.service;

import com.career.assessment.dto.*;
import com.career.assessment.entity.*;
import com.career.assessment.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssessmentService {

    private final AssessmentSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final UserResponseRepository responseRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryScoreRepository scoreRepository;
    private final CareerRecommendationRepository recommendationRepository;

    public AssessmentService(
            AssessmentSessionRepository sessionRepository,
            UserRepository userRepository,
            QuestionRepository questionRepository,
            AnswerOptionRepository answerOptionRepository,
            UserResponseRepository responseRepository,
            CategoryRepository categoryRepository,
            CategoryScoreRepository scoreRepository,
            CareerRecommendationRepository recommendationRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
        this.responseRepository = responseRepository;
        this.categoryRepository = categoryRepository;
        this.scoreRepository = scoreRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Transactional
    public SessionDTO startSession(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AssessmentSession session = AssessmentSession.builder()
                .user(user)
                .sessionCode("SESS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .status(AssessmentSession.SessionStatus.IN_PROGRESS)
                .startedAt(LocalDateTime.now())
                .build();

        session = sessionRepository.save(session);
        return toSessionDTO(session);
    }

    @Transactional
    public void submitAnswer(Long sessionId, Long userId, SubmitAnswerRequest request) {
        AssessmentSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (session.getStatus() == AssessmentSession.SessionStatus.COMPLETED) {
            throw new RuntimeException("Session already completed");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));
        AnswerOption option = answerOptionRepository.findById(request.getSelectedOptionId())
                .orElseThrow(() -> new RuntimeException("Answer option not found"));

        Optional<UserResponse> existing = responseRepository
                .findBySessionIdAndQuestionId(sessionId, request.getQuestionId());

        if (existing.isPresent()) {
            UserResponse resp = existing.get();
            resp.setSelectedOption(option);
            resp.setAnsweredAt(LocalDateTime.now());
            responseRepository.save(resp);
        } else {
            UserResponse resp = UserResponse.builder()
                    .session(session)
                    .user(user)
                    .question(question)
                    .selectedOption(option)
                    .build();
            responseRepository.save(resp);
        }
    }

    @Transactional
    public AssessmentResultDTO completeSession(Long sessionId, Long userId) {
        AssessmentSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setStatus(AssessmentSession.SessionStatus.COMPLETED);
        session.setCompletedAt(LocalDateTime.now());
        sessionRepository.save(session);

        List<CategoryScore> scores = calculateScores(session, userId);
        List<CareerRecommendation> recommendations = generateRecommendations(session, userId, scores);

        return buildResultDTO(session, scores, recommendations);
    }

    public AssessmentResultDTO getResults(Long sessionId) {
        AssessmentSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        List<CategoryScore> scores = scoreRepository.findBySessionIdOrderByCategoryDisplayOrderAsc(sessionId);
        List<CareerRecommendation> recommendations = recommendationRepository.findBySessionIdOrderByRankOrderAsc(sessionId);

        return buildResultDTO(session, scores, recommendations);
    }

    public List<SessionDTO> getUserSessions(Long userId) {
        return sessionRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toSessionDTO)
                .collect(Collectors.toList());
    }

    public SessionDTO getSessionStatus(Long sessionId) {
        AssessmentSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return toSessionDTO(session);
    }

    private List<CategoryScore> calculateScores(AssessmentSession session, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Category> categories = categoryRepository.findAllByOrderByDisplayOrderAsc();
        List<CategoryScore> scores = new ArrayList<>();

        for (Category category : categories) {
            List<UserResponse> responses = responseRepository
                    .findBySessionIdAndCategoryId(session.getId(), category.getId());

            int rawScore = 0;
            int maxScore = 0;
            Map<String, Integer> traitScores = new HashMap<>();

            for (UserResponse response : responses) {
                AnswerOption selected = response.getSelectedOption();
                rawScore += selected.getScoreValue();
                maxScore += 5;

                if (selected.getTraitCode() != null) {
                    traitScores.merge(selected.getTraitCode(), selected.getScoreValue(), Integer::sum);
                }
            }

            if (maxScore == 0) maxScore = 1;

            BigDecimal percentage = BigDecimal.valueOf(rawScore)
                    .divide(BigDecimal.valueOf(maxScore), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(2, RoundingMode.HALF_UP);

            String topTrait = traitScores.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("BALANCED");

            String traitSummary = generateTraitSummary(category.getCode(), topTrait, percentage);

            CategoryScore score = CategoryScore.builder()
                    .session(session)
                    .user(user)
                    .category(category)
                    .rawScore(rawScore)
                    .maxScore(maxScore)
                    .percentage(percentage)
                    .traitSummary(traitSummary)
                    .build();

            scores.add(scoreRepository.save(score));
        }
        return scores;
    }

    private String generateTraitSummary(String categoryCode, String topTrait, BigDecimal percentage) {
        return switch (categoryCode) {
            case "ORIENTATION" -> "Your dominant orientation style is " + formatTrait(topTrait)
                    + ". You scored " + percentage + "% in this dimension, indicating a strong preference for "
                    + getOrientationDescription(topTrait) + ".";
            case "INTEREST" -> "Your primary interest area is " + formatTrait(topTrait)
                    + ". With a score of " + percentage + "%, you show a strong inclination towards "
                    + getInterestDescription(topTrait) + ".";
            case "PERSONALITY" -> "Your personality profile highlights " + formatTrait(topTrait)
                    + " as your dominant trait. Scoring " + percentage + "%, you demonstrate "
                    + getPersonalityDescription(topTrait) + ".";
            case "APTITUDE" -> "Your strongest aptitude area is " + formatTrait(topTrait)
                    + ". You achieved " + percentage + "% in this dimension, showing strong "
                    + getAptitudeDescription(topTrait) + ".";
            case "EQ" -> "Your EQ profile is led by " + formatTrait(topTrait)
                    + ". With a score of " + percentage + "%, you show strong "
                    + getEQDescription(topTrait) + ".";
            default -> "Score: " + percentage + "%";
        };
    }

    private String formatTrait(String trait) {
        return trait.replace("_", " ").toLowerCase();
    }

    private String getOrientationDescription(String trait) {
        return switch (trait) {
            case "STRUCTURED" -> "organized, planned approaches to work";
            case "FLEXIBLE" -> "adaptable, dynamic work environments";
            case "COLLABORATIVE" -> "team-based, cooperative work settings";
            case "INDEPENDENT" -> "autonomous, self-directed work";
            case "ANALYTICAL" -> "data-driven, logical problem solving";
            case "CREATIVE" -> "innovative, imaginative approaches";
            case "HANDS_ON" -> "practical, experiential learning";
            case "LEADERSHIP" -> "leading and directing others";
            default -> "balanced work approaches";
        };
    }

    private String getInterestDescription(String trait) {
        return switch (trait) {
            case "REALISTIC" -> "hands-on, practical, and technical careers";
            case "INVESTIGATIVE" -> "research, analysis, and scientific exploration";
            case "ARTISTIC" -> "creative expression, design, and arts";
            case "SOCIAL" -> "helping, teaching, and working with people";
            case "ENTERPRISING" -> "leadership, business, and persuasion";
            case "CONVENTIONAL" -> "organization, data management, and systematic work";
            default -> "diverse career interests";
        };
    }

    private String getPersonalityDescription(String trait) {
        return switch (trait) {
            case "EXTRAVERSION" -> "outgoing, energetic, and socially active tendencies";
            case "INTROVERSION" -> "thoughtful, reserved, and reflective qualities";
            case "CONSCIENTIOUSNESS" -> "organized, responsible, and disciplined behavior";
            case "AGREEABLENESS" -> "cooperative, empathetic, and harmonious relationships";
            case "OPENNESS" -> "curiosity, creativity, and openness to new experiences";
            default -> "balanced personality traits";
        };
    }

    private String getAptitudeDescription(String trait) {
        return switch (trait) {
            case "NUMERICAL" -> "mathematical and quantitative reasoning abilities";
            case "VERBAL" -> "language comprehension and communication skills";
            case "LOGICAL" -> "analytical thinking and pattern recognition";
            case "SPATIAL" -> "visual-spatial reasoning and geometric understanding";
            default -> "cognitive abilities across multiple areas";
        };
    }

    private String getEQDescription(String trait) {
        return switch (trait) {
            case "SELF_AWARENESS" -> "understanding of your own emotions and their impact";
            case "SELF_REGULATION" -> "ability to manage and control your emotional responses";
            case "MOTIVATION" -> "inner drive, goal-setting, and resilience";
            case "EMPATHY" -> "ability to understand and share others' feelings";
            case "SOCIAL_SKILLS" -> "interpersonal communication and relationship management";
            default -> "overall emotional intelligence";
        };
    }

    private List<CareerRecommendation> generateRecommendations(
            AssessmentSession session, Long userId, List<CategoryScore> scores) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, BigDecimal> categoryPercentages = new HashMap<>();
        for (CategoryScore score : scores) {
            categoryPercentages.put(score.getCategory().getCode(), score.getPercentage());
        }

        List<CareerData> allCareers = getCareerDatabase();
        List<CareerData> ranked = allCareers.stream()
                .map(career -> {
                    BigDecimal match = calculateMatchScore(career, categoryPercentages);
                    career.matchScore = match;
                    return career;
                })
                .sorted((a, b) -> b.matchScore.compareTo(a.matchScore))
                .limit(5)
                .collect(Collectors.toList());

        List<CareerRecommendation> recommendations = new ArrayList<>();
        for (int i = 0; i < ranked.size(); i++) {
            CareerData c = ranked.get(i);
            CareerRecommendation rec = CareerRecommendation.builder()
                    .session(session)
                    .user(user)
                    .careerTitle(c.title)
                    .careerDescription(c.description)
                    .matchPercentage(c.matchScore.setScale(2, RoundingMode.HALF_UP))
                    .careerField(c.field)
                    .requiredEducation(c.education)
                    .salaryRange(c.salaryRange)
                    .growthOutlook(c.growthOutlook)
                    .rankOrder(i + 1)
                    .build();
            recommendations.add(recommendationRepository.save(rec));
        }
        return recommendations;
    }

    private BigDecimal calculateMatchScore(CareerData career, Map<String, BigDecimal> scores) {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal weightSum = BigDecimal.ZERO;

        for (Map.Entry<String, Double> entry : career.weights.entrySet()) {
            BigDecimal score = scores.getOrDefault(entry.getKey(), BigDecimal.ZERO);
            BigDecimal weight = BigDecimal.valueOf(entry.getValue());
            total = total.add(score.multiply(weight));
            weightSum = weightSum.add(weight);
        }

        if (weightSum.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return total.divide(weightSum, 4, RoundingMode.HALF_UP);
    }

    private List<CareerData> getCareerDatabase() {
        List<CareerData> careers = new ArrayList<>();

        careers.add(new CareerData("Software Engineer", "Design, develop, and maintain software applications and systems.",
                "Technology", "B.Tech/B.E. in Computer Science or related field", "8-25 LPA", "Excellent",
                Map.of("APTITUDE", 0.35, "INTEREST", 0.25, "ORIENTATION", 0.15, "PERSONALITY", 0.15, "EQ", 0.10)));

        careers.add(new CareerData("Data Scientist", "Analyze complex data sets to identify trends, build predictive models, and drive business decisions.",
                "Technology & Analytics", "B.Tech + M.Tech/MS in Data Science, Statistics, or related field", "10-30 LPA", "Excellent",
                Map.of("APTITUDE", 0.40, "INTEREST", 0.25, "ORIENTATION", 0.10, "PERSONALITY", 0.10, "EQ", 0.15)));

        careers.add(new CareerData("Doctor / Physician", "Diagnose illnesses, prescribe treatments, and provide medical care to patients.",
                "Healthcare", "MBBS + MD/MS specialization", "8-30 LPA", "Excellent",
                Map.of("APTITUDE", 0.25, "INTEREST", 0.20, "ORIENTATION", 0.10, "PERSONALITY", 0.15, "EQ", 0.30)));

        careers.add(new CareerData("Graphic Designer", "Create visual content for digital and print media using design software and artistic skills.",
                "Design & Creative", "B.Des/BFA or diploma in Graphic Design", "4-15 LPA", "Good",
                Map.of("APTITUDE", 0.15, "INTEREST", 0.35, "ORIENTATION", 0.15, "PERSONALITY", 0.20, "EQ", 0.15)));

        careers.add(new CareerData("Civil Engineer", "Plan, design, and oversee construction of infrastructure projects.",
                "Engineering", "B.Tech/B.E. in Civil Engineering", "5-18 LPA", "Good",
                Map.of("APTITUDE", 0.35, "INTEREST", 0.20, "ORIENTATION", 0.20, "PERSONALITY", 0.10, "EQ", 0.15)));

        careers.add(new CareerData("Psychologist", "Study human behavior and mental processes to help individuals overcome challenges.",
                "Mental Health", "MA/M.Sc in Psychology + M.Phil (RCI)", "5-15 LPA", "Good",
                Map.of("APTITUDE", 0.15, "INTEREST", 0.20, "ORIENTATION", 0.10, "PERSONALITY", 0.20, "EQ", 0.35)));

        careers.add(new CareerData("Business Analyst", "Bridge the gap between IT and business by analyzing processes and recommending solutions.",
                "Business & Management", "BBA/MBA or B.Tech with MBA", "6-20 LPA", "Excellent",
                Map.of("APTITUDE", 0.30, "INTEREST", 0.20, "ORIENTATION", 0.20, "PERSONALITY", 0.15, "EQ", 0.15)));

        careers.add(new CareerData("Content Writer", "Create engaging written content for websites, blogs, social media, and marketing materials.",
                "Media & Communications", "BA/MA in English, Journalism, or Mass Communication", "3-12 LPA", "Good",
                Map.of("APTITUDE", 0.20, "INTEREST", 0.30, "ORIENTATION", 0.15, "PERSONALITY", 0.20, "EQ", 0.15)));

        careers.add(new CareerData("Chartered Accountant", "Manage financial records, auditing, taxation, and financial advisory for organizations.",
                "Finance", "B.Com + CA qualification", "7-25 LPA", "Excellent",
                Map.of("APTITUDE", 0.40, "INTEREST", 0.20, "ORIENTATION", 0.20, "PERSONALITY", 0.10, "EQ", 0.10)));

        careers.add(new CareerData("Teacher / Educator", "Educate and inspire students in academic subjects, fostering their intellectual growth.",
                "Education", "B.Ed + Subject specialization", "3-12 LPA", "Stable",
                Map.of("APTITUDE", 0.15, "INTEREST", 0.20, "ORIENTATION", 0.15, "PERSONALITY", 0.20, "EQ", 0.30)));

        careers.add(new CareerData("Architect", "Design buildings and spaces that are functional, safe, and aesthetically pleasing.",
                "Architecture & Design", "B.Arch (5-year program)", "5-20 LPA", "Good",
                Map.of("APTITUDE", 0.25, "INTEREST", 0.30, "ORIENTATION", 0.15, "PERSONALITY", 0.15, "EQ", 0.15)));

        careers.add(new CareerData("Marketing Manager", "Develop and execute marketing strategies to promote products and build brand awareness.",
                "Marketing & Sales", "BBA/MBA in Marketing", "6-22 LPA", "Excellent",
                Map.of("APTITUDE", 0.15, "INTEREST", 0.25, "ORIENTATION", 0.20, "PERSONALITY", 0.25, "EQ", 0.15)));

        careers.add(new CareerData("Environmental Scientist", "Study and address environmental issues like pollution, conservation, and sustainability.",
                "Environmental Science", "B.Sc/M.Sc in Environmental Science", "4-15 LPA", "Growing",
                Map.of("APTITUDE", 0.25, "INTEREST", 0.30, "ORIENTATION", 0.15, "PERSONALITY", 0.10, "EQ", 0.20)));

        careers.add(new CareerData("UX/UI Designer", "Design user-centered digital interfaces that are intuitive and visually appealing.",
                "Design & Technology", "B.Des or relevant certification in UX/UI Design", "6-20 LPA", "Excellent",
                Map.of("APTITUDE", 0.20, "INTEREST", 0.30, "ORIENTATION", 0.15, "PERSONALITY", 0.20, "EQ", 0.15)));

        careers.add(new CareerData("Lawyer / Legal Advisor", "Provide legal counsel, represent clients, and ensure compliance with laws and regulations.",
                "Law & Legal", "BA LLB / LLB + LLM specialization", "5-25 LPA", "Good",
                Map.of("APTITUDE", 0.25, "INTEREST", 0.20, "ORIENTATION", 0.15, "PERSONALITY", 0.20, "EQ", 0.20)));

        return careers;
    }

    private AssessmentResultDTO buildResultDTO(AssessmentSession session,
                                                List<CategoryScore> scores,
                                                List<CareerRecommendation> recommendations) {
        User user = session.getUser();

        List<CategoryScoreDTO> scoreDTOs = scores.stream()
                .map(s -> CategoryScoreDTO.builder()
                        .categoryId(s.getCategory().getId())
                        .categoryName(s.getCategory().getName())
                        .categoryCode(s.getCategory().getCode())
                        .icon(s.getCategory().getIcon())
                        .rawScore(s.getRawScore())
                        .maxScore(s.getMaxScore())
                        .percentage(s.getPercentage())
                        .traitSummary(s.getTraitSummary())
                        .build())
                .collect(Collectors.toList());

        List<CareerRecommendationDTO> recDTOs = recommendations.stream()
                .map(r -> CareerRecommendationDTO.builder()
                        .id(r.getId())
                        .careerTitle(r.getCareerTitle())
                        .careerDescription(r.getCareerDescription())
                        .matchPercentage(r.getMatchPercentage())
                        .careerField(r.getCareerField())
                        .requiredEducation(r.getRequiredEducation())
                        .salaryRange(r.getSalaryRange())
                        .growthOutlook(r.getGrowthOutlook())
                        .rankOrder(r.getRankOrder())
                        .build())
                .collect(Collectors.toList());

        return AssessmentResultDTO.builder()
                .sessionId(session.getId())
                .sessionCode(session.getSessionCode())
                .userName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .completedAt(session.getCompletedAt())
                .categoryScores(scoreDTOs)
                .careerRecommendations(recDTOs)
                .overallSummary("Assessment completed successfully. Based on your responses across 5 dimensions, we have identified your top career matches.")
                .build();
    }

    private SessionDTO toSessionDTO(AssessmentSession session) {
        long totalQuestions = questionRepository.count();
        long answered = responseRepository.countBySessionId(session.getId());

        return SessionDTO.builder()
                .id(session.getId())
                .sessionCode(session.getSessionCode())
                .status(session.getStatus().name())
                .startedAt(session.getStartedAt())
                .completedAt(session.getCompletedAt())
                .createdAt(session.getCreatedAt())
                .totalQuestions(totalQuestions)
                .answeredQuestions(answered)
                .build();
    }

    private static class CareerData {
        String title, description, field, education, salaryRange, growthOutlook;
        Map<String, Double> weights;
        BigDecimal matchScore = BigDecimal.ZERO;

        CareerData(String title, String description, String field, String education,
                   String salaryRange, String growthOutlook, Map<String, Double> weights) {
            this.title = title;
            this.description = description;
            this.field = field;
            this.education = education;
            this.salaryRange = salaryRange;
            this.growthOutlook = growthOutlook;
            this.weights = weights;
        }
    }
}

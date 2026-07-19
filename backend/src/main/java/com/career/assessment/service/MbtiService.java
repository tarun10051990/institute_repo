package com.career.assessment.service;

import com.career.assessment.dto.*;
import com.career.assessment.entity.AssessmentSession;
import com.career.assessment.entity.MbtiOption;
import com.career.assessment.entity.MbtiQuestion;
import com.career.assessment.entity.MbtiResult;
import com.career.assessment.entity.MbtiTypeProfile;
import com.career.assessment.entity.User;
import com.career.assessment.repository.AssessmentSessionRepository;
import com.career.assessment.repository.MbtiQuestionRepository;
import com.career.assessment.repository.MbtiResultRepository;
import com.career.assessment.repository.MbtiTypeProfileRepository;
import com.career.assessment.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MbtiService {

    private static final Logger log = LoggerFactory.getLogger(MbtiService.class);

    private final MbtiQuestionRepository questionRepository;
    private final MbtiTypeProfileRepository profileRepository;
    private final MbtiResultRepository resultRepository;
    private final UserRepository userRepository;
    private final AssessmentSessionRepository sessionRepository;
    private final ObjectMapper objectMapper;

    public MbtiService(MbtiQuestionRepository questionRepository,
                       MbtiTypeProfileRepository profileRepository,
                       MbtiResultRepository resultRepository,
                       UserRepository userRepository,
                       AssessmentSessionRepository sessionRepository,
                       ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.profileRepository = profileRepository;
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public List<MbtiQuestionDTO> getQuestions() {
        List<MbtiQuestionDTO> dtos = new ArrayList<>();
        for (MbtiQuestion q : questionRepository.findByActiveTrueOrderByDisplayOrderAsc()) {
            List<MbtiOptionDTO> options = new ArrayList<>();
            for (MbtiOption o : q.getOptions()) {
                options.add(new MbtiOptionDTO(o.getLabel(), o.getText(), o.getLetter()));
            }
            dtos.add(new MbtiQuestionDTO(q.getId().intValue(), q.getDimension(), q.getQuestionText(), options));
        }
        return dtos;
    }

    /** Stateless evaluation (public preview, no persistence). */
    public MbtiResultDTO evaluate(MbtiAnswerRequest request) {
        return evaluate(request, null);
    }

    /**
     * Evaluate answers into a full report. When {@code userId} is provided the
     * result is persisted (optionally linked to an assessment session).
     */
    @Transactional
    public MbtiResultDTO evaluate(MbtiAnswerRequest request, Long userId) {
        if (request == null || request.getAnswers() == null || request.getAnswers().isEmpty()) {
            throw new IllegalArgumentException("No answers provided");
        }

        Map<String, Integer> counts = new HashMap<>();
        for (MbtiAnswerRequest.MbtiAnswer answer : request.getAnswers()) {
            if (answer.getLetter() == null) {
                continue;
            }
            counts.merge(answer.getLetter().trim().toUpperCase(), 1, Integer::sum);
        }

        List<MbtiDimensionScoreDTO> dimensionScores = new ArrayList<>();
        dimensionScores.add(buildDimensionScore("EI", "E", "Extraversion", "I", "Introversion", counts));
        dimensionScores.add(buildDimensionScore("SN", "S", "Sensing", "N", "Intuition", counts));
        dimensionScores.add(buildDimensionScore("TF", "T", "Thinking", "F", "Feeling", counts));
        dimensionScores.add(buildDimensionScore("JP", "J", "Judging", "P", "Perceiving", counts));

        StringBuilder type = new StringBuilder();
        for (MbtiDimensionScoreDTO score : dimensionScores) {
            type.append(score.getChosenLetter());
        }

        MbtiResultDTO result = buildResult(type.toString(), dimensionScores);

        if (userId != null) {
            persistResult(result, userId, request.getSessionId());
        }
        return result;
    }

    private MbtiResultDTO buildResult(String typeCode, List<MbtiDimensionScoreDTO> dimensionScores) {
        MbtiTypeProfile profile = profileRepository.findByTypeCode(typeCode)
                .orElseGet(() -> profileRepository.findByTypeCode("INTJ").orElse(null));

        MbtiResultDTO result = new MbtiResultDTO();
        result.setType(typeCode);
        result.setDimensions(dimensionScores);
        if (profile != null) {
            result.setNickname(profile.getNickname());
            result.setSummary(profile.getSummary());
            result.setOverview(profile.getOverview());
            result.setStrengths(splitLines(profile.getStrengths()));
            result.setWeaknesses(splitLines(profile.getWeaknesses()));
            result.setCareers(splitLines(profile.getCareers()));
            result.setRelationships(profile.getRelationships());
            result.setGrowthTips(profile.getGrowthTips());
        }
        return result;
    }

    private void persistResult(MbtiResultDTO result, Long userId, Long sessionId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }
        AssessmentSession session = null;
        if (sessionId != null) {
            session = sessionRepository.findById(sessionId).orElse(null);
        }
        String json;
        try {
            json = objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            log.warn("Failed to serialize MBTI result", e);
            json = null;
        }
        MbtiResult entity = MbtiResult.builder()
                .user(user)
                .session(session)
                .typeCode(result.getType())
                .nickname(result.getNickname())
                .dimensionsJson(json)
                .build();
        resultRepository.save(entity);
    }

    /** Rebuild the full report DTO from a persisted result. */
    public MbtiResultDTO toDto(MbtiResult entity) {
        if (entity == null) {
            return null;
        }
        if (entity.getDimensionsJson() != null) {
            try {
                return objectMapper.readValue(entity.getDimensionsJson(), MbtiResultDTO.class);
            } catch (Exception e) {
                log.warn("Failed to deserialize MBTI result {}", entity.getId(), e);
            }
        }
        return buildResult(entity.getTypeCode(), new ArrayList<>());
    }

    private List<String> splitLines(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }
        List<String> out = new ArrayList<>();
        for (String line : value.split("\\r?\\n")) {
            if (!line.isBlank()) {
                out.add(line.trim());
            }
        }
        return out;
    }

    private MbtiDimensionScoreDTO buildDimensionScore(String dimension, String leftLetter, String leftName,
                                                      String rightLetter, String rightName, Map<String, Integer> counts) {
        int left = counts.getOrDefault(leftLetter, 0);
        int right = counts.getOrDefault(rightLetter, 0);
        int total = left + right;

        String chosenLetter = left >= right ? leftLetter : rightLetter;
        String chosenName = left >= right ? leftName : rightName;
        int chosenCount = Math.max(left, right);
        int strength = total == 0 ? 50 : (int) Math.round((chosenCount * 100.0) / total);

        MbtiDimensionScoreDTO score = new MbtiDimensionScoreDTO();
        score.setDimension(dimension);
        score.setLeftLetter(leftLetter);
        score.setLeftName(leftName);
        score.setRightLetter(rightLetter);
        score.setRightName(rightName);
        score.setLeftCount(left);
        score.setRightCount(right);
        score.setChosenLetter(chosenLetter);
        score.setChosenName(chosenName);
        score.setStrengthPercentage(strength);
        score.setDescription(dimensionDescription(chosenLetter, strength));
        return score;
    }

    private String dimensionDescription(String letter, int strength) {
        String base = switch (letter) {
            case "E" -> "You draw energy from the outer world of people and activity, and tend to think out loud.";
            case "I" -> "You draw energy from your inner world of ideas and reflection, and prefer depth over breadth.";
            case "S" -> "You focus on concrete facts, details, and practical realities you can observe.";
            case "N" -> "You focus on patterns, possibilities, and the big-picture meaning behind information.";
            case "T" -> "You make decisions using logic, consistency, and objective analysis.";
            case "F" -> "You make decisions based on personal values and the impact on people.";
            case "J" -> "You prefer structure, planning, and reaching closure on decisions.";
            case "P" -> "You prefer flexibility, spontaneity, and keeping your options open.";
            default -> "Balanced preference.";
        };
        String intensity;
        if (strength >= 80) intensity = "This is a clear, strong preference for you.";
        else if (strength >= 60) intensity = "This is a moderate preference for you.";
        else intensity = "This preference is slight, so the opposite trait also shows up often.";
        return base + " " + intensity;
    }
}

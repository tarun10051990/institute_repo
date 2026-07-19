package com.career.assessment.service;

import com.career.assessment.dto.*;
import com.career.assessment.entity.*;
import com.career.assessment.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final AssessmentSessionRepository sessionRepository;
    private final MbtiResultRepository mbtiResultRepository;
    private final MbtiQuestionRepository mbtiQuestionRepository;
    private final MbtiOptionRepository mbtiOptionRepository;
    private final MbtiTypeProfileRepository mbtiTypeProfileRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;

    public AdminService(UserRepository userRepository,
                        AssessmentSessionRepository sessionRepository,
                        MbtiResultRepository mbtiResultRepository,
                        MbtiQuestionRepository mbtiQuestionRepository,
                        MbtiOptionRepository mbtiOptionRepository,
                        MbtiTypeProfileRepository mbtiTypeProfileRepository,
                        CategoryRepository categoryRepository,
                        QuestionRepository questionRepository,
                        AnswerOptionRepository answerOptionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.mbtiResultRepository = mbtiResultRepository;
        this.mbtiQuestionRepository = mbtiQuestionRepository;
        this.mbtiOptionRepository = mbtiOptionRepository;
        this.mbtiTypeProfileRepository = mbtiTypeProfileRepository;
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
    }

    // ---------------- Users ----------------

    public List<AdminUserDTO> listUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserDTO)
                .collect(Collectors.toList());
    }

    private AdminUserDTO toUserDTO(User user) {
        List<AssessmentSession> sessions = sessionRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        long completed = sessions.stream()
                .filter(s -> s.getStatus() == AssessmentSession.SessionStatus.COMPLETED)
                .count();
        return AdminUserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole().name() : User.Role.USER.name())
                .city(user.getCity())
                .educationLevel(user.getEducationLevel() != null ? user.getEducationLevel().name() : null)
                .createdAt(user.getCreatedAt())
                .sessionCount(sessions.size())
                .completedCount(completed)
                .build();
    }

    @Transactional
    public void deleteUser(Long userId, Long currentAdminId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (user.getId().equals(currentAdminId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot delete your own account");
        }
        if (user.getRole() == User.Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin accounts cannot be deleted");
        }
        userRepository.delete(user);
    }

    // ---------------- Sessions & reports ----------------

    public List<AdminSessionDTO> listAllSessions() {
        return sessionRepository.findAll().stream()
                .map(this::toAdminSessionDTO)
                .collect(Collectors.toList());
    }

    public List<AdminSessionDTO> listUserSessions(Long userId) {
        return sessionRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toAdminSessionDTO)
                .collect(Collectors.toList());
    }

    private AdminSessionDTO toAdminSessionDTO(AssessmentSession session) {
        User user = session.getUser();
        String mbtiType = mbtiResultRepository.findTopBySessionIdOrderByCreatedAtDesc(session.getId())
                .map(MbtiResult::getTypeCode)
                .orElse(null);
        return AdminSessionDTO.builder()
                .sessionId(session.getId())
                .sessionCode(session.getSessionCode())
                .status(session.getStatus().name())
                .userId(user.getId())
                .userName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .mbtiType(mbtiType)
                .completedAt(session.getCompletedAt())
                .createdAt(session.getCreatedAt())
                .build();
    }

    // ---------------- MBTI questions ----------------

    public List<MbtiQuestionAdminDTO> listMbtiQuestions() {
        return mbtiQuestionRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(this::toMbtiQuestionDTO)
                .collect(Collectors.toList());
    }

    private MbtiQuestionAdminDTO toMbtiQuestionDTO(MbtiQuestion q) {
        List<MbtiQuestionAdminDTO.MbtiOptionAdminDTO> options = q.getOptions().stream()
                .map(o -> MbtiQuestionAdminDTO.MbtiOptionAdminDTO.builder()
                        .id(o.getId())
                        .label(o.getLabel())
                        .text(o.getText())
                        .letter(o.getLetter())
                        .displayOrder(o.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());
        return MbtiQuestionAdminDTO.builder()
                .id(q.getId())
                .dimension(q.getDimension())
                .questionText(q.getQuestionText())
                .displayOrder(q.getDisplayOrder())
                .active(q.getActive())
                .options(options)
                .build();
    }

    @Transactional
    public MbtiQuestionAdminDTO saveMbtiQuestion(Long id, MbtiQuestionAdminDTO dto) {
        MbtiQuestion question = id != null
                ? mbtiQuestionRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MBTI question not found"))
                : MbtiQuestion.builder().build();

        question.setDimension(dto.getDimension());
        question.setQuestionText(dto.getQuestionText());
        question.setDisplayOrder(dto.getDisplayOrder() != null ? dto.getDisplayOrder() : 0);
        question.setActive(dto.getActive() != null ? dto.getActive() : true);

        question.getOptions().clear();
        int order = 0;
        if (dto.getOptions() != null) {
            for (MbtiQuestionAdminDTO.MbtiOptionAdminDTO o : dto.getOptions()) {
                MbtiOption option = MbtiOption.builder()
                        .question(question)
                        .label(o.getLabel())
                        .text(o.getText())
                        .letter(o.getLetter() != null ? o.getLetter().trim().toUpperCase() : null)
                        .displayOrder(o.getDisplayOrder() != null ? o.getDisplayOrder() : order++)
                        .build();
                question.getOptions().add(option);
            }
        }
        return toMbtiQuestionDTO(mbtiQuestionRepository.save(question));
    }

    @Transactional
    public void deleteMbtiQuestion(Long id) {
        if (!mbtiQuestionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MBTI question not found");
        }
        mbtiQuestionRepository.deleteById(id);
    }

    // ---------------- MBTI profiles (report content) ----------------

    public List<MbtiProfileDTO> listMbtiProfiles() {
        return mbtiTypeProfileRepository.findAllByOrderByTypeCodeAsc().stream()
                .map(this::toProfileDTO)
                .collect(Collectors.toList());
    }

    private MbtiProfileDTO toProfileDTO(MbtiTypeProfile p) {
        return MbtiProfileDTO.builder()
                .id(p.getId())
                .typeCode(p.getTypeCode())
                .nickname(p.getNickname())
                .summary(p.getSummary())
                .overview(p.getOverview())
                .strengths(splitLines(p.getStrengths()))
                .weaknesses(splitLines(p.getWeaknesses()))
                .careers(splitLines(p.getCareers()))
                .relationships(p.getRelationships())
                .growthTips(p.getGrowthTips())
                .build();
    }

    @Transactional
    public MbtiProfileDTO updateMbtiProfile(Long id, MbtiProfileDTO dto) {
        MbtiTypeProfile p = mbtiTypeProfileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MBTI profile not found"));
        p.setNickname(dto.getNickname());
        p.setSummary(dto.getSummary());
        p.setOverview(dto.getOverview());
        p.setStrengths(joinLines(dto.getStrengths()));
        p.setWeaknesses(joinLines(dto.getWeaknesses()));
        p.setCareers(joinLines(dto.getCareers()));
        p.setRelationships(dto.getRelationships());
        p.setGrowthTips(dto.getGrowthTips());
        return toProfileDTO(mbtiTypeProfileRepository.save(p));
    }

    // ---------------- Career questions & options ----------------

    public List<QuestionAdminDTO> listQuestions(Long categoryId) {
        List<Question> questions = categoryId != null
                ? questionRepository.findByCategoryIdAndIsActiveTrueOrderByDisplayOrderAsc(categoryId)
                : questionRepository.findAll();
        return questions.stream().map(this::toQuestionDTO).collect(Collectors.toList());
    }

    private QuestionAdminDTO toQuestionDTO(Question q) {
        List<QuestionAdminDTO.AnswerOptionAdminDTO> options = q.getAnswerOptions().stream()
                .map(o -> QuestionAdminDTO.AnswerOptionAdminDTO.builder()
                        .id(o.getId())
                        .optionText(o.getOptionText())
                        .optionLabel(o.getOptionLabel())
                        .scoreValue(o.getScoreValue())
                        .traitCode(o.getTraitCode())
                        .displayOrder(o.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());
        return QuestionAdminDTO.builder()
                .id(q.getId())
                .categoryId(q.getCategory() != null ? q.getCategory().getId() : null)
                .categoryName(q.getCategory() != null ? q.getCategory().getName() : null)
                .questionText(q.getQuestionText())
                .questionType(q.getQuestionType() != null ? q.getQuestionType().name() : null)
                .difficultyLevel(q.getDifficultyLevel() != null ? q.getDifficultyLevel().name() : null)
                .displayOrder(q.getDisplayOrder())
                .active(q.getIsActive())
                .options(options)
                .build();
    }

    @Transactional
    public QuestionAdminDTO saveQuestion(Long id, QuestionAdminDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));

        Question question = id != null
                ? questionRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"))
                : Question.builder().build();

        question.setCategory(category);
        question.setQuestionText(dto.getQuestionText());
        question.setQuestionType(parseEnum(Question.QuestionType.class, dto.getQuestionType(), Question.QuestionType.SINGLE_CHOICE));
        question.setDifficultyLevel(parseEnum(Question.DifficultyLevel.class, dto.getDifficultyLevel(), Question.DifficultyLevel.MEDIUM));
        question.setDisplayOrder(dto.getDisplayOrder() != null ? dto.getDisplayOrder() : 0);
        question.setIsActive(dto.getActive() != null ? dto.getActive() : true);

        question.getAnswerOptions().clear();
        int order = 0;
        if (dto.getOptions() != null) {
            for (QuestionAdminDTO.AnswerOptionAdminDTO o : dto.getOptions()) {
                AnswerOption option = AnswerOption.builder()
                        .question(question)
                        .optionText(o.getOptionText())
                        .optionLabel(o.getOptionLabel())
                        .scoreValue(o.getScoreValue() != null ? o.getScoreValue() : 0)
                        .traitCode(o.getTraitCode())
                        .displayOrder(o.getDisplayOrder() != null ? o.getDisplayOrder() : order++)
                        .build();
                question.getAnswerOptions().add(option);
            }
        }
        return toQuestionDTO(questionRepository.save(question));
    }

    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
        }
        questionRepository.deleteById(id);
    }

    // ---------------- helpers ----------------

    private <E extends Enum<E>> E parseEnum(Class<E> type, String value, E fallback) {
        if (value == null) {
            return fallback;
        }
        try {
            return Enum.valueOf(type, value);
        } catch (IllegalArgumentException e) {
            return fallback;
        }
    }

    private List<String> splitLines(String value) {
        if (value == null || value.isBlank()) {
            return new ArrayList<>();
        }
        List<String> out = new ArrayList<>();
        for (String line : value.split("\\r?\\n")) {
            if (!line.isBlank()) {
                out.add(line.trim());
            }
        }
        return out;
    }

    private String joinLines(List<String> items) {
        if (items == null) {
            return null;
        }
        return items.stream().filter(s -> s != null && !s.isBlank())
                .map(String::trim)
                .collect(Collectors.joining("\n"));
    }
}

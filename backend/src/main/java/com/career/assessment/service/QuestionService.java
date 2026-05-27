package com.career.assessment.service;

import com.career.assessment.dto.AnswerOptionDTO;
import com.career.assessment.dto.QuestionDTO;
import com.career.assessment.entity.Question;
import com.career.assessment.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDTO> getQuestionsByCategory(Long categoryId) {
        return questionRepository.findByCategoryIdAndIsActiveTrueOrderByDisplayOrderAsc(categoryId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public QuestionDTO getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return toDTO(question);
    }

    private QuestionDTO toDTO(Question question) {
        List<AnswerOptionDTO> options = question.getAnswerOptions().stream()
                .map(opt -> AnswerOptionDTO.builder()
                        .id(opt.getId())
                        .optionText(opt.getOptionText())
                        .optionLabel(opt.getOptionLabel())
                        .displayOrder(opt.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());

        return QuestionDTO.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .questionType(question.getQuestionType().name())
                .difficultyLevel(question.getDifficultyLevel().name())
                .displayOrder(question.getDisplayOrder())
                .categoryId(question.getCategory().getId())
                .categoryName(question.getCategory().getName())
                .options(options)
                .build();
    }
}

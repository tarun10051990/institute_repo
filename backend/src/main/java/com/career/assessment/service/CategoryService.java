package com.career.assessment.service;

import com.career.assessment.dto.CategoryDTO;
import com.career.assessment.entity.Category;
import com.career.assessment.repository.CategoryRepository;
import com.career.assessment.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    public CategoryService(CategoryRepository categoryRepository, QuestionRepository questionRepository) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return toDTO(category);
    }

    public CategoryDTO getCategoryByCode(String code) {
        Category category = categoryRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return toDTO(category);
    }

    private CategoryDTO toDTO(Category category) {
        long questionCount = questionRepository.countByCategoryIdAndIsActiveTrue(category.getId());
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .code(category.getCode())
                .description(category.getDescription())
                .icon(category.getIcon())
                .displayOrder(category.getDisplayOrder())
                .timeLimitMinutes(category.getTimeLimitMinutes())
                .totalQuestions(category.getTotalQuestions())
                .questionCount(questionCount)
                .build();
    }
}

package com.career.assessment.controller;

import com.career.assessment.dto.QuestionDTO;
import com.career.assessment.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(questionService.getQuestionsByCategory(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }
}

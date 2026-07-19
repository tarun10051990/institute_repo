package com.career.assessment.controller;

import com.career.assessment.dto.MbtiAnswerRequest;
import com.career.assessment.dto.MbtiQuestionDTO;
import com.career.assessment.dto.MbtiResultDTO;
import com.career.assessment.entity.User;
import com.career.assessment.service.MbtiService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mbti")
public class MbtiController {

    private final MbtiService mbtiService;

    public MbtiController(MbtiService mbtiService) {
        this.mbtiService = mbtiService;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<MbtiQuestionDTO>> getQuestions() {
        return ResponseEntity.ok(mbtiService.getQuestions());
    }

    @PostMapping("/result")
    public ResponseEntity<MbtiResultDTO> getResult(@RequestBody MbtiAnswerRequest request,
                                                   @AuthenticationPrincipal User user) {
        Long userId = user != null ? user.getId() : null;
        return ResponseEntity.ok(mbtiService.evaluate(request, userId));
    }
}

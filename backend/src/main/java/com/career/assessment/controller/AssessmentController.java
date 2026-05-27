package com.career.assessment.controller;

import com.career.assessment.dto.AssessmentResultDTO;
import com.career.assessment.dto.SessionDTO;
import com.career.assessment.dto.SubmitAnswerRequest;
import com.career.assessment.entity.User;
import com.career.assessment.service.AssessmentService;
import com.career.assessment.service.PdfReportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assessment")
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final PdfReportService pdfReportService;

    public AssessmentController(AssessmentService assessmentService, PdfReportService pdfReportService) {
        this.assessmentService = assessmentService;
        this.pdfReportService = pdfReportService;
    }

    @PostMapping("/start")
    public ResponseEntity<SessionDTO> startSession(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(assessmentService.startSession(user.getId()));
    }

    @PostMapping("/sessions/{sessionId}/answer")
    public ResponseEntity<Map<String, String>> submitAnswer(
            @PathVariable Long sessionId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody SubmitAnswerRequest request) {
        assessmentService.submitAnswer(sessionId, user.getId(), request);
        return ResponseEntity.ok(Map.of("message", "Answer saved successfully"));
    }

    @PostMapping("/sessions/{sessionId}/complete")
    public ResponseEntity<AssessmentResultDTO> completeSession(
            @PathVariable Long sessionId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(assessmentService.completeSession(sessionId, user.getId()));
    }

    @GetMapping("/sessions/{sessionId}/results")
    public ResponseEntity<AssessmentResultDTO> getResults(@PathVariable Long sessionId) {
        return ResponseEntity.ok(assessmentService.getResults(sessionId));
    }

    @GetMapping("/sessions/{sessionId}/status")
    public ResponseEntity<SessionDTO> getSessionStatus(@PathVariable Long sessionId) {
        return ResponseEntity.ok(assessmentService.getSessionStatus(sessionId));
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<SessionDTO>> getUserSessions(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(assessmentService.getUserSessions(user.getId()));
    }

    @GetMapping("/sessions/{sessionId}/report/pdf")
    public ResponseEntity<byte[]> downloadPdfReport(@PathVariable Long sessionId) throws IOException {
        AssessmentResultDTO result = assessmentService.getResults(sessionId);
        byte[] pdfBytes = pdfReportService.generateReport(result);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment",
                "career-assessment-report-" + result.getSessionCode() + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}

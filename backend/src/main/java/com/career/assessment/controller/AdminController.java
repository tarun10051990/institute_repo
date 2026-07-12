package com.career.assessment.controller;

import com.career.assessment.dto.*;
import com.career.assessment.entity.User;
import com.career.assessment.service.AdminService;
import com.career.assessment.service.AssessmentService;
import com.career.assessment.service.PdfReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final AssessmentService assessmentService;
    private final PdfReportService pdfReportService;

    public AdminController(AdminService adminService,
                          AssessmentService assessmentService,
                          PdfReportService pdfReportService) {
        this.adminService = adminService;
        this.assessmentService = assessmentService;
        this.pdfReportService = pdfReportService;
    }

    // ---------------- Users ----------------

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserDTO>> listUsers() {
        return ResponseEntity.ok(adminService.listUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id,
                                                           @AuthenticationPrincipal User admin) {
        adminService.deleteUser(id, admin.getId());
        return ResponseEntity.ok(Map.of("message", "User deleted"));
    }

    @GetMapping("/users/{id}/sessions")
    public ResponseEntity<List<AdminSessionDTO>> userSessions(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.listUserSessions(id));
    }

    // ---------------- Sessions & reports ----------------

    @GetMapping("/sessions")
    public ResponseEntity<List<AdminSessionDTO>> listSessions() {
        return ResponseEntity.ok(adminService.listAllSessions());
    }

    @GetMapping("/sessions/{sessionId}/results")
    public ResponseEntity<AssessmentResultDTO> results(@PathVariable Long sessionId) {
        return ResponseEntity.ok(assessmentService.getResults(sessionId));
    }

    @GetMapping("/sessions/{sessionId}/report/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long sessionId) throws IOException {
        AssessmentResultDTO result = assessmentService.getResults(sessionId);
        byte[] pdf = pdfReportService.generateReport(result);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment",
                "career-assessment-report-" + result.getSessionCode() + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdf);
    }

    // ---------------- MBTI questions ----------------

    @GetMapping("/mbti/questions")
    public ResponseEntity<List<MbtiQuestionAdminDTO>> listMbtiQuestions() {
        return ResponseEntity.ok(adminService.listMbtiQuestions());
    }

    @PostMapping("/mbti/questions")
    public ResponseEntity<MbtiQuestionAdminDTO> createMbtiQuestion(@RequestBody MbtiQuestionAdminDTO dto) {
        return ResponseEntity.ok(adminService.saveMbtiQuestion(null, dto));
    }

    @PutMapping("/mbti/questions/{id}")
    public ResponseEntity<MbtiQuestionAdminDTO> updateMbtiQuestion(@PathVariable Long id,
                                                                   @RequestBody MbtiQuestionAdminDTO dto) {
        return ResponseEntity.ok(adminService.saveMbtiQuestion(id, dto));
    }

    @DeleteMapping("/mbti/questions/{id}")
    public ResponseEntity<Map<String, String>> deleteMbtiQuestion(@PathVariable Long id) {
        adminService.deleteMbtiQuestion(id);
        return ResponseEntity.ok(Map.of("message", "MBTI question deleted"));
    }

    // ---------------- MBTI profiles (report content) ----------------

    @GetMapping("/mbti/profiles")
    public ResponseEntity<List<MbtiProfileDTO>> listMbtiProfiles() {
        return ResponseEntity.ok(adminService.listMbtiProfiles());
    }

    @PutMapping("/mbti/profiles/{id}")
    public ResponseEntity<MbtiProfileDTO> updateMbtiProfile(@PathVariable Long id,
                                                            @RequestBody MbtiProfileDTO dto) {
        return ResponseEntity.ok(adminService.updateMbtiProfile(id, dto));
    }

    // ---------------- Career questions & options ----------------

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionAdminDTO>> listQuestions(@RequestParam(required = false) Long categoryId) {
        return ResponseEntity.ok(adminService.listQuestions(categoryId));
    }

    @PostMapping("/questions")
    public ResponseEntity<QuestionAdminDTO> createQuestion(@RequestBody QuestionAdminDTO dto) {
        return ResponseEntity.ok(adminService.saveQuestion(null, dto));
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<QuestionAdminDTO> updateQuestion(@PathVariable Long id,
                                                           @RequestBody QuestionAdminDTO dto) {
        return ResponseEntity.ok(adminService.saveQuestion(id, dto));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Map<String, String>> deleteQuestion(@PathVariable Long id) {
        adminService.deleteQuestion(id);
        return ResponseEntity.ok(Map.of("message", "Question deleted"));
    }
}

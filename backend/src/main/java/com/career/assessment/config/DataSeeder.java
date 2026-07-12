package com.career.assessment.config;

import com.career.assessment.entity.MbtiOption;
import com.career.assessment.entity.MbtiQuestion;
import com.career.assessment.entity.MbtiTypeProfile;
import com.career.assessment.entity.User;
import com.career.assessment.repository.MbtiQuestionRepository;
import com.career.assessment.repository.MbtiTypeProfileRepository;
import com.career.assessment.repository.UserRepository;
import com.career.assessment.service.MbtiSeedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Seeds the initial admin account and the admin-manageable MBTI content on
 * first startup. Idempotent: only inserts records that are missing.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final MbtiQuestionRepository mbtiQuestionRepository;
    private final MbtiTypeProfileRepository mbtiTypeProfileRepository;
    private final PasswordEncoder passwordEncoder;

    private final String adminEmail;
    private final String adminPassword;
    private final String adminFirstName;
    private final String adminLastName;

    public DataSeeder(UserRepository userRepository,
                      MbtiQuestionRepository mbtiQuestionRepository,
                      MbtiTypeProfileRepository mbtiTypeProfileRepository,
                      PasswordEncoder passwordEncoder,
                      @Value("${app.admin.email}") String adminEmail,
                      @Value("${app.admin.password}") String adminPassword,
                      @Value("${app.admin.first-name}") String adminFirstName,
                      @Value("${app.admin.last-name}") String adminLastName) {
        this.userRepository = userRepository;
        this.mbtiQuestionRepository = mbtiQuestionRepository;
        this.mbtiTypeProfileRepository = mbtiTypeProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.adminFirstName = adminFirstName;
        this.adminLastName = adminLastName;
    }

    @Override
    public void run(String... args) {
        seedAdmin();
        seedMbtiQuestions();
        seedMbtiProfiles();
    }

    private void seedAdmin() {
        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }
        User admin = User.builder()
                .firstName(adminFirstName)
                .lastName(adminLastName)
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .educationLevel(User.EducationLevel.PROFESSIONAL)
                .role(User.Role.ADMIN)
                .build();
        userRepository.save(admin);
        log.info("Seeded initial admin account: {}", adminEmail);
    }

    private void seedMbtiQuestions() {
        if (mbtiQuestionRepository.count() > 0) {
            return;
        }
        List<MbtiQuestion> toSave = new ArrayList<>();
        for (MbtiSeedData.SeedQuestion sq : MbtiSeedData.questions()) {
            MbtiQuestion question = MbtiQuestion.builder()
                    .dimension(sq.dimension())
                    .questionText(sq.text())
                    .displayOrder(sq.order())
                    .active(true)
                    .build();
            List<MbtiOption> options = new ArrayList<>();
            int order = 0;
            for (MbtiSeedData.SeedOption so : sq.options()) {
                options.add(MbtiOption.builder()
                        .question(question)
                        .label(so.label())
                        .text(so.text())
                        .letter(so.letter())
                        .displayOrder(order++)
                        .build());
            }
            question.setOptions(options);
            toSave.add(question);
        }
        mbtiQuestionRepository.saveAll(toSave);
        log.info("Seeded {} MBTI questions", toSave.size());
    }

    private void seedMbtiProfiles() {
        if (mbtiTypeProfileRepository.count() > 0) {
            return;
        }
        List<MbtiTypeProfile> toSave = new ArrayList<>();
        for (MbtiSeedData.SeedProfile sp : MbtiSeedData.profiles()) {
            toSave.add(MbtiTypeProfile.builder()
                    .typeCode(sp.type())
                    .nickname(sp.nickname())
                    .summary(sp.summary())
                    .overview(sp.overview())
                    .strengths(String.join("\n", sp.strengths()))
                    .weaknesses(String.join("\n", sp.weaknesses()))
                    .careers(String.join("\n", sp.careers()))
                    .relationships(sp.relationships())
                    .growthTips(sp.growthTips())
                    .build());
        }
        mbtiTypeProfileRepository.saveAll(toSave);
        log.info("Seeded {} MBTI type profiles", toSave.size());
    }
}

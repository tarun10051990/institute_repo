package com.career.assessment.dto;

import java.util.List;

public class MbtiAnswerRequest {

    private List<MbtiAnswer> answers;
    private Long sessionId;

    public List<MbtiAnswer> getAnswers() { return answers; }
    public void setAnswers(List<MbtiAnswer> answers) { this.answers = answers; }

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }

    public static class MbtiAnswer {
        private int questionId;
        private String letter;

        public int getQuestionId() { return questionId; }
        public void setQuestionId(int questionId) { this.questionId = questionId; }

        public String getLetter() { return letter; }
        public void setLetter(String letter) { this.letter = letter; }
    }
}

package com.career.assessment.dto;

import java.util.List;

public class MbtiQuestionDTO {
    private int id;
    private String dimension;
    private String questionText;
    private List<MbtiOptionDTO> options;

    public MbtiQuestionDTO() {}

    public MbtiQuestionDTO(int id, String dimension, String questionText, List<MbtiOptionDTO> options) {
        this.id = id;
        this.dimension = dimension;
        this.questionText = questionText;
        this.options = options;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDimension() { return dimension; }
    public void setDimension(String dimension) { this.dimension = dimension; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public List<MbtiOptionDTO> getOptions() { return options; }
    public void setOptions(List<MbtiOptionDTO> options) { this.options = options; }
}

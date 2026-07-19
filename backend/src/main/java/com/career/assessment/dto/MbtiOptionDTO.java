package com.career.assessment.dto;

public class MbtiOptionDTO {
    private String label;
    private String text;
    private String letter;

    public MbtiOptionDTO() {}

    public MbtiOptionDTO(String label, String text, String letter) {
        this.label = label;
        this.text = text;
        this.letter = letter;
    }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getLetter() { return letter; }
    public void setLetter(String letter) { this.letter = letter; }
}

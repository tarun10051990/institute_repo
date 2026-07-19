package com.career.assessment.dto;

public class MbtiDimensionScoreDTO {
    private String dimension;
    private String leftLetter;
    private String leftName;
    private String rightLetter;
    private String rightName;
    private int leftCount;
    private int rightCount;
    private String chosenLetter;
    private String chosenName;
    private int strengthPercentage;
    private String description;

    public MbtiDimensionScoreDTO() {}

    public String getDimension() { return dimension; }
    public void setDimension(String dimension) { this.dimension = dimension; }

    public String getLeftLetter() { return leftLetter; }
    public void setLeftLetter(String leftLetter) { this.leftLetter = leftLetter; }

    public String getLeftName() { return leftName; }
    public void setLeftName(String leftName) { this.leftName = leftName; }

    public String getRightLetter() { return rightLetter; }
    public void setRightLetter(String rightLetter) { this.rightLetter = rightLetter; }

    public String getRightName() { return rightName; }
    public void setRightName(String rightName) { this.rightName = rightName; }

    public int getLeftCount() { return leftCount; }
    public void setLeftCount(int leftCount) { this.leftCount = leftCount; }

    public int getRightCount() { return rightCount; }
    public void setRightCount(int rightCount) { this.rightCount = rightCount; }

    public String getChosenLetter() { return chosenLetter; }
    public void setChosenLetter(String chosenLetter) { this.chosenLetter = chosenLetter; }

    public String getChosenName() { return chosenName; }
    public void setChosenName(String chosenName) { this.chosenName = chosenName; }

    public int getStrengthPercentage() { return strengthPercentage; }
    public void setStrengthPercentage(int strengthPercentage) { this.strengthPercentage = strengthPercentage; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

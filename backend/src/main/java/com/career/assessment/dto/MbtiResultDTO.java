package com.career.assessment.dto;

import java.util.List;

public class MbtiResultDTO {
    private String type;
    private String nickname;
    private String summary;
    private String overview;
    private List<MbtiDimensionScoreDTO> dimensions;
    private List<String> strengths;
    private List<String> weaknesses;
    private List<String> careers;
    private String relationships;
    private String growthTips;

    public MbtiResultDTO() {}

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public List<MbtiDimensionScoreDTO> getDimensions() { return dimensions; }
    public void setDimensions(List<MbtiDimensionScoreDTO> dimensions) { this.dimensions = dimensions; }

    public List<String> getStrengths() { return strengths; }
    public void setStrengths(List<String> strengths) { this.strengths = strengths; }

    public List<String> getWeaknesses() { return weaknesses; }
    public void setWeaknesses(List<String> weaknesses) { this.weaknesses = weaknesses; }

    public List<String> getCareers() { return careers; }
    public void setCareers(List<String> careers) { this.careers = careers; }

    public String getRelationships() { return relationships; }
    public void setRelationships(String relationships) { this.relationships = relationships; }

    public String getGrowthTips() { return growthTips; }
    public void setGrowthTips(String growthTips) { this.growthTips = growthTips; }
}

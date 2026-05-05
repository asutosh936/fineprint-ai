package com.fineprintai.tosanalyzer.dto;

import java.util.List;

public class AnalysisResult {
    private int overallRiskScore; // 0-10
    private List<FlaggedClause> flaggedClauses;
    private String disclaimer;

    public AnalysisResult() {}

    public AnalysisResult(int overallRiskScore, List<FlaggedClause> flaggedClauses, String disclaimer) {
        this.overallRiskScore = overallRiskScore;
        this.flaggedClauses = flaggedClauses;
        this.disclaimer = disclaimer;
    }

    // Getters and setters
    public int getOverallRiskScore() {
        return overallRiskScore;
    }

    public void setOverallRiskScore(int overallRiskScore) {
        this.overallRiskScore = overallRiskScore;
    }

    public List<FlaggedClause> getFlaggedClauses() {
        return flaggedClauses;
    }

    public void setFlaggedClauses(List<FlaggedClause> flaggedClauses) {
        this.flaggedClauses = flaggedClauses;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }
}
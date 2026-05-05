package com.fineprintai.tosanalyzer.dto;

public class FlaggedClause {
    private String clauseText;
    private String severity; // "high", "medium", "low"
    private String rationale;
    private String comparisonToBaseline;

    public FlaggedClause() {}

    public FlaggedClause(String clauseText, String severity, String rationale, String comparisonToBaseline) {
        this.clauseText = clauseText;
        this.severity = severity;
        this.rationale = rationale;
        this.comparisonToBaseline = comparisonToBaseline;
    }

    // Getters and setters
    public String getClauseText() {
        return clauseText;
    }

    public void setClauseText(String clauseText) {
        this.clauseText = clauseText;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getRationale() {
        return rationale;
    }

    public void setRationale(String rationale) {
        this.rationale = rationale;
    }

    public String getComparisonToBaseline() {
        return comparisonToBaseline;
    }

    public void setComparisonToBaseline(String comparisonToBaseline) {
        this.comparisonToBaseline = comparisonToBaseline;
    }
}
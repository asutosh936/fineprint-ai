package com.fineprintai.tosanalyzer.dto;

public class ScanHistoryEntry {
    private String id;
    private String title;
    private String scannedAt;
    private int riskScore;
    private String topIssue;
    private AnalysisResult result;

    public ScanHistoryEntry() {
    }

    public ScanHistoryEntry(String id, String title, String scannedAt, int riskScore, String topIssue, AnalysisResult result) {
        this.id = id;
        this.title = title;
        this.scannedAt = scannedAt;
        this.riskScore = riskScore;
        this.topIssue = topIssue;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScannedAt() {
        return scannedAt;
    }

    public void setScannedAt(String scannedAt) {
        this.scannedAt = scannedAt;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public String getTopIssue() {
        return topIssue;
    }

    public void setTopIssue(String topIssue) {
        this.topIssue = topIssue;
    }

    public AnalysisResult getResult() {
        return result;
    }

    public void setResult(AnalysisResult result) {
        this.result = result;
    }
}

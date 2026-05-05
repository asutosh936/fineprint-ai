package com.fineprintai.tosanalyzer.controller;

import com.fineprintai.tosanalyzer.dto.AnalysisResult;
import com.fineprintai.tosanalyzer.service.TosAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TosAnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(TosAnalysisController.class);

    private final TosAnalysisService tosAnalysisService;

    @Autowired
    public TosAnalysisController(TosAnalysisService tosAnalysisService) {
        this.tosAnalysisService = tosAnalysisService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResult> analyzeTos(@RequestParam String text) {
        logger.info("Received ToS analysis request with text length: {}", text != null ? text.length() : 0);

        if (text == null || text.trim().isEmpty()) {
            logger.warn("ToS analysis request rejected: text is null or empty");
            return ResponseEntity.badRequest().build();
        }
        if (text.length() > 10000) {
            logger.warn("ToS analysis request rejected: text length {} exceeds limit", text.length());
            return ResponseEntity.badRequest().body(null);
        }

        try {
            AnalysisResult result = tosAnalysisService.analyzeTos(text);
            logger.info("ToS analysis completed successfully with risk score: {}", result.getOverallRiskScore());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error during ToS analysis: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
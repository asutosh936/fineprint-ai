package com.fineprintai.tosanalyzer.controller;

import com.fineprintai.tosanalyzer.dto.AnalysisResult;
import com.fineprintai.tosanalyzer.service.TosAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TosAnalysisController {

    private static final Logger logger = LoggerFactory.getLogger(TosAnalysisController.class);

    private final TosAnalysisService tosAnalysisService;

    @Autowired
    public TosAnalysisController(TosAnalysisService tosAnalysisService) {
        this.tosAnalysisService = tosAnalysisService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/analyze")
    public String analyzeTos(@RequestParam(required = false) String text, Model model) {
        logger.info("Received ToS analysis request with text length: {}", text != null ? text.length() : 0);

        if (text == null || text.trim().isEmpty()) {
            logger.warn("ToS analysis request rejected: text is null or empty");
            model.addAttribute("error", "Please provide valid ToS text.");
            return "index";
        }
        if (text.length() > 10000) {
            logger.warn("ToS analysis request rejected: text length {} exceeds limit", text.length());
            model.addAttribute("error", "ToS text is too long. Maximum 10,000 characters allowed.");
            return "index";
        }

        try {
            AnalysisResult result = tosAnalysisService.analyzeTos(text);
            logger.info("ToS analysis completed successfully with risk score: {}", result.getOverallRiskScore());
            model.addAttribute("result", result);
            return "results";
        } catch (Exception e) {
            logger.error("Error during ToS analysis: {}", e.getMessage(), e);
            model.addAttribute("error", "An error occurred during analysis. Please try again.");
            return "index";
        }
    }
}
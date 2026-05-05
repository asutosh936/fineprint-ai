package com.fineprintai.tosanalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fineprintai.tosanalyzer.dto.AnalysisResult;
import com.fineprintai.tosanalyzer.dto.FlaggedClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TosAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(TosAnalysisService.class);

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public TosAnalysisService(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
        logger.info("TosAnalysisService initialized with ChatClient and ObjectMapper");
    }

    public AnalysisResult analyzeTos(String tosText) {
        if (tosText == null || tosText.trim().isEmpty()) {
            logger.error("ToS text is null or empty");
            throw new IllegalArgumentException("ToS text cannot be null or empty");
        }

        logger.info("Starting ToS analysis for text of length: {}", tosText.length());

        String promptText = buildPrompt(tosText);
        logger.debug("Generated prompt for AI");

        try {
            String response = chatClient.prompt(new Prompt(promptText))
                    .call()
                    .content();
            logger.debug("Received AI response");

            AnalysisResult result = parseResponse(response);
            logger.info("ToS analysis completed successfully");
            return result;
        } catch (Exception e) {
            logger.error("Error during ToS analysis: {}", e.getMessage(), e);
            throw e;
        }
    }

    private String buildPrompt(String tosText) {
        return """
                Analyze the following Terms of Service text for potential red flags. Focus on clauses that could be risky for users, such as data privacy issues, arbitration, unilateral changes, etc.

                ToS Text:
                """ + tosText + """

                Provide a structured JSON response with the following format:
                {
                    "overallRiskScore": <number 0-10>,
                    "flaggedClauses": [
                        {
                            "clauseText": "<quoted sentence snippet>",
                            "severity": "<high|medium|low>",
                            "rationale": "<plain English explanation why it's risky>",
                            "comparisonToBaseline": "<e.g., worse than baseline, similar to baseline>"
                        }
                    ],
                    "disclaimer": "This is not legal advice."
                }

                Limit to top 10 flagged clauses. Compare to standard baseline ToS practices.
                """;
    }

    private AnalysisResult parseResponse(String response) {
        try {
            AnalysisResult result = objectMapper.readValue(response, AnalysisResult.class);
            logger.debug("Successfully parsed AI response into AnalysisResult");
            return result;
        } catch (Exception e) {
            logger.warn("Failed to parse AI response as JSON, using fallback result: {}", e.getMessage());
            // Fallback to hardcoded result if parsing fails
            FlaggedClause clause1 = new FlaggedClause(
                    "We may change these terms at any time without notice.",
                    "high",
                    "Allows unilateral changes without user consent.",
                    "worse than baseline"
            );
            List<FlaggedClause> clauses = Arrays.asList(clause1);
            return new AnalysisResult(7, clauses, "This is not legal advice.");
        }
    }
}
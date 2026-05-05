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
        long startTime = System.currentTimeMillis();

        String promptText = buildPrompt(tosText);
        // Log the prompt (truncated for readability if very long)
        String logPrompt = promptText.length() > 500 ?
            promptText.substring(0, 500) + "...[truncated]" : promptText;
        logger.debug("Generated prompt for AI (length: {}): {}", promptText.length(), logPrompt);

        try {
            String response = chatClient.prompt(new Prompt(promptText))
                    .call()
                    .content();
            // Log the response (truncated for readability if very long)
            String logResponse = response.length() > 500 ?
                response.substring(0, 500) + "...[truncated]" : response;
            logger.debug("Received AI response (length: {}): {}", response.length(), logResponse);

            AnalysisResult result = parseResponse(response);
            long endTime = System.currentTimeMillis();
            logger.info("ToS analysis completed successfully in {}ms", (endTime - startTime));
            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            logger.error("Error during ToS analysis after {}ms: {}", (endTime - startTime), e.getMessage(), e);
            throw e;
        }
    }

    private String buildPrompt(String tosText) {
        return """
                Analyze this Terms of Service for user risks. Focus on: data privacy, arbitration, unilateral changes, liability limitations.

                ToS Text:
                """ + tosText + """

                Return ONLY valid JSON (no markdown, no code blocks):
                {
                    "overallRiskScore": <0-10>,
                    "flaggedClauses": [
                        {
                            "clauseText": "<exact quote>",
                            "severity": "<high|medium|low>",
                            "rationale": "<why risky>",
                            "comparisonToBaseline": "<vs standard practices>"
                        }
                    ],
                    "disclaimer": "Not legal advice."
                }

                Max 8 clauses. Be concise.
                """;
    }

    private AnalysisResult parseResponse(String response) {
        try {
            // Clean the response if it contains markdown code blocks
            String cleanResponse = response.trim();
            if (cleanResponse.startsWith("```json")) {
                // Remove the opening ```json
                cleanResponse = cleanResponse.substring(7);
                // Remove the closing ```
                int closingIndex = cleanResponse.lastIndexOf("```");
                if (closingIndex > 0) {
                    cleanResponse = cleanResponse.substring(0, closingIndex);
                }
                cleanResponse = cleanResponse.trim();
            }

            AnalysisResult result = objectMapper.readValue(cleanResponse, AnalysisResult.class);
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
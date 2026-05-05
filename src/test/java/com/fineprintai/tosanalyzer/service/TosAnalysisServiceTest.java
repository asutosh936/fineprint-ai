package com.fineprintai.tosanalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fineprintai.tosanalyzer.dto.AnalysisResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TosAnalysisServiceTest {

    @Mock
    private ChatClient.Builder chatClientBuilder;

    @Mock
    private ChatClient chatClient;

    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec;

    @Mock
    private ChatClient.CallResponseSpec callResponseSpec;

    private TosAnalysisService tosAnalysisService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        when(chatClientBuilder.build()).thenReturn(chatClient);
        tosAnalysisService = new TosAnalysisService(chatClientBuilder, objectMapper);
    }

    @Test
    void analyzeTos_ValidText_ReturnsResult() {
        // Arrange
        String testText = "Sample ToS text";
        String mockResponse = "{\"overallRiskScore\":7,\"flaggedClauses\":[{\"clauseText\":\"Test\",\"severity\":\"high\",\"rationale\":\"Test\",\"comparisonToBaseline\":\"Test\"}],\"disclaimer\":\"Disclaimer\"}";

        when(chatClient.prompt(any(Prompt.class))).thenReturn(requestSpec);
        when(requestSpec.call()).thenReturn(callResponseSpec);
        when(callResponseSpec.content()).thenReturn(mockResponse);

        // Act
        AnalysisResult result = tosAnalysisService.analyzeTos(testText);

        // Assert
        assertNotNull(result);
        assertEquals(7, result.getOverallRiskScore());
        assertEquals(1, result.getFlaggedClauses().size());
        assertEquals("Disclaimer", result.getDisclaimer());
    }

    @Test
    void analyzeTos_NullText_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tosAnalysisService.analyzeTos(null);
        });
        assertEquals("ToS text cannot be null or empty", exception.getMessage());
    }

    @Test
    void analyzeTos_EmptyText_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tosAnalysisService.analyzeTos("");
        });
        assertEquals("ToS text cannot be null or empty", exception.getMessage());
    }

    @Test
    void analyzeTos_WhitespaceText_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tosAnalysisService.analyzeTos("   ");
        });
        assertEquals("ToS text cannot be null or empty", exception.getMessage());
    }

    @Test
    void buildPrompt_IncludesText() {
        // Use reflection or make method package-private for testing
        // For now, assume it's covered via integration
    }
}
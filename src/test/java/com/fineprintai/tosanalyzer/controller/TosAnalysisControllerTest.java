package com.fineprintai.tosanalyzer.controller;

import com.fineprintai.tosanalyzer.dto.AnalysisResult;
import com.fineprintai.tosanalyzer.dto.FlaggedClause;
import com.fineprintai.tosanalyzer.service.TosAnalysisService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TosAnalysisController.class)
class TosAnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TosAnalysisService tosAnalysisService;

    @Test
    void analyzeTos_ValidInput_ReturnsOk() throws Exception {
        // Arrange
        FlaggedClause clause = new FlaggedClause("Test clause", "high", "Test rationale", "worse than baseline");
        List<FlaggedClause> clauses = Arrays.asList(clause);
        AnalysisResult result = new AnalysisResult(5, clauses, "Disclaimer");
        when(tosAnalysisService.analyzeTos(anyString())).thenReturn(result);

        // Act & Assert
        mockMvc.perform(post("/api/analyze")
                        .param("text", "Sample ToS text")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.overallRiskScore").value(5))
                .andExpect(jsonPath("$.flaggedClauses[0].clauseText").value("Test clause"));
    }

    @Test
    void analyzeTos_EmptyText_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/analyze")
                        .param("text", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void analyzeTos_NullText_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/analyze"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void analyzeTos_TextTooLong_ReturnsBadRequest() throws Exception {
        String longText = "a".repeat(10001);
        mockMvc.perform(post("/api/analyze")
                        .param("text", longText))
                .andExpect(status().isBadRequest());
    }

    @Test
    void analyzeTos_ServiceThrowsException_ReturnsInternalServerError() throws Exception {
        when(tosAnalysisService.analyzeTos(anyString())).thenThrow(new RuntimeException("Test error"));

        mockMvc.perform(post("/api/analyze")
                        .param("text", "Sample text"))
                .andExpect(status().isInternalServerError());
    }
}
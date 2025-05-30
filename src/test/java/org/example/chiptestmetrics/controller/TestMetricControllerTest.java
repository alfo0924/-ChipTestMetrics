package org.example.chiptestmetrics.controller;

import org.example.chiptestmetrics.model.TestMetric;
import org.example.chiptestmetrics.service.TestMetricService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestMetricController.class)
class TestMetricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestMetricService service;

    private TestMetric testMetric;

    @BeforeEach
    void setUp() {
        testMetric = new TestMetric();
        testMetric.setId(1L);
        testMetric.setChipId("CHIP001");
        testMetric.setVoltage(3.3);
        testMetric.setCurrent(0.5);
        testMetric.setTestDate("2025-05-30");
    }

    @Test
    void addMetric_Success() throws Exception {
        when(service.saveMetric(any(TestMetric.class))).thenReturn(testMetric);

        String json = "{\"chipId\": \"CHIP001\", \"voltage\": 3.3, \"current\": 0.5, \"testDate\": \"2025-05-30\"}";

        mockMvc.perform(post("/api/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chipId").value("CHIP001"))
                .andExpect(jsonPath("$.voltage").value(3.3));
    }

    @Test
    void getAllMetrics_ReturnsList() throws Exception {
        List<TestMetric> metrics = Arrays.asList(testMetric);
        when(service.getAllMetrics()).thenReturn(metrics);

        mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].chipId").value("CHIP001"))
                .andExpect(jsonPath("$[0].voltage").value(3.3));
    }

    @Test
    void getMetricById_Found() throws Exception {
        when(service.getMetricById(1L)).thenReturn(testMetric);

        mockMvc.perform(get("/api/metrics/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chipId").value("CHIP001"))
                .andExpect(jsonPath("$.voltage").value(3.3));
    }

    @Test
    void getMetricById_NotFound() throws Exception {
        when(service.getMetricById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/metrics/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}

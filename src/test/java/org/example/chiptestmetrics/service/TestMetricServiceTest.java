package org.example.chiptestmetrics.service;

import org.example.chiptestmetrics.model.TestMetric;
import org.example.chiptestmetrics.repository.TestMetricRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestMetricServiceTest {

    @Mock
    private TestMetricRepository repository;

    @InjectMocks
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
    void saveMetric_Success() {
        when(repository.save(any(TestMetric.class))).thenReturn(testMetric);

        TestMetric savedMetric = service.saveMetric(testMetric);

        assertNotNull(savedMetric);
        assertEquals("CHIP001", savedMetric.getChipId());
        assertEquals(3.3, savedMetric.getVoltage());
        verify(repository, times(1)).save(testMetric);
    }

    @Test
    void getAllMetrics_ReturnsList() {
        List<TestMetric> metrics = Arrays.asList(testMetric);
        when(repository.findAll()).thenReturn(metrics);

        List<TestMetric> result = service.getAllMetrics();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CHIP001", result.get(0).getChipId());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getMetricById_Found() {
        when(repository.findById(1L)).thenReturn(Optional.of(testMetric));

        TestMetric result = service.getMetricById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("CHIP001", result.getChipId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getMetricById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        TestMetric result = service.getMetricById(1L);

        assertNull(result);
        verify(repository, times(1)).findById(1L);
    }
}
